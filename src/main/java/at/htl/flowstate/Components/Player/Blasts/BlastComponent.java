package at.htl.flowstate.Components.Player.Blasts;

import at.htl.flowstate.Components.Identifier.EnemyIdentifierComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

import java.util.ArrayList;
import java.util.List;

public abstract class BlastComponent extends Component {
    private final double blastRadius;
    private final double blastDuration;

    private final double maxFadeoutTime;
    private double fadeoutTime;

    private final List<Entity> alreadyHit = new ArrayList<>();

    public BlastComponent(double blastRadius, double blastDuration, double fadeoutTime) {
        this.fadeoutTime = fadeoutTime;
        this.blastRadius = blastRadius;
        this.blastDuration = blastDuration;
        maxFadeoutTime = fadeoutTime;
    }

    private double originX;
    private double originY;

    @Override
    public void onAdded() {
        originX = entity.getCenter().getX();
        originY = entity.getCenter().getY();
    }

    @Override
    public void onUpdate(double tpf) {
        if(entity.getScaleY() < blastRadius) {
            checkHit();
            grow(tpf);
        }else {
            fadeout(tpf);
        }
    }

    protected abstract void hitEnemy(Entity e);

    private void checkHit() {
        double radius = entity.getScaleX();

        getEnemies().forEach(e -> {
            double ex = e.getCenter().getX();
            double ey = e.getCenter().getY();
            double dist = Math.hypot(originX - ex, originY - ey);

            if (dist <= radius && !alreadyHit.contains(e)) {
                hitEnemy(e);
                alreadyHit.add(e);
            }
        });
    }

    private void grow(double tpf) {
        double growValue = blastRadius/blastDuration*tpf;

        entity.setScaleX(entity.getScaleX()+growValue);
        entity.setScaleY(entity.getScaleY()+growValue);
        entity.setX(entity.getX()+growValue);
        entity.setY(entity.getY()+growValue);
    }

    private void fadeout(double tpf) {
        fadeoutTime -= tpf;
        entity.setOpacity(fadeoutTime /maxFadeoutTime);

        if(fadeoutTime <= 0) entity.removeFromWorld();
    }

    private List<Entity> getEnemies() {
        return FXGL.getGameWorld().getEntitiesFiltered(
                e -> e.getComponentOptional(EnemyIdentifierComponent.class).isPresent()
        );
    }
}
