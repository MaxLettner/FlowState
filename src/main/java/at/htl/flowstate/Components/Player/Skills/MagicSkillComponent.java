package at.htl.flowstate.Components.Player.Skills;

import at.htl.flowstate.Components.Player.*;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import java.net.URL;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class MagicSkillComponent extends SkillComponent {
    private static final double ENCHANTMENT_MANA_DRAIN_RATE = 2.0;
    public static final double ENCHANTMENT_LIFESTEAL_RATE = 0.05;
    private static final double ENCHANTMENT_SPEED_MULT = 1.3;
    public static final double ENCHANTMENT_CRITCHANCE_MULT = 2;

    private static final double ICON_X_OFFSET = 0.955;
    private static final double ICON_Y = 115;
    private static final double ICON_SIZE = 40;

    private EnchantmentType currentActiveEnchantment = null;

    private StackPane activeIcon = null;

    @Override
    public void onUpdate(double tpf) {
        if (currentActiveEnchantment == null) return;
        boolean hasMana = entity.getComponent(PlayerStatsComponent.class).takeMana(ENCHANTMENT_MANA_DRAIN_RATE * tpf);
        if (!hasMana) deactivate();
    }

    //-----Skill actions-----
    @Override
    public void doDefault() {
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

    //-----Subskilltrees
    @Override
    public void doSub1() { //Arcane

    }

    @Override
    public void doSub2() { //Elemental

    }

    @Override
    public void doSub3() { //Enchanting
        activate(EnchantmentType.Crit);
    }

    //-----Skills of Arcane-----
    @Override
    public void doSub1Skill1() { //magic missile

    }

    @Override
    public void doSub1Skill2() { //mana shield

    }

    @Override
    public void doSub1Skill3() { //levitation

    }

    //-----Skills of Elemental-----
    @Override
    public void doSub2Skill1() { //fireball
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
    public void doSub2Skill2() { //icecicle
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
    public void doSub2Skill3() { //poison darts
        if (!entity.getComponent(PlayerStatsComponent.class).takeMana(15)) return;

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

    //-----Skills of Enchanting-----
    @Override
    public void doSub3Skill1() { //Lifesteal
        activate(EnchantmentType.Lifesteal);
    }

    @Override
    public void doSub3Skill2() { //piercing
        activate(EnchantmentType.Piercing);
    }

    @Override public void doSub3Skill3() { //speed
        activate(EnchantmentType.Speed);
    }

    //-----Builder-----
    public void projectileBuilder(String textureName, double textureScale, double textureOffsetX, double textureOffsetY, double textureRotation, double projWidth, double projHeight, double manaCost, double projSpeed, Point2D target, @NotNull PlayerProjectileComponent specificComponent, boolean debug) {
        if (entity.getComponent(PlayerStatsComponent.class).takeMana(manaCost)) {
            URL url = MeeleSkillComponent.class.getResource("/assets/textures/" + textureName);
            Texture texture = new Texture(new Image(url.toExternalForm(), textureScale, textureScale, false, true));
            texture.setRotate(textureRotation);
            texture.setTranslateX(textureOffsetX);
            texture.setTranslateY(textureOffsetY);

            if (debug) {
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
            } else {
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

    //-----Enchantment lifecycle-----
    public void activate(EnchantmentType type) {
        if (currentActiveEnchantment != null) deactivate();
        currentActiveEnchantment = type;
        showIcon(type);
    }

    public void deactivate() {
        if (currentActiveEnchantment == null) return;
        currentActiveEnchantment = null;
        removeIcon();
    }

    //-----Icon-----
    private void showIcon(EnchantmentType type) {
        Rectangle bg = new Rectangle(ICON_SIZE, ICON_SIZE, iconColor(type));
        bg.setArcWidth(6);
        bg.setArcHeight(6);

        Text label = new Text(iconLabel(type));
        label.setFill(Color.WHITE);
        label.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");

        activeIcon = new StackPane(bg, label);
        activeIcon.setAlignment(Pos.CENTER);
        activeIcon.setLayoutX(FXGL.getAppWidth() * ICON_X_OFFSET);
        activeIcon.setLayoutY(ICON_Y);

        activeIcon.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) deactivate();
        });

        FXGL.getGameScene().addUINode(activeIcon);
    }

    private void removeIcon() {
        if (activeIcon != null) {
            FXGL.getGameScene().removeUINode(activeIcon);
            activeIcon = null;
        }
    }

    private Color iconColor(EnchantmentType type) {
        return switch (type) {
            case Crit -> Color.ORANGE;
            case Speed -> Color.YELLOW;
            case Lifesteal -> Color.CRIMSON;
            case Piercing -> Color.CYAN;
        };
    }

    private String iconLabel(EnchantmentType type) {
        return switch (type) {
            case Crit -> "CRT";
            case Speed -> "SPD";
            case Lifesteal -> "LST";
            case Piercing -> "PRC";
        };
    }

    //-----Getters-----
    public EnchantmentType getCurrentActiveEnchantment() {
        return currentActiveEnchantment;
    }

    public boolean isManaRegenEnabled() {
        return currentActiveEnchantment == null; //needs to be changed when levitation and manashield are added
    }

    public double getCurrentSpeedMult() {
        if(currentActiveEnchantment == EnchantmentType.Speed) return ENCHANTMENT_SPEED_MULT;
        return 1;
    }
}