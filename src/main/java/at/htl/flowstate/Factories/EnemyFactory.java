package at.htl.flowstate.Factories;

import at.htl.flowstate.Components.Enemies.EnemyStatsComponent;
import at.htl.flowstate.Components.Enemies.MeleeEnemyBehaviourComponent;
import at.htl.flowstate.Components.Enemies.RangedEnemyBehaviourComponent;
import at.htl.flowstate.Components.Identifier.EnemyIdentifierComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EnemyFactory implements EntityFactory {

    private static final short CATEGORY_TERRAIN = 0x0001;
    private static final short CATEGORY_ENEMY   = 0x0004;

    @Spawns("meleeEnemy")
    public Entity newEnemy(SpawnData data) {
        Entity player = data.get("player");

        FixtureDef enemyFd = new FixtureDef().friction(0).density(1.0f);
        enemyFd.getFilter().categoryBits = CATEGORY_ENEMY;
        enemyFd.getFilter().maskBits = CATEGORY_TERRAIN;

        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(enemyFd);

        return FXGL.entityBuilder(data)
                .viewWithBBox(new Rectangle(40, 80, Color.GREEN))
                .with(physics)
                .with(new MeleeEnemyBehaviourComponent(player))
                .with(new EnemyStatsComponent())
                .with(new HealthDoubleComponent(100))
                .with(new EnemyIdentifierComponent())
                .collidable()
                .build();
    }

    @Spawns("rangedEnemy")
    public Entity newRangedEnemy(SpawnData data) {
        Entity player = data.get("player");

        FixtureDef enemyFd = new FixtureDef().friction(0).density(1.0f);
        enemyFd.getFilter().categoryBits = CATEGORY_ENEMY;
        enemyFd.getFilter().maskBits = CATEGORY_TERRAIN;

        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(enemyFd);

        return FXGL.entityBuilder(data)
                .viewWithBBox(new Rectangle(40, 80, Color.GREEN))
                .with(physics)
                .with(new RangedEnemyBehaviourComponent(player))
                .with(new EnemyStatsComponent())
                .with(new HealthDoubleComponent(100))
                .with(new EnemyIdentifierComponent())
                .collidable()
                .build();
    }
}