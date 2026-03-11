package at.htl.flowstate.Menu;

import at.htl.flowstate.Skills.Skill;
import at.htl.flowstate.Skills.SkillList;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;

import com.almasb.fxgl.input.UserAction;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public abstract class SkillTreeParent extends FXGLMenu {

    protected final int nodeSize = 100;
    protected final VBox root;

    protected SkillList skillList;

    public SkillTreeParent(MenuType menuType, String titleText) {
        super(menuType);

        skillList = SkillList.getInstance();

        // Root container
        root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        // Title
        Text title = new Text(titleText);
        title.setFill(Color.WHITE);
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
        
        root.getChildren().addAll(title);

        // Add to FXGL menu
        getContentRoot().getChildren().add(root);

        // Optional: simple animation example
        FXGL.animationBuilder()
                .duration(Duration.seconds(1))
                .autoReverse(true)
                .repeatInfinitely()
                .scale(root)
                .buildAndPlay();

        getInput().addAction(new UserAction("Close Skill Tree") {
            @Override
            protected void onActionBegin(){
                getController().gotoPlay();
            }
        }, KeyCode.I);
    }




    protected Button createButton(String name) {
        Button button = new Button(name);
        button.setPrefWidth(nodeSize * 2);
        button.setPrefHeight(nodeSize / 2);
        button.setStyle("-fx-font-size: 18px; -fx-background-color: #444; -fx-text-fill: white;");

        // Simple hover effect
        button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 18px; -fx-background-color: #666; -fx-text-fill: white;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 18px; -fx-background-color: #444; -fx-text-fill: white;"));

        // Click action

        return button;
    }

    protected Button createSkillButton(SkillType skillType) {
        Skill skill = skillList.getSkill(skillType);
        Button button = new Button(skillType.getName());
        button.setPrefWidth(nodeSize * 2);
        button.setPrefHeight(nodeSize / 2);

        updateSkillButtonStyle(button, skill);

        button.setOnMouseEntered(e -> button.setStyle(button.getStyle() + "-fx-opacity: 0.8;"));
        button.setOnMouseExited(e -> button.setStyle(button.getStyle().replace("-fx-opacity: 0.8;", "")));

        button.setOnAction(e -> {

            skillList.unlockSkill(skillType);

            updateSkillButtonStyle(button, skill);

        });

        return button;
    }

    private void updateSkillButtonStyle(Button button, Skill skill) {
        if (skill.isUnlocked()) {
            button.setStyle("-fx-font-size: 18px; -fx-background-color: #2a7a2a; -fx-text-fill: white;");
        } else {
            button.setStyle("-fx-font-size: 18px; -fx-background-color: #444; -fx-text-fill: white;");
        }
    }

    private void debugOutputSkillList(){
        System.out.println("Current Skill List:");
        for (Skill skill : skillList.getSkills()) {
            System.out.println("- " + skill.getName() + ": " + (skill.isUnlocked() ? "Unlocked" : "Locked"));
        }
    }

    @Override
    public void onCreate() {
        // This is called when the menu is created
    }

    @Override
    public void onUpdate(double tpf) {
        // Called every frame if you want animations or dynamic updates
    }

}