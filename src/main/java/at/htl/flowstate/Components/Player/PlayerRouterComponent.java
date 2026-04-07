package at.htl.flowstate.Components.Player;

import at.htl.flowstate.Components.Player.Skills.MagicSkillComponent;
import at.htl.flowstate.Components.Player.Skills.MeeleSkillComponent;
import at.htl.flowstate.Components.Player.Skills.RangedSkillComponent;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.entity.component.Component;

public class PlayerRouterComponent extends Component {
    private SkillType currentSelection = SkillType.START;

    private MeeleSkillComponent meele;
    private RangedSkillComponent ranged;
    private MagicSkillComponent magic;

    @Override
    public void onAdded() {
        meele = entity.getComponent(MeeleSkillComponent.class);
        ranged = entity.getComponent(RangedSkillComponent.class);
        magic = entity.getComponent(MagicSkillComponent.class);
    }

    public void setCurrentSelection(SkillType selection){
        currentSelection = selection;
    }

    public void doCurrentAction() {
        switch(currentSelection) {
            //start
            case SkillType.START: meele.doStart();break;
            //meele
            case SkillType.MEELE: meele.doDefault();break;
            //swords
            case SkillType.MEELE_SWORDS: meele.doSub1();break;
            case SkillType.SWORDS_SHORTSWORD: meele.doSub1Skill1();break;
            case SkillType.SWORDS_DUAL_WIELDING: meele.doSub1Skill2();break;
            case SkillType.SWORDS_ZWEIHANDER: meele.doSub1Skill3();break;
            //fisticuffs
            case SkillType.MEELE_FISTICUFFS: meele.doSub2();break;
            case SkillType.FISTICUFFS_LEATHER: meele.doSub2Skill1();break;
            case SkillType.FISTICUFFS_METAL_GLOVES: meele.doSub2Skill2();break;
            case SkillType.FISTICUFFS_SPIKE_GLOVES: meele.doSub2Skill3();break;
            //blunt
            case SkillType.MEELE_BLUNT: meele.doSub3();break;
            case SkillType.BLUNT_HAMMER: meele.doSub3Skill1();break;
            case SkillType.BLUNT_MORNINGSTAR: meele.doSub3Skill2();break;
            case SkillType.BLUNT_SPRING_HAMMER: meele.doSub3Skill3();break;
            //ranged
            case SkillType.RANGED: ranged.doDefault();break;
            //bows
            case SkillType.RANGED_BOW: ranged.doSub1();break;
            case SkillType.BOW_SHORTBOW: ranged.doSub1Skill1();break;
            case SkillType.BOW_BONE_BOW: ranged.doSub1Skill2();break;
            case SkillType.BOW_WAR_BOW: ranged.doSub1Skill3();break;
            //crossbows
            case SkillType.RANGED_CROSSBOW: ranged.doSub2();break;
            case SkillType.CROSSBOW_DUAL_CROSSBOW: ranged.doSub2Skill1();break;
            case SkillType.CROSSBOW_POISON_CROSSBOW: ranged.doSub2Skill2();break;
            case SkillType.CROSSBOW_HEAVY_CROSSBOW: ranged.doSub2Skill3();break;
            //tridents
            case SkillType.RANGED_TRIDENT: ranged.doSub3();break;
            case SkillType.TRIDENT_RECALL_TRIDENT: ranged.doSub3Skill1();break;
            case SkillType.TRIDENT_ICE_TRIDENT: ranged.doSub3Skill2();break;
            case SkillType.TRIDENT_HEAVY_TRIDENT: ranged.doSub3Skill3();break;
            //magic
            case SkillType.MAGIC: magic.doDefault();break;
            //arcane
            case SkillType.MAGIC_ARCANE: magic.doSub1();break;
            case SkillType.ARCANE_MAGIC_MISSILE: magic.doSub1Skill1();break;
            case SkillType.ARCANE_MANA_SHIELD: magic.doSub1Skill2();break;
            case SkillType.ARCANE_LEVITATION: magic.doSub1Skill3();break;
            //elemental
            case SkillType.MAGIC_ELEMENTAL: magic.doSub2();break;
            case SkillType.ELEMENTAL_FIRE_BALL: magic.doSub2Skill1();break;
            case SkillType.ELEMENTAL_ICECICLE: magic.doSub2Skill2();break;
            case SkillType.ELEMENTAL_POISON_DARTS: magic.doSub2Skill3();break;
            //enchanting
            case SkillType.MAGIC_ENCHANTING: magic.doSub3();break;
            case SkillType.ENCHANTING_LIFE_STEAL: magic.doSub3Skill1();break;
            case SkillType.ENCHANTING_PIERCING: magic.doSub3Skill2();break;
            case SkillType.ENCHANTING_SPEED: magic.doSub3Skill3();break;
        }
    }
}
