package at.htl.flowstate.Components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

public class SwingComponent extends Component {

    private static final double START_ANGLE = 180.0;
    private static final double END_ANGLE = 0.0;

    private final Entity player;
    private final double duration;

    private double elapsed = 0;

    public SwingComponent(Entity player, double duration) {
        this.player = player;
        this.duration = duration;
    }

    @Override
    public void onAdded() {
        entity.setRotationOrigin(new Point2D(20, 0));
        entity.setRotation(getDirection() > 0 ? START_ANGLE : -START_ANGLE);
    }

    @Override
    public void onUpdate(double tpf) {
        elapsed += tpf;
        double t = Math.min(elapsed / duration, 1.0);
        double eased = 1 - Math.pow(1 - t, 2);

        double start = getDirection() > 0 ? START_ANGLE : -START_ANGLE;
        double end = getDirection() > 0 ? END_ANGLE   : -END_ANGLE;

        entity.setRotation(start + (end - start) * eased);
        entity.setPosition(player.getCenter().getX() - 20, player.getCenter().getY() - 10);

        if (t >= 1.0) entity.removeFromWorld();
    }

    public int getDirection() {
        return player.getComponent(PlayerComponent.class).getLastMoveDirection() * -1;
    }
}