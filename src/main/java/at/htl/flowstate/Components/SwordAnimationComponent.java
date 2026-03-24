package at.htl.flowstate.Components;

import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;

public class SwordAnimationComponent extends WeaponAnimationComponent {

    private static final double START_ANGLE = 180.0;
    private static final double END_ANGLE = 00.0;

    private final double duration;

    private double elapsed = 0;

    public SwordAnimationComponent(Entity player, int attackWeight, double duration) {
        this.duration = duration;

        super(player, attackWeight);
    }

    @Override
    public void onAdded() {
        entity.setRotationOrigin(new Point2D(entity.getWidth()/2, 0));
        entity.setRotation(getCurrentWatchDirection() > 0 ? START_ANGLE : -START_ANGLE);
    }

    @Override
    public void onUpdate(double tpf) {
        animate(tpf);
    }

    @Override
    protected void animate(double tpf) {
        elapsed += tpf;
        double t = Math.min(elapsed / duration, 1.0);
        double eased = 1 - Math.pow(1 - t, 2);

        double start = getCurrentWatchDirection() > 0 ? START_ANGLE : -START_ANGLE;
        double end = getCurrentWatchDirection() > 0 ? END_ANGLE : -END_ANGLE;

        entity.setRotation(start + (end - start) * eased);
        entity.setPosition(player.getCenter().getX() - entity.getWidth()/2, player.getCenter().getY() - player.getHeight()/4);

        if (t >= 1.0) {
            endAnimation();
        }
    }
}