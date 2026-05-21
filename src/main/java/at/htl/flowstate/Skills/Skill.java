package at.htl.flowstate.Skills;

import at.htl.flowstate.Components.Player.PlayerStatsComponent;
import at.htl.flowstate.Game;
import com.almasb.fxgl.entity.Entity;

public class Skill {
        private final String name;
        private boolean isUnlocked;
        private final SkillType type;
        private final int cost;
        private final Entity player;
        private final SubTreeType subTreeType;

        public Skill(SkillType type, int cost, SubTreeType subTreeType) {
            this.name = type.getName();
            this.isUnlocked = false;
            this.type = type;
            this.cost = cost;
            this.player = Game.getPlayer();
            this.subTreeType = subTreeType;
        }

        public String getName() {
            return name;
        }

        public boolean isUnlocked() {
            return isUnlocked;
        }

        public void unlock() {
            isUnlocked = true;
        }

        public SkillType getType() {
            return type;
        }

        public int getCost() {
            return cost;
        }

        public SubTreeType getSubTreeType() {
            return subTreeType;
        }



    public boolean canAfford() {
        return player.getComponent(PlayerStatsComponent.class).getSkillPoints() >= cost;
    }
}
