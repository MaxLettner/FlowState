package at.htl.flowstate.Factories;

import at.htl.flowstate.Components.Enemies.EnemyStatsComponent;
import at.htl.flowstate.Components.Enemies.MeleeEnemyBehaviourComponent;
import at.htl.flowstate.Components.Enemies.RangedEnemyBehaviourComponent;
import at.htl.flowstate.Components.Identifier.EnemyIdentifierComponent;
import at.htl.flowstate.Components.SpriteComponents.SpriteComponent;
import at.htl.flowstate.Game;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EnemyFactory implements EntityFactory {

    private static final short CATEGORY_TERRAIN = 0x0001;
    private static final short CATEGORY_ENEMY = 0x0004;

    @Spawns("meleeEnemy")
    public Entity newEnemy(SpawnData data) {
        PhysicsComponent physics = physicsBuilder();

        return FXGL.entityBuilder(data)
                .view(new Rectangle(40, 80, Color.GREEN))
                .bbox(new HitBox(BoundingShape.box(40, 80)))
                .with(physics)
                .with(new MeleeEnemyBehaviourComponent())
                .with(new EnemyStatsComponent())
                .with(new HealthDoubleComponent(100))
                .with(new EnemyIdentifierComponent())
                .with(new SpriteComponent("Enemy.png", "EnemyWalking.png", "EnemyAirborne.png", "EnemyLanding.png", 100))
                .collidable()
                .build();
    }

    @Spawns("rangedEnemy")
    public Entity newRangedEnemy(SpawnData data) {
        PhysicsComponent physics = physicsBuilder();

        return FXGL.entityBuilder(data)
                .view(new Rectangle(40, 80, Color.GREEN))
                .bbox(new HitBox(BoundingShape.box(40, 80)))
                .with(physics)
                .with(new RangedEnemyBehaviourComponent())
                .with(new EnemyStatsComponent())
                .with(new HealthDoubleComponent(100))
                .with(new EnemyIdentifierComponent())
                .with(new SpriteComponent("Enemy.png", "EnemyWalking.png", "EnemyAirborne.png", "EnemyLanding.png", 100))
                .collidable()
                .build();
    }

    private PhysicsComponent physicsBuilder() {
        FixtureDef enemyFd = new FixtureDef().friction(0).density(1.0f);
        enemyFd.getFilter().categoryBits = CATEGORY_ENEMY;
        enemyFd.getFilter().maskBits = CATEGORY_TERRAIN;

        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(enemyFd);

        return physics;
    }
}