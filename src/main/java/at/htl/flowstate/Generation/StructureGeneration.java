package at.htl.flowstate.Generation;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.spawn;

public class StructureGeneration {
    //list containing all chests on the map so it can be checked whether the player is inside
    private List<Entity> chests = new ArrayList<>();

    public StructureGeneration() { }

    //-----STRUCTURES-----

    public double spawnStructure(double lastGeneratedX, double currentY) {
        int rand = FXGLMath.random(0,2);

        switch(rand) {
            case 0: return spawnChestStructure(lastGeneratedX, currentY);
            case 1: return spawnChallengeStructure(lastGeneratedX, currentY);
        }
        return lastGeneratedX;
    }

    private double  spawnChestStructure(double lastGeneratedX, double currentY) {

        spawnChest(lastGeneratedX+10, currentY-50);
        LevelGeneration.spawnGroundSegment(lastGeneratedX, currentY, 100);
        return lastGeneratedX += 100;
    }

    private double spawnChallengeStructure(double lastGeneratedX, double currentY) {

        spawnAirPlatform(lastGeneratedX, currentY - 200, 50, 50);
        spawnAirPlatform(lastGeneratedX + 200, currentY - 400, 50, 50);
        spawnAirPlatform(lastGeneratedX, currentY - 600, 50, 50);
        spawnAirPlatform(lastGeneratedX + 200, currentY - 800, 100, 50);

        spawnChest(lastGeneratedX + 210, currentY - 850);

        LevelGeneration.spawnGroundSegment(lastGeneratedX, currentY, 400);
        return lastGeneratedX += 400;
    }

    //-----HELPERS-----

    private void spawnAirPlatform(double x, double y, double width, double height) {
        spawn("platform", new SpawnData(x, y)
                .put("color", Color.SADDLEBROWN)
                .put("width", width)
                .put("height", height));
    }

    private void spawnChest(double x, double y) {
        chests.add(spawn("chest", new SpawnData(x, y)
                .put("color", Color.RED)
                .put("width", 80.0)
                .put("height", 50.0)));
    }
}
