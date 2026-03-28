package at.htl.flowstate.Components;

import at.htl.flowstate.Components.Identifier.EnemyIdentifierComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

import java.util.ArrayList;
import java.util.List;

public class PlayerBlastComponent extends Component {
    private final double blastDamage;
    private final double blastRadius;
    private final double blastDuration;

    private static final double FADEOUT_TIME = 2;
    private double fadeoutTimer;

    private final List<Entity> alreadyHit = new ArrayList<>();

    public PlayerBlastComponent(double blastDamage, double blastRadius, double blastDuration) {
        this.blastDamage = blastDamage;
        this.blastRadius = blastRadius;
        this.blastDuration = blastDuration;
    }

    @Override
    public void onAdded() {
        fadeoutTimer = FADEOUT_TIME;
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

    private void checkHit() {
        getEnemies().forEach(e -> {
            if(entity.isColliding(e) && !alreadyHit.contains(e)) {
                e.getComponent(HealthDoubleComponent.class).damage(blastDamage);
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
        fadeoutTimer -= tpf;
        entity.setOpacity(fadeoutTimer/FADEOUT_TIME);

        if(fadeoutTimer <= 0) entity.removeFromWorld();
    }

    private List<Entity> getEnemies() {
        return FXGL.getGameWorld().getEntitiesFiltered(
                e -> e.getComponentOptional(EnemyIdentifierComponent.class).isPresent()
        );
    }
}
