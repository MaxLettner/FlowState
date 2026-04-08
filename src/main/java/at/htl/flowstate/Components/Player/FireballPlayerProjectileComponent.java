package at.htl.flowstate.Components.Player;

import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class FireballPlayerProjectileComponent extends PlayerProjectileComponent{
    private final double blastDamage;
    private final double blastRadius;
    private final double blastDuration;
    private final double blastFadeoutTime;

    public FireballPlayerProjectileComponent(double projectileDamage, double blastDamage, double blastRadius, double blastDuration, double blastFadeoutTime) {
        super(projectileDamage, 1);
        this.blastDamage = blastDamage;
        this.blastRadius = blastRadius;
        this.blastDuration = blastDuration;
        this.blastFadeoutTime = blastFadeoutTime;
    }

    @Override
    protected void hitEnemy(Entity e) {
        createBlast();
    }

    @Override
    protected void hitGround() {
        createBlast();
    }

    private void createBlast() {
        entityBuilder()
                .at(entity.getCenter())
                .viewWithBBox(new Circle(1, Paint.valueOf("#835142")))
                .with(new ExplosionBlastComponent(blastDamage, blastRadius, blastDuration, blastFadeoutTime))
                .zIndex(5)
                .buildAndAttach();
    }
}
