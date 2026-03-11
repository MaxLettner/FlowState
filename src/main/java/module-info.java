module at.htl.flowstate {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens at.htl.flowstate to com.almasb.fxgl.core;
    exports at.htl.flowstate;
    exports at.htl.flowstate.Factories;
    opens at.htl.flowstate.Factories to com.almasb.fxgl.core;
    opens at.htl.flowstate.Components to com.almasb.fxgl.core;
    exports at.htl.flowstate.Components;
    exports at.htl.flowstate.Generation;
    opens at.htl.flowstate.Generation to com.almasb.fxgl.core;
}