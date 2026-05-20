package at.htl.flowstate.Generation;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.SpawnData;
import javafx.scene.paint.Color;

import static com.almasb.fxgl.dsl.FXGL.spawn;

public class LevelGeneration {

    private final StructureGeneration structureGeneration;
    private final EnemyGeneration enemyGeneration;

    //the floor of each ground segment fills from its top surface all the way
    //down to DEEP_FLOOR which is twice the window height well off-screen
    //so the player never sees the bottom edge
    private static final double DEEP_FLOOR = 1500;

    //terrain parameters
    private static final double BASE_Y = 950;  //the base level the terrain hovers around
    private static final double MIN_Y = 800;  //highest the terrain can rise
    private static final double MAX_Y = 1000; //lowest the terrain can sink before its a wall

    //pit parameters
    private static final double PIT_CHANCE = 0.06;  //in decimal
    private static final double PIT_MIN_WIDTH = 160;
    private static final double PIT_MAX_WIDTH = 280;

    //how far ahead the world is generated
    private static final double SPAWN_DISTANCE = 1400;

    //generation state
    private double lastGeneratedX = 0;
    private double currentY = BASE_Y; //current terrain surface height

    //terrain drift gives hills their momentum so they rise and fall naturally
    //rather than randomly jumping around every segment
    //positive drift = terrain going lower = downhill
    //negative drift = going higher = uphill
    private double terrainDrift = 0;

    //how many more segments the current slope should continue before reconsidering
    private int slopeSegmentsLeft = 0;


    //-----INIT-----
    public LevelGeneration() {
        structureGeneration = new StructureGeneration();
        enemyGeneration = new EnemyGeneration();
    }


    //-----GENERATON-----
    public void generateLevel(double playerX) {
        while (playerX + SPAWN_DISTANCE > lastGeneratedX) {
            spawnNextSegment();
        }
    }

    private void spawnNextSegment() {
        //pit
        if (Math.random() < PIT_CHANCE) {
            double pitWidth = FXGLMath.random((int) PIT_MIN_WIDTH, (int) PIT_MAX_WIDTH);
            lastGeneratedX += pitWidth;
            terrainDrift = 0;
            slopeSegmentsLeft = 0;
            return;
        }

        Double structureWidth = structureGeneration.trySpawn(lastGeneratedX, currentY);
        if(structureWidth != null) {
            lastGeneratedX = structureWidth;
            return;
        }

        //segment witdh
        double segmentW = FXGLMath.random(180, 420);

        //update slope
        currentY = nextTerrainY(segmentW);

        spawnGroundSegment(lastGeneratedX, currentY, segmentW);
        enemyGeneration.trySpawn(lastGeneratedX, currentY);
        lastGeneratedX += segmentW;
    }

    /**
     *Computes the next terrain Y using a momentum based drift system
     *
     *the terrain has a drift value that represents how fast and in which
     *direction it is currently sloping the drift persists for several segments
     *before randomly being reconsidered, producing smooth hills and valleys
     *rather than chaotic random noise
     *
     *most of the time the drift is tiny (relatively flat) sometimes a bigger
     *slope kicks in for a hill or valley
     */
    private double nextTerrainY(double segmentW) {
        if (slopeSegmentsLeft <= 0) {
            //pick a new slope direction and length
            //70% of the time: nearly flat
            //30% of the time: a real slope
            double roll = Math.random();

            if (roll < 0.70) {
                //nearly flat
                int drift =FXGLMath.random(-12, 12);
                if(drift < 3 && drift > -3) drift = 0;
                terrainDrift = drift + (BASE_Y - currentY) * 0.05;
                slopeSegmentsLeft = FXGLMath.random(3, 7);
            } else if (roll < 0.90) {
                //gentle slope
                int drift = FXGLMath.random(-30, 30);
                if(drift < 3 && drift > -3) drift = 0;
                terrainDrift = drift;
                slopeSegmentsLeft = FXGLMath.random(4, 9);
            } else {
                //hill or valley
                int drift = FXGLMath.random(-60, 60);
                if(drift < 3 && drift > -3) drift = 0;
                terrainDrift = drift;
                slopeSegmentsLeft = FXGLMath.random(3, 6);
            }

            //if near a boundary slope back towards BASE_Y
            if (currentY < MIN_Y + 30)  terrainDrift = Math.abs(terrainDrift);  //force downward (higher Y)
            if (currentY > MAX_Y - 30)  terrainDrift = -Math.abs(terrainDrift); //force upward (lower Y)
        }

        slopeSegmentsLeft--;

        double newY = currentY + terrainDrift;

        //hard clamp
        newY = Math.max(MIN_Y, Math.min(MAX_Y, newY));

        return newY;
    }

    /**
     *spawns a single ground segment: a solid rectangle whose top surface is at
     *the given Y and whose bottom extends to DEEP_FLOOR
     *this way no matter how the camera angles the player never sees the bottom
     */




    //-----HELPERS-----
    public static void spawnGroundSegment(double x, double y, double width) {
        double height = DEEP_FLOOR - y;
        spawn("platform", new SpawnData(x, y)
                .put("color", Color.DARKSLATEGRAY)
                .put("width", width)
                .put("height", height));
    }

    //-----GETTERS-----

    public double getBaseY() {
        return BASE_Y;
    }
}
