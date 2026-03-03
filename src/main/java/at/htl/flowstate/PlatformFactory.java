package at.htl.flowstate;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.shape.Rectangle;


public class PlatformFactory implements EntityFactory {
    private int x = 1;
    private int y = 1;

    @Spawns("platform")
    public Entity newPlatform (SpawnData data) {
        var texture = FXGL.texture("Brick.png");

        PhysicsComponent platPhysics = new PhysicsComponent();
        platPhysics.setBodyType(BodyType.STATIC);

        return FXGL.entityBuilder(data)
                .at(x, y)
                .viewWithBBox(texture)
                .with(platPhysics)
                .collidable()
                .buildAndAttach();
    }
}
