module at.htl.flowstate {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens at.htl.flowstate to com.almasb.fxgl.core;
    exports at.htl.flowstate;
}