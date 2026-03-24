package at.htl.flowstate.Menu;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GameMenu extends FXGLMenu {

    public GameMenu() {
        super(MenuType.GAME_MENU);

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20;");

        // Title
        Text title = new Text("Game Menu");
        title.setFill(Color.WHITE);
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");

        // Resume button
        Button resumeBtn = new Button("Resume");
        resumeBtn.setPrefWidth(200);
        resumeBtn.setPrefHeight(50);
        resumeBtn.setStyle("-fx-font-size: 18px;");
        resumeBtn.setOnAction(e -> getController().gotoPlay());

        // Exit button
        Button exitBtn = new Button("Exit");
        exitBtn.setPrefWidth(200);
        exitBtn.setPrefHeight(50);
        exitBtn.setStyle("-fx-font-size: 18px;");
        exitBtn.setOnAction(e -> getController().exit());

        root.getChildren().addAll(title, resumeBtn, exitBtn);
        getContentRoot().getChildren().add(root);
    }

    @Override
    public void onCreate() {
        // Called when menu is created
    }

    @Override
    public void onUpdate(double tpf) {
        // Called every frame
    }
}

