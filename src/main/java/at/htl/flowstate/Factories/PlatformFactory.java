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


public class PlatformFactory implements EntityFactory {

    @Spawns("platform")
    public Entity newPlatform (SpawnData data) {
        //var texture = FXGL.texture("Brick.png");

        Color color = data.get("color");
        double w = data.get("width");
        double h = data.get("height");

        PhysicsComponent platPhysics = new PhysicsComponent();
        platPhysics.setBodyType(BodyType.STATIC);

        return FXGL.entityBuilder(data)
                .at(data.getX(), data.getY())
                .viewWithBBox(new Rectangle(w, h, color))
                .with(platPhysics)
                .collidable()
                .buildAndAttach();
    }
}
