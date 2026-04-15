package at.htl.flowstate.Components.Player;

import at.htl.flowstate.Components.Enemies.EnemyStatsComponent;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class IceTridentComponent extends TridentComponent {
    private final double freezeDuration;
    private final double blastRadius;
    private final double blastDuration;
    private final double blastFadeoutTime;

    public IceTridentComponent(double damage, double freezeDuration, double blastRadius, double blastDuration, double blastFadeoutTime) {
        this.freezeDuration = freezeDuration;
        this.blastRadius = blastRadius;
        this.blastDuration = blastDuration;
        this.blastFadeoutTime = blastFadeoutTime;
        super(damage);
    }

    @Override
    protected void hitGround() {
        super.hitGround();
        createBlast();

    }

    @Override
    protected void hitEnemy(Entity e) {
        e.getComponent(EnemyStatsComponent.class).stun(freezeDuration);
        super.hitEnemy(e);
    }

    private void createBlast() {
        entityBuilder()
                .at(entity.getCenter())
                .viewWithBBox(new Circle(1, Color.BLUE))
                .with(new IceBlastComponent(freezeDuration, blastRadius, blastDuration, blastFadeoutTime))
                .zIndex(5)
                .buildAndAttach();
    }
}
