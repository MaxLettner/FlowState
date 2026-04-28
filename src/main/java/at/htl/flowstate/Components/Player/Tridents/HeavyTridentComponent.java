package at.htl.flowstate.Components.Player.Tridents;

import at.htl.flowstate.Components.Player.Blasts.IceBlastComponent;
import at.htl.flowstate.Components.Player.Blasts.KnockbackBlastComponent;
import com.almasb.fxgl.physics.CircleShapeData;
import com.almasb.fxgl.physics.HitBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class HeavyTridentComponent extends TridentComponent {
    private final double knockbackStrength;
    private final double knockbackRadius;

    public HeavyTridentComponent(double damage, double knockbackStrength, double knockbackRadius) {
        super(damage);
        this.knockbackRadius = knockbackRadius;
        this.knockbackStrength = knockbackStrength;
    }

    @Override
    protected void hitGround() {
        super.hitGround();
        entityBuilder()
                .at(entity.getCenter())
                .bbox(new CircleShapeData(1))
                .with(new KnockbackBlastComponent(entity, knockbackStrength, knockbackRadius))
                .zIndex(5)
                .buildAndAttach();
    }
}
