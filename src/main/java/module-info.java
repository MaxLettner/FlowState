module at.htl.flowstate {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires annotations;
    requires jdk.security.jgss;

    opens at.htl.flowstate to com.almasb.fxgl.core;
    exports at.htl.flowstate;
    exports at.htl.flowstate.Factories;
    opens at.htl.flowstate.Factories to com.almasb.fxgl.core;
    opens at.htl.flowstate.Components to com.almasb.fxgl.core;
    exports at.htl.flowstate.Components;
    exports at.htl.flowstate.Generation;
    opens at.htl.flowstate.Generation to com.almasb.fxgl.core;
    exports at.htl.flowstate.Components.Identifier;
    opens at.htl.flowstate.Components.Identifier to com.almasb.fxgl.core;
    exports at.htl.flowstate.Components.Player.Melee.AttackAnimations;
    opens at.htl.flowstate.Components.Player.Melee.AttackAnimations to com.almasb.fxgl.core;
    exports at.htl.flowstate.Components.Enemies;
    opens at.htl.flowstate.Components.Enemies to com.almasb.fxgl.core;
    exports at.htl.flowstate.Components.Player;
    opens at.htl.flowstate.Components.Player to com.almasb.fxgl.core;
    exports at.htl.flowstate.Components.Player.Skills;
    opens at.htl.flowstate.Components.Player.Skills to com.almasb.fxgl.core;
    exports at.htl.flowstate.Components.Player.Blasts;
    opens at.htl.flowstate.Components.Player.Blasts to com.almasb.fxgl.core;
    exports at.htl.flowstate.Components.Player.Tridents;
    opens at.htl.flowstate.Components.Player.Tridents to com.almasb.fxgl.core;
    exports at.htl.flowstate.Components.Player.MagicProjectiles;
    opens at.htl.flowstate.Components.Player.MagicProjectiles to com.almasb.fxgl.core;
    exports at.htl.flowstate.Components.Player.Melee;
    opens at.htl.flowstate.Components.Player.Melee to com.almasb.fxgl.core;
    exports at.htl.flowstate.Components.Player.Helpers;
    opens at.htl.flowstate.Components.Player.Helpers to com.almasb.fxgl.core;
}