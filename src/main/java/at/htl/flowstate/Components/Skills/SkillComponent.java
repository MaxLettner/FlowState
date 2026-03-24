package at.htl.flowstate.Components.Skills;

import com.almasb.fxgl.entity.component.Component;

public abstract class SkillComponent extends Component {
    public abstract void doDefault();

    public abstract void doSub1();
    public abstract void doSub2();
    public abstract void doSub3();

    public abstract void doSub1Skill1();
    public abstract void doSub1Skill2();
    public abstract void doSub1Skill3();

    public abstract void doSub2Skill1();
    public abstract void doSub2Skill2();
    public abstract void doSub2Skill3();

    public abstract void doSub3Skill1();
    public abstract void doSub3Skill2();
    public abstract void doSub3Skill3();

}
