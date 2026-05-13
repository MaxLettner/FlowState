package at.htl.flowstate.Components.Enemies;

import at.htl.flowstate.Components.Player.Helpers.DeleteAfterTimeComponent;
import at.htl.flowstate.Components.Player.Skills.MeleeSkillComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.text;

public class RangedEnemyBehaviourComponent extends EnemyBehaviourComponent {
    private static final double ATTACK_INTERVAL = 2.0;
    private static final double PROJECTILE_SPEED = 800;
    private static final double GRAVITY = 800; //must match Game.java initPhysics

    private static final double DAMAGE = 20;

    private boolean inAttackMode = false;
    private double attackCooldown = 0;

    public RangedEnemyBehaviourComponent() {

    }

    @Override
    public void onUpdate(double tpf) {
        if (attackCooldown > 0) attackCooldown -= tpf;
        super.onUpdate(tpf);
    }

    @Override
    protected void chasePlayer() {
        double dx = player.getX() - entity.getX();
        int direction;

        if (dx < 500 && dx > -500) {
            inAttackMode = true;
            direction = 0;
        } else {
            inAttackMode = false;
            direction = dx > 0 ? 1 : -1;
        }

        physics.setVelocityX(specificMoveSpeed * direction);

        if (isGrounded) {
            tryStep(direction);
            tryJump(direction);
        }
    }

    @Override
    public void attack() {
        if (!inAttackMode || attackCooldown > 0) return;

        Point2D vel = calculateBallisticVelocity();
        if (vel == null) return; //target out of range

        //offset spawn so the arrow clears the enemies own collider
        double spawnX = entity.getCenter().getX() + Math.signum(vel.getX()) * 30;
        double spawnY = entity.getCenter().getY();

        PhysicsComponent projPhysics = new PhysicsComponent();
        projPhysics.setBodyType(BodyType.DYNAMIC);
        projPhysics.setOnPhysicsInitialized(() -> {
            projPhysics.setVelocityX(vel.getX());
            projPhysics.setVelocityY(vel.getY());
            projPhysics.getBody().setFixedRotation(false);
        });

        URL url = MeleeSkillComponent.class.getResource("/assets/textures/Spear.png");
        assert url != null;
        Texture texture = new Texture(new Image(url.toExternalForm(), 100, 100, false, true));

        //TODO: fix texture

        entityBuilder()
                .from(new SpawnData(spawnX, spawnY))
                .view(texture)
                .viewWithBBox(new Rectangle(14, 4, Color.SADDLEBROWN))
                .with(projPhysics)
                .with(new EnemyProjectileComponent(DAMAGE))
                .with(new DeleteAfterTimeComponent(10))
                .with(new OffscreenCleanComponent())
                .buildAndAttach();

        attackCooldown = ATTACK_INTERVAL;
    }

    //DO NOT TOUCH THIS UNLESS YOU KNOW EXACTLY HOW MATH MATHING
    private Point2D calculateBallisticVelocity() {
        double dx = player.getCenter().getX() - entity.getCenter().getX();
        double dy = player.getCenter().getY() - entity.getCenter().getY();
        double s = PROJECTILE_SPEED;
        double g = GRAVITY;

        double a = 0.25 * g * g;
        double b = -(s * s + g * dy);
        double c = dx * dx + dy * dy;

        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) return null;

        double sqrt = Math.sqrt(discriminant);
        double u1 = (-b + sqrt) / (2 * a);
        double u2 = (-b - sqrt) / (2 * a);

        double u;
        if (u1 > 0 && u2 > 0) u = Math.min(u1, u2);
        else if (u1 > 0) u = u1;
        else if (u2 > 0) u = u2;
        else return null;

        double T = Math.sqrt(u);
        double vx = dx / T;
        double vy = (dy - 0.5 * g * T * T) / T;

        return new Point2D(vx, vy);
    }
}