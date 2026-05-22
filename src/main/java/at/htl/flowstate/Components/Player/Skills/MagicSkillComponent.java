package at.htl.flowstate.Components.Player.Skills;

import at.htl.flowstate.Components.Player.*;
import at.htl.flowstate.Components.Player.MagicProjectiles.FireballProjectileComponent;
import at.htl.flowstate.Components.Player.MagicProjectiles.IcecicleProjectileComponent;
import at.htl.flowstate.Components.Player.MagicProjectiles.PlayerProjectileComponent;
import at.htl.flowstate.Components.Player.MagicProjectiles.PoisonDartProjectileComponent;
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
    private static final double LEVITATION_MANA_DRAIN_RATE = 5.0;
    private static final double SHIELD_MANA_DRAIN_RATE = 2.0;

    public static final double ENCHANTMENT_LIFESTEAL_RATE = 0.05;
    private static final double ENCHANTMENT_SPEED_MULT = 1.3;
    public static final double ENCHANTMENT_CRITCHANCE_MULT = 2;

    private static final double ENCHANTMENT_ICON_X_OFFSET = 0.955;
    private static final double LEVITATION_ICON_X_OFFSET = 0.930;
    private static final double SHIELD_ICON_X_OFFSET = 0.905;

    private static final double ICON_Y = 145;
    private static final double ICON_SIZE = 40;

    private IconType currentActiveEnchantment = null;
    private boolean isLevitationActive = false;
    private boolean isShieldActive = false;

    private StackPane activeEnchantmentIcon = null;
    private StackPane activeLevitationIcon = null;
    private StackPane activeShieldIcon = null;

    @Override
    public void onUpdate(double tpf) {
        if (currentActiveEnchantment != null) {
            if (!entity.getComponent(PlayerStatsComponent.class).takeMana(ENCHANTMENT_MANA_DRAIN_RATE * tpf))
                deactivateEnchantment();
        }
        if (isLevitationActive) {
            if (!entity.getComponent(PlayerStatsComponent.class).takeMana(LEVITATION_MANA_DRAIN_RATE * tpf))
                deactivateLevitation();
        }
        if (isShieldActive) {
            if (!entity.getComponent(PlayerStatsComponent.class).takeMana(SHIELD_MANA_DRAIN_RATE * tpf))
                deactivateShield();
        }
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
                new PlayerProjectileComponent(20, 1), false
        );
    }

    @Override public void doSub1() {
        //basic arcane
    }
    @Override public void doSub2() {
        //basic elemental
    }
    @Override public void doSub3() {
        activateEnchantment(IconType.Crit);
    }

    //-----Skills of Arcane-----
    @Override public void doSub1Skill1() {
        //magic missile
    }

    @Override
    public void doSub1Skill2() { //mana shield
        if (isShieldActive) deactivateShield();
        else activateShield();
    }

    @Override
    public void doSub1Skill3() { //levitation
        if (isLevitationActive) deactivateLevitation();
        else activateLevitation();
    }

    //-----Skills of Elemental-----
    @Override
    public void doSub2Skill1() {
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
                new FireballProjectileComponent(30, 20, 200, 0.5, 2), false
        );
    }

    @Override
    public void doSub2Skill2() {
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
                new IcecicleProjectileComponent(20, 5), false
        );
    }

    @Override
    public void doSub2Skill3() {
        if (!entity.getComponent(PlayerStatsComponent.class).takeMana(15)) return;

        Point2D dir = getInput().getVectorToMouse(entity.getCenter());
        double spread = Math.toRadians(5);
        double cos = Math.cos(spread);
        double sin = Math.sin(spread);

        Point2D topDir = new Point2D( //calculations for the vector of the top dart
                dir.getX() * cos - dir.getY() * sin,
                dir.getX() * sin + dir.getY() * cos
        );
        Point2D bottomDir = new Point2D( //for the bottom dart
                dir.getX() * cos + dir.getY() * sin,
                -dir.getX() * sin + dir.getY() * cos
        );

        projectileBuilder( //middle dart
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
                new PoisonDartProjectileComponent(10), false
        );
        projectileBuilder( //top dart
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
                new PoisonDartProjectileComponent(10), false
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
                new PoisonDartProjectileComponent(10), false
        );
    }

    //-----Skills of Enchanting-----
    @Override public void doSub3Skill1() { activateEnchantment(IconType.Lifesteal); }
    @Override public void doSub3Skill2() { activateEnchantment(IconType.Piercing); }
    @Override public void doSub3Skill3() { activateEnchantment(IconType.Speed); }

    //-----Enchantment lifecycle-----
    public void activateEnchantment(IconType type) {
        if (currentActiveEnchantment != null) deactivateEnchantment();
        currentActiveEnchantment = type;
        showEnchantmentIcon(type);
    }

    public void deactivateEnchantment() {
        if (currentActiveEnchantment == null) return;
        currentActiveEnchantment = null;
        removeEnchantmentIcon();
    }

    //-----Levitation lifecycle-----
    public void activateLevitation() {
        isLevitationActive = true;
        showLevitationIcon();
    }

    public void deactivateLevitation() {
        isLevitationActive = false;
        removeLevitationIcon();
    }

    //-----Shield lifecycle-----
    public void activateShield() {
        isShieldActive = true;
        showShieldIcon();
    }

    public void deactivateShield() {
        isShieldActive = false;
        removeShieldIcon();
    }

    //-----Icons-----
    private void showEnchantmentIcon(IconType type) {
        activeEnchantmentIcon = buildIcon(type, ENCHANTMENT_ICON_X_OFFSET);
        activeEnchantmentIcon.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) deactivateEnchantment();
        });
        FXGL.getGameScene().addUINode(activeEnchantmentIcon);
    }

    private void removeEnchantmentIcon() {
        if (activeEnchantmentIcon != null) {
            FXGL.getGameScene().removeUINode(activeEnchantmentIcon);
            activeEnchantmentIcon = null;
        }
    }

    private void showLevitationIcon() {
        activeLevitationIcon = buildIcon(IconType.Levitation, LEVITATION_ICON_X_OFFSET);
        activeLevitationIcon.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) deactivateLevitation();
        });
        FXGL.getGameScene().addUINode(activeLevitationIcon);
    }

    private void removeLevitationIcon() {
        if (activeLevitationIcon != null) {
            FXGL.getGameScene().removeUINode(activeLevitationIcon);
            activeLevitationIcon = null;
        }
    }

    private void showShieldIcon() {
        activeShieldIcon = buildIcon(IconType.Shield, SHIELD_ICON_X_OFFSET);
        activeShieldIcon.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) deactivateShield();
        });
        FXGL.getGameScene().addUINode(activeShieldIcon);
    }

    private void removeShieldIcon() {
        if (activeShieldIcon != null) {
            FXGL.getGameScene().removeUINode(activeShieldIcon);
            activeShieldIcon = null;
        }
    }

    private StackPane buildIcon(IconType type, double xOffset) {
        Rectangle bg = new Rectangle(ICON_SIZE, ICON_SIZE, iconColor(type));
        bg.setArcWidth(6);
        bg.setArcHeight(6);

        Text label = new Text(iconLabel(type));
        label.setFill(Color.WHITE);
        label.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");

        StackPane icon = new StackPane(bg, label);
        icon.setAlignment(Pos.CENTER);
        icon.setLayoutX(FXGL.getAppWidth() * xOffset);
        icon.setLayoutY(ICON_Y);
        return icon;
    }

    private Color iconColor(IconType type) {
        return switch (type) {
            case Crit -> Color.ORANGE;
            case Speed -> Color.YELLOW;
            case Lifesteal -> Color.CRIMSON;
            case Piercing -> Color.CYAN;
            case Levitation -> Color.BLUE;
            case Shield -> Color.DARKBLUE;
        };
    }

    private String iconLabel(IconType type) {
        return switch (type) {
            case Crit -> "CRT";
            case Speed -> "SPD";
            case Lifesteal -> "LST";
            case Piercing -> "PRC";
            case Levitation -> "LEV";
            case Shield -> "SLD";
        };
    }

    //-----Builder-----
    public void projectileBuilder(String textureName, double textureScale, double textureOffsetX, double textureOffsetY, double textureRotation, double projWidth, double projHeight, double manaCost, double projSpeed, Point2D target, @NotNull PlayerProjectileComponent specificComponent, boolean debug) {
        if (entity.getComponent(PlayerStatsComponent.class).takeMana(manaCost)) {
            URL url = MeleeSkillComponent.class.getResource("/assets/textures/" + textureName);
            assert url != null;
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

    //-----Getters-----
    public IconType getCurrentActiveEnchantment() { return currentActiveEnchantment; }
    public boolean isLevitationActive() { return isLevitationActive; }
    public boolean isShieldActive() { return isShieldActive; }

    public boolean isManaRegenEnabled() {
        return currentActiveEnchantment == null && !isLevitationActive && !isShieldActive;
    }

    public double getCurrentSpeedMult() {
        if (currentActiveEnchantment == IconType.Speed) return ENCHANTMENT_SPEED_MULT;
        return 1;
    }
}