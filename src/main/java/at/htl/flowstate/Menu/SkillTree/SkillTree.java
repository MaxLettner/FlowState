package at.htl.flowstate.Menu.SkillTree;

import at.htl.flowstate.Menu.SkillTree.Components.SkillTreeNode;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class SkillTree extends SkillTreeParent {
    private SkillTreeNode magicNode;
    private SkillTreeNode meleeNode;
    private SkillTreeNode rangedNode;

    private final boolean[] skillUnlockStatus = new boolean[3];
    /*
     * false == first time unlocked
     * true == was already unlocked before
     * 0 == magic
     * 1 == melee
     * 2 == ranged
     * */

    private SkillTreeNode currentCategoryNode;
    private VBox contentBox;
    private VBox mainContainer;

    private Button meleeBtn;
    private Button magicBtn;
    private Button rangedBtn;

    public SkillTree() {
        super("Skill Tree");
        initializeHierarchy();
        setupUI();
    }

    private void initializeHierarchy() {
        // Magic sub-trees
        SkillTreeNode arcaneNode = new SkillTreeNode(SkillType.MAGIC_ARCANE, new SkillTreeNode[]{
                new SkillTreeNode(SkillType.ARCANE_LEVITATION, null),
                new SkillTreeNode(SkillType.ARCANE_MAGIC_MISSILE, null),
                new SkillTreeNode(SkillType.ARCANE_MANA_SHIELD, null)
        });

        SkillTreeNode elementalNode = new SkillTreeNode(SkillType.MAGIC_ELEMENTAL, new SkillTreeNode[]{
                new SkillTreeNode(SkillType.ELEMENTAL_FIRE_BALL, null),
                new SkillTreeNode(SkillType.ELEMENTAL_ICICLE, null),
                new SkillTreeNode(SkillType.ELEMENTAL_POISON_DARTS, null)
        });

        SkillTreeNode enchantingNode = new SkillTreeNode(SkillType.MAGIC_ENCHANTING, new SkillTreeNode[]{
                new SkillTreeNode(SkillType.ENCHANTING_SPEED, null),
                new SkillTreeNode(SkillType.ENCHANTING_LIFE_STEAL, null),
                new SkillTreeNode(SkillType.ENCHANTING_PIERCING, null)
        });

        magicNode = new SkillTreeNode(SkillType.MAGIC, new SkillTreeNode[]{arcaneNode, elementalNode, enchantingNode});

        // Melee sub-trees
        SkillTreeNode swordsNode = new SkillTreeNode(SkillType.MELEE_SWORDS, new SkillTreeNode[]{
                new SkillTreeNode(SkillType.SWORDS_SHORTSWORD, null),
                new SkillTreeNode(SkillType.SWORDS_DUAL_WIELDING, null),
                new SkillTreeNode(SkillType.SWORDS_ZWEIHANDER, null)
        });

        SkillTreeNode fisticuffsNode = new SkillTreeNode(SkillType.MELEE_FISTICUFFS, new SkillTreeNode[]{
                new SkillTreeNode(SkillType.FISTICUFFS_LEATHER, null),
                new SkillTreeNode(SkillType.FISTICUFFS_METAL_GLOVES, null),
                new SkillTreeNode(SkillType.FISTICUFFS_SPIKE_GLOVES, null)
        });

        SkillTreeNode bluntNode = new SkillTreeNode(SkillType.MELEE_BLUNT, new SkillTreeNode[]{
                new SkillTreeNode(SkillType.BLUNT_HAMMER, null),
                new SkillTreeNode(SkillType.BLUNT_MORNINGSTAR, null),
                new SkillTreeNode(SkillType.BLUNT_SPRING_HAMMER, null)
        });

        meleeNode = new SkillTreeNode(SkillType.MELEE, new SkillTreeNode[]{swordsNode, fisticuffsNode, bluntNode});

        // Ranged sub-trees
        SkillTreeNode bowNode = new SkillTreeNode(SkillType.RANGED_BOW, new SkillTreeNode[]{
                new SkillTreeNode(SkillType.BOW_BONE_BOW, null),
                new SkillTreeNode(SkillType.BOW_SHORTBOW, null),
                new SkillTreeNode(SkillType.BOW_WAR_BOW, null)
        });

        SkillTreeNode crossbowNode = new SkillTreeNode(SkillType.RANGED_CROSSBOW, new SkillTreeNode[]{
                new SkillTreeNode(SkillType.CROSSBOW_DUAL_CROSSBOW, null),
                new SkillTreeNode(SkillType.CROSSBOW_HEAVY_CROSSBOW, null),
                new SkillTreeNode(SkillType.CROSSBOW_POISON_CROSSBOW, null)
        });

        SkillTreeNode tridentNode = new SkillTreeNode(SkillType.RANGED_TRIDENT, new SkillTreeNode[]{
                new SkillTreeNode(SkillType.TRIDENT_HEAVY_TRIDENT, null),
                new SkillTreeNode(SkillType.TRIDENT_ICE_TRIDENT, null),
                new SkillTreeNode(SkillType.TRIDENT_RECALL_TRIDENT, null)
        });

        rangedNode = new SkillTreeNode(SkillType.RANGED, new SkillTreeNode[]{bowNode, crossbowNode, tridentNode});

        currentCategoryNode = null;
    }

    private void setupUI() {
        double windowWidth = FXGL.getAppWidth();
        root.setLayoutX(windowWidth / 2 - 350);
        root.setLayoutY(50);

        contentBox = new VBox(15);
        contentBox.setAlignment(Pos.TOP_CENTER);
        contentBox.setStyle("-fx-padding: 20;");

        displayTopLevel();

        mainContainer = new VBox(10);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.getChildren().add(contentBox);

        root.getChildren().add(mainContainer);
    }

    private void displayTopLevel() {
        contentBox.getChildren().clear();
        currentCategoryNode = null;

        meleeBtn  = new Button(SkillType.MELEE.getName());
        magicBtn  = new Button(SkillType.MAGIC.getName());
        rangedBtn = new Button(SkillType.RANGED.getName());

        for (Button btn : new Button[]{meleeBtn, magicBtn, rangedBtn}) {
            btn.setPrefWidth(nodeSize * 2);
            btn.setPrefHeight(nodeSize / 2);
        }

        magicBtn.setStyle(baseStyle(SkillType.MAGIC,  true, 18));
        meleeBtn.setStyle(baseStyle(SkillType.MELEE,  true, 18));
        rangedBtn.setStyle(baseStyle(SkillType.RANGED, true, 18));

        magicBtn.setOnMouseEntered(e ->  magicBtn.setStyle(hoverStyle(SkillType.MAGIC,  true, 18)));
        meleeBtn.setOnMouseEntered(e ->  meleeBtn.setStyle(hoverStyle(SkillType.MELEE,  true, 18)));
        rangedBtn.setOnMouseEntered(e -> rangedBtn.setStyle(hoverStyle(SkillType.RANGED, true, 18)));

        magicBtn.setOnMouseExited(e ->   magicBtn.setStyle(baseStyle(SkillType.MAGIC,  true, 18)));
        meleeBtn.setOnMouseExited(e ->   meleeBtn.setStyle(baseStyle(SkillType.MELEE,  true, 18)));
        rangedBtn.setOnMouseExited(e ->  rangedBtn.setStyle(baseStyle(SkillType.RANGED, true, 18)));

        magicBtn.setOnAction(e ->  handleMagicUnlock());
        meleeBtn.setOnAction(e ->  handleMeleeUnlock());
        rangedBtn.setOnAction(e -> handleRangedUnlock());

        HBox categoryBox = new HBox(20);
        categoryBox.setAlignment(Pos.CENTER);
        categoryBox.getChildren().addAll(meleeBtn, magicBtn, rangedBtn);
        contentBox.getChildren().add(categoryBox);
    }

    private void handleMagicUnlock() {
        magicNode.onClick();
        magicBtn.setStyle("-fx-font-size: 14px; -fx-background-color: #008000; -fx-text-fill: white;");
        magicBtn.setOnMouseExited(e -> magicBtn.setStyle("-fx-font-size: 14px; -fx-background-color: #008000; -fx-text-fill: white;"));
        if(skillUnlockStatus[0]) {
            displayFullTree(magicNode);
        } else {
            skillUnlockStatus[0] = true;
        }
    }
    private void handleMeleeUnlock() {
        meleeNode.onClick();
        meleeBtn.setStyle("-fx-font-size: 14px; -fx-background-color: #008000; -fx-text-fill: white;");
        meleeBtn.setOnMouseExited(e -> meleeBtn.setStyle("-fx-font-size: 14px; -fx-background-color: #008000; -fx-text-fill: white;"));
        if(skillUnlockStatus[1]) {
            displayFullTree(meleeNode);
        } else {
            skillUnlockStatus[1] = true;
        }
    }
    private void handleRangedUnlock() {
        rangedNode.onClick();
        rangedBtn.setStyle("-fx-font-size: 14px; -fx-background-color: #008000; -fx-text-fill: white;");
        rangedBtn.setOnMouseExited(e -> rangedBtn.setStyle("-fx-font-size: 14px; -fx-background-color: #008000; -fx-text-fill: white;"));
        if(skillUnlockStatus[2]) {
            displayFullTree(rangedNode);
        } else {
            skillUnlockStatus[2] = true;
        }
    }


    private void displayFullTree(SkillTreeNode categoryNode) {
        contentBox.getChildren().clear();
        currentCategoryNode = categoryNode;

        Button backBtn = new Button("Go Back");
        backBtn.setPrefWidth((int)(nodeSize * 1.5));
        backBtn.setPrefHeight((int)(nodeSize / 2.5));
        backBtn.setStyle("-fx-font-size: 12px; -fx-background-color: #555; -fx-text-fill: white;");
        backBtn.setOnAction(e -> displayTopLevel());

        Text title = new Text(categoryNode.getSkillType().getName() + " Tree");
        title.setFill(Color.WHITE);
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        VBox titleBox = new VBox();
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(backBtn, title);

        VBox treeBox = new VBox(25);
        treeBox.setAlignment(Pos.TOP_CENTER);

        if (categoryNode.getChildren() != null) {
            for (SkillTreeNode subCategoryNode : categoryNode.getChildren()) {
                treeBox.getChildren().add(displaySubCategory(categoryNode, subCategoryNode));
            }
        }

        contentBox.getChildren().addAll(titleBox, treeBox);
    }

    private VBox displaySubCategory(SkillTreeNode parentNode, SkillTreeNode subCategoryNode) {
        VBox subBox = new VBox(10);
        subBox.setAlignment(Pos.CENTER);
        subBox.setStyle("-fx-border-color: #666; -fx-border-width: 1; -fx-padding: 15;");

        Text subTitle = new Text(subCategoryNode.getSkillType().getName());
        subTitle.setFill(Color.LIGHTYELLOW);
        subTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button subCategoryBtn = createCategoryButtonWithState(subCategoryNode, parentNode);

        HBox skillsBox = new HBox(15);
        skillsBox.setAlignment(Pos.CENTER);

        if (subCategoryNode.getChildren() != null) {
            for (SkillTreeNode skillNode : subCategoryNode.getChildren()) {
                skillsBox.getChildren().add(createSkillButtonWithState(skillNode, subCategoryNode));
            }
        }

        subBox.getChildren().addAll(subTitle, subCategoryBtn, skillsBox);
        return subBox;
    }

    private Button createCategoryButtonWithState(SkillTreeNode node, SkillTreeNode parentNode) {
        Button btn = new Button(node.getSkillType().getName());
        btn.setPrefWidth(nodeSize * 2);
        btn.setPrefHeight((int)(nodeSize / 2.2));

        boolean parentUnlocked = parentNode.isUnlocked();
        btn.setDisable(!parentUnlocked);
        btn.setStyle(baseStyle(node.getSkillType(), parentUnlocked, 14));
        btn.setOnMouseEntered(e -> { if (!btn.isDisabled()) btn.setStyle(hoverStyle(node.getSkillType(), parentUnlocked, 14)); });
        btn.setOnMouseExited(e ->  { if (!btn.isDisabled()) btn.setStyle(baseStyle(node.getSkillType(), parentUnlocked, 14)); });
        btn.setOnAction(e -> { node.onClick(); updateFullTree(); });
        return btn;
    }

    private Button createSkillButtonWithState(SkillTreeNode skillNode, SkillTreeNode parentSubCategoryNode) {
        Button btn = new Button(skillNode.getSkillType().getName());
        btn.setPrefWidth(nodeSize * 1.8);
        btn.setPrefHeight((int)(nodeSize / 2.5));
        btn.setWrapText(true);

        boolean parentUnlocked = parentSubCategoryNode.isUnlocked();
        btn.setDisable(!parentUnlocked);
        btn.setStyle(baseStyle(skillNode.getSkillType(), parentUnlocked, 12));
        btn.setOnMouseEntered(e -> { if (!btn.isDisabled()) btn.setStyle(hoverStyle(skillNode.getSkillType(), parentUnlocked, 12)); });
        btn.setOnMouseExited(e ->  { if (!btn.isDisabled()) btn.setStyle(baseStyle(skillNode.getSkillType(), parentUnlocked, 12)); });
        btn.setOnAction(e -> { skillNode.onClick(); updateFullTree(); });
        return btn;
    }

    private String baseStyle(SkillType skillType, boolean parentUnlocked, int fontSize) {
        String fs = "-fx-font-size: " + fontSize + "px; ";
        if (!parentUnlocked)                                       return fs + "-fx-background-color: #444; -fx-text-fill: #888;";
        boolean unlocked  = skillList.isSkillUnlocked(skillType);
        boolean selected  = skillList.isSelected(skillType);
        boolean canAfford = skillList.getSkill(skillType).canAfford();
        if (unlocked && selected) return fs + "-fx-background-color: #1565C0; -fx-text-fill: white;";
        if (unlocked)             return fs + "-fx-background-color: #008000; -fx-text-fill: white;";
        if (canAfford)            return fs + "-fx-background-color: #FF8C00; -fx-text-fill: white;";
        return fs + "-fx-background-color: #444;    -fx-text-fill: #888;";
    }

    private String hoverStyle(SkillType skillType, boolean parentUnlocked, int fontSize) {
        String fs = "-fx-font-size: " + fontSize + "px; ";
        if (!parentUnlocked)                                       return fs + "-fx-background-color: #444; -fx-text-fill: #888;";
        boolean unlocked  = skillList.isSkillUnlocked(skillType);
        boolean selected  = skillList.isSelected(skillType);
        boolean canAfford = skillList.getSkill(skillType).canAfford();
        if (unlocked && selected) return fs + "-fx-background-color: #1976D2; -fx-text-fill: white;";
        if (unlocked)             return fs + "-fx-background-color: #00a000; -fx-text-fill: white;";
        if (canAfford)            return fs + "-fx-background-color: #FFA333; -fx-text-fill: white;";
        return fs + "-fx-background-color: #444;    -fx-text-fill: #888;";
    }

    private void updateFullTree() {
        if (currentCategoryNode != null) {
            displayFullTree(currentCategoryNode);
        }
    }
}