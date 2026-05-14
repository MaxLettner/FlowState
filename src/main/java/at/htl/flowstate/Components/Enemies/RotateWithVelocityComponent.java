package at.htl.flowstate.Components.Enemies;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.Body;

public class RotateWithVelocityComponent extends Component {
    private PhysicsComponent physics;

    @Override
    public void onAdded() {
        physics = entity.getComponent(PhysicsComponent.class);
    }

    @Override
    public void onUpdate(double tpf) {
        Body body = physics.getBody();
        double vx = body.getLinearVelocity().x;
        double vy = body.getLinearVelocity().y;
        if (Math.abs(vx) > 0.01 || Math.abs(vy) > 0.01) {
            double angle = Math.toDegrees(Math.atan2(-vy, vx));
            entity.setRotation(angle);
        }
    }
}