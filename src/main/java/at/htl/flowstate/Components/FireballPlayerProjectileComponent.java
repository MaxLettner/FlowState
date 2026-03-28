package at.htl.flowstate.Components;

import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class FireballPlayerProjectileComponent extends PlayerProjectileComponent{
    private final double blastDamage;
    private final double blastRadius;
    private final double blastDuration;

    public FireballPlayerProjectileComponent(double projectileDamage, double blastDamage, double blastRadius, double blastDuration) {
        super(projectileDamage, 1);
        this.blastDamage = blastDamage;
        this.blastRadius = blastRadius;
        this.blastDuration = blastDuration;
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
                .with(new PlayerBlastComponent(blastDamage, blastRadius, blastDuration))
                .zIndex(5)
                .buildAndAttach();
    }
}
