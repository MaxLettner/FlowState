package at.htl.flowstate.Factories;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LevelFactory implements EntityFactory {

    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        Color color = data.get("color");
        double w = data.get("width");
        double h = data.get("height");

        PhysicsComponent platPhysics = new PhysicsComponent();
        platPhysics.setBodyType(BodyType.STATIC);

        return FXGL.entityBuilder(data)
                .viewWithBBox(new Rectangle(w, h, color))
                .with(platPhysics)
                .collidable()
                .build();
    }

    @Spawns("chest")
    public Entity newChest(SpawnData data) {
        Color color = data.get("color");
        double w = data.get("width");
        double h = data.get("height");

        return FXGL.entityBuilder(data)
                .viewWithBBox(new Rectangle(w, h, color))
                .collidable()
                .zIndex(-1)
                .buildAndAttach();
    }
}