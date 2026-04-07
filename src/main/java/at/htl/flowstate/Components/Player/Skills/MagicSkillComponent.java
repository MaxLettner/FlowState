package at.htl.flowstate.Components.Player.Skills;

import at.htl.flowstate.Components.Player.*;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.net.URL;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class MagicSkillComponent extends SkillComponent{
    boolean canRegenMana = true;
    EnchantmentType currentActiveEnchantment = null;

    @Override
    public void doDefault() { //magic projectile
        projectileBuilder(
                "MagicMissile.png",
                80,
                -30,
                -28.5,
                0,
                20,
                20,
                20,
                600,
                getInput().getVectorToMouse(entity.getCenter()),
                new PlayerProjectileComponent(20, 1),
                false
        );
    }

    @Override
    public void doSub1() { //Arcane

    }

    @Override
    public void doSub2() { //Elemental

    }

    @Override
    public void doSub3() { //Enchanting -> crit

    }

    //-----Arcane-----
    @Override
    public void doSub1Skill1() { //Magic Missile

    }

    @Override
    public void doSub1Skill2() { //Mana Shield

    }

    @Override
    public void doSub1Skill3() { //Levitation

    }

    //-----Elemental-----
    @Override
    public void doSub2Skill1() { //Fireball
        projectileBuilder(
                "FireBall.png",
                130,
                -50,
                -52,
                0,
                30,
                30,
                30,
                500,
                getInput().getVectorToMouse(entity.getCenter()),
                new FireballPlayerProjectileComponent(30, 20, 200, 0.5),
                false
        );
    }

    @Override
    public void doSub2Skill2() { //Icecicle
        projectileBuilder(
                "Icecicle.png",
                80,
                -15,
                -31,
                0,
                50,
                15,
                15,
                1000,
                getInput().getVectorToMouse(entity.getCenter()),
                new IceciclePlayerProjectileComponent(20, 5),
                false
        );
    }

    @Override
    public void doSub2Skill3() { //Poison Darts
        if(!entity.getComponent(PlayerStatsComponent.class).takeMana(15)) return;

        Point2D dir = getInput().getVectorToMouse(entity.getCenter());
        double spread = Math.toRadians(5);

        double cos = Math.cos(spread);
        double sin = Math.sin(spread);

        Point2D topDir = new Point2D(
                dir.getX() * cos - dir.getY() * sin,
                dir.getX() * sin + dir.getY() * cos);
        Point2D bottomDir = new Point2D(
                dir.getX() * cos + dir.getY() * sin,
                -dir.getX() * sin + dir.getY() * cos);

        //middle
        projectileBuilder(
                "PoisonDart.png",
                80,
                -30,
                -31,
                0,
                30,
                15,
                0,
                800,
                dir,
                new PoisonDartPlayerProjectileComponent(10),
                false
        );
        //top
        projectileBuilder(
                "PoisonDart.png",
                80,
                -30,
                -31,
                0,
                30,
                15,
                0,
                800,
                topDir,
                new PoisonDartPlayerProjectileComponent(10),
                false
        );
        //bottom
        projectileBuilder(
                "PoisonDart.png",
                80,
                -30,
                -31,
                0,
                30,
                15,
                0,
                800,
                bottomDir,
                new PoisonDartPlayerProjectileComponent(10),
                false
        );
    }

    //-----Enchanting----
    @Override
    public void doSub3Skill1() { //Life Steal

    }

    @Override
    public void doSub3Skill2() { //Piercing

    }

    @Override
    public void doSub3Skill3() { //?

    }

    public void projectileBuilder(String textureName, double textureScale, double textureOffsetX, double textureOffsetY, double textureRotation, double projWidth, double projHeight, double manaCost, double projSpeed, Point2D target, @NotNull PlayerProjectileComponent specificComponent, boolean debug) {
        if(entity.getComponent(PlayerStatsComponent.class).takeMana(manaCost)) {
            URL url = MeeleSkillComponent.class.getResource("/assets/textures/" + textureName);
            Texture texture = new Texture(new Image(url.toExternalForm(), textureScale, textureScale, false, true));
            texture.setRotate(textureRotation);
            texture.setTranslateX(textureOffsetX);
            texture.setTranslateY(textureOffsetY);

            if(debug) {
                entityBuilder()
                        .at(entity.getCenter())
                        .bbox(new HitBox(BoundingShape.box(projWidth, projHeight)))
                        .view(new Rectangle(projWidth, projHeight))
                        .view(texture)
                        .with(new ProjectileComponent(target, projSpeed))
                        .with(specificComponent)
                        .with(new OffscreenCleanComponent())
                        .zIndex(-1)
                        .buildAndAttach();
            }else {
                entityBuilder()
                        .at(entity.getCenter())
                        .bbox(new HitBox(BoundingShape.box(projWidth, projHeight)))
                        .view(texture)
                        .with(new ProjectileComponent(target, projSpeed))
                        .with(specificComponent)
                        .with(new OffscreenCleanComponent())
                        .zIndex(-1)
                        .buildAndAttach();
            }

        }

    }

    private void enchantmentBuilder() {

    }

    public boolean getCanRegenMana() {
        return canRegenMana;
    }

    public void setCanRegenMana(boolean canRegenMana) {
        this.canRegenMana = canRegenMana;
    }

    public EnchantmentType getCurrentActiveEnchantment() {
        return currentActiveEnchantment;
    }

    public void setCurrentActiveEnchantment(EnchantmentType currentActiveEnchantment) {
        this.currentActiveEnchantment = currentActiveEnchantment;
    }
}
