package at.htl.flowstate.Menu;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;

public class MenuFactory extends SceneFactory {
    public FXGLMenu newSkillTreeMenu() {
        return new SkillTree();
    }
}
