package at.htl.flowstate.Components.Player;

import at.htl.flowstate.Components.Player.Skills.MagicSkillComponent;
import at.htl.flowstate.Components.Player.Skills.MeeleSkillComponent;
import at.htl.flowstate.Components.Player.Skills.RangedSkillComponent;
import com.almasb.fxgl.entity.component.Component;

public class PlayerRouterComponent extends Component {
    private String currentSelection = "";

    private MeeleSkillComponent meele;
    private RangedSkillComponent ranged;
    private MagicSkillComponent magic;

    @Override
    public void onAdded() {
        meele = entity.getComponent(MeeleSkillComponent.class);
        ranged = entity.getComponent(RangedSkillComponent.class);
        magic = entity.getComponent(MagicSkillComponent.class);
    }

    public void setCurrentSelection(String selection){
        currentSelection = selection;
    }

    public void doCurrentAction() {
        switch(currentSelection) {
            //meele
            case "M00": meele.doDefault();break;
            //swords
            case "M10": meele.doSub1();break;
            case "M11": meele.doSub1Skill1();break;
            case "M12": meele.doSub1Skill2();break;
            case "M13": meele.doSub1Skill3();break;
            //fisticuffs
            case "M20": meele.doSub2();break;
            case "M21": meele.doSub2Skill1();break;
            case "M22": meele.doSub2Skill2();break;
            case "M23": meele.doSub2Skill3();break;
            //blunt
            case "M30": meele.doSub3();break;
            case "M31": meele.doSub3Skill1();break;
            case "M32": meele.doSub3Skill2();break;
            case "M33": meele.doSub3Skill3();break;
            //ranged
            case "R00": ranged.doDefault();break;
            //bows
            case "R10": ranged.doSub1();break;
            case "R11": ranged.doSub1Skill1();break;
            case "R12": ranged.doSub1Skill2();break;
            case "R13": ranged.doSub1Skill3();break;
            //crossbows
            case "R20": ranged.doSub2();break;
            case "R21": ranged.doSub2Skill1();break;
            case "R22": ranged.doSub2Skill2();break;
            case "R23": ranged.doSub2Skill3();break;
            //tridents
            case "R30": ranged.doSub3();break;
            case "R31": ranged.doSub3Skill1();break;
            case "R32": ranged.doSub3Skill2();break;
            case "R33": ranged.doSub3Skill3();break;
            //magic
            case "A00": magic.doDefault();break;
            //arcane
            case "A10": magic.doSub1();break;
            case "A11": magic.doSub1Skill1();break;
            case "A12": magic.doSub1Skill2();break;
            case "A13": magic.doSub1Skill3();break;
            //elemental
            case "A20": magic.doSub2();break;
            case "A21": magic.doSub2Skill1();break;
            case "A22": magic.doSub2Skill2();break;
            case "A23": magic.doSub2Skill3();break;
            //enchanting
            case "A30": magic.doSub3();break;
            case "A31": magic.doSub3Skill1();break;
            case "A32": magic.doSub3Skill2();break;
            case "A33": magic.doSub3Skill3();break;
            //start
            default: meele.doStart();break;
        }
    }
}
