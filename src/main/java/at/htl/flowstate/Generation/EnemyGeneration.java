package at.htl.flowstate.Generation;

import static com.almasb.fxgl.dsl.FXGL.spawn;
import com.almasb.fxgl.entity.SpawnData;

public class EnemyGeneration {
    private static final double RANGED_CHANCE = 0.2;
    private static final double GANG_CHANCE = 0.05;

    public void spawnEnemy(double x, double y) {
        if(Math.random() < RANGED_CHANCE) {
            spawn("rangedEnemy", new SpawnData(x, y));
        }else {
            int count = Math.random() < GANG_CHANCE ? 5 : 1;
            for(int i = 0;i < count;i++) {
                spawn("meleeEnemy", new SpawnData(x, y));
            }

        }
    }
}

