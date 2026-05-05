package at.htl.flowstate.Components.Player.Helpers;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

public class HomingProjectileComponent extends Component {

    private final Entity target;
    private final double speed;
    private final double maxTurnRateRad;
    private boolean isActive;
    private boolean initialized;

    private double directionAngle;

    public HomingProjectileComponent(Entity target, double speed, double maxTurnDegreesPerSecond) {
        this.target = target;
        this.speed = speed;
        this.maxTurnRateRad = Math.toRadians(maxTurnDegreesPerSecond);
        isActive = false;
        initialized = false;
        directionAngle = 0;
    }

    @Override
    public void onUpdate(double tpf) {
        if (!isActive) return; //instant return if the component is not active

        if (!initialized) {
            directionAngle = angleToTarget();
            initialized = true;
        }

        steer(tpf);
        progressMovement(tpf);
    }

    public void start() {
        isActive = true;
    }

    public void stop() {
        isActive = false;
    }

    private void steer(double tpf) {
        double targetAngle = angleToTarget();
        double delta = normalizeAngle(targetAngle - directionAngle);
        double maxTurn = maxTurnRateRad * tpf;

        if (Math.abs(delta) <= maxTurn) {
            directionAngle = targetAngle;
        } else {
            directionAngle += Math.signum(delta) * maxTurn;
        }
    }

    private void progressMovement(double tpf) {
        double dx = Math.cos(directionAngle) * speed * tpf;
        double dy = Math.sin(directionAngle) * speed * tpf;
        entity.translate(dx, dy);
    }

    private double angleToTarget() {
        Point2D selfCenter = entity.getCenter();
        Point2D targetCenter = target.getCenter();
        double dx = targetCenter.getX() - selfCenter.getX();
        double dy = targetCenter.getY() - selfCenter.getY();
        return Math.atan2(dy, dx);
    }

    private double normalizeAngle(double angle) {
        while (angle >  Math.PI) angle -= 2 * Math.PI;
        while (angle < -Math.PI) angle += 2 * Math.PI;
        return angle;
    }
}