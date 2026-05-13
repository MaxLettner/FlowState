package at.htl.flowstate.Factories;

import at.htl.flowstate.Components.Chests.ChestComponent;
import at.htl.flowstate.Components.Identifier.PlatformIdentifierComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LevelFactory implements EntityFactory {

    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        Color color = data.get("color");
        double w = data.get("width");
        double h = data.get("height");

        FixtureDef platFd = new FixtureDef();
        platFd.getFilter().categoryBits = 0x0001;
        platFd.getFilter().maskBits = (short) 0xFFFF;

        PhysicsComponent platPhysics = new PhysicsComponent();
        platPhysics.setBodyType(BodyType.STATIC);
        platPhysics.setFixtureDef(platFd);

        return FXGL.entityBuilder(data)
                .viewWithBBox(new Rectangle(w, h, color))
                .with(platPhysics)
                .with(new PlatformIdentifierComponent())
                .collidable()
                .zIndex(10)
                .build();
    }

    @Spawns("chest")
    public Entity newChest(SpawnData data) {
        Color color = data.get("color");
        double w = data.get("width");
        double h = data.get("height");

        return FXGL.entityBuilder(data)
                .viewWithBBox(new Rectangle(w, h, color))
                .with(new ChestComponent())
                .collidable()
                .zIndex(-1)
                .buildAndAttach();
    }

}