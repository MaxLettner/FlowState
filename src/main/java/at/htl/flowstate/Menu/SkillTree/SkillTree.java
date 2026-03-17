package at.htl.flowstate.Menu.SkillTree;

import at.htl.flowstate.Menu.SkillTree.Components.SkillTreeNode;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class SkillTree extends SkillTreeParent {

    // Categories of the Skill-Tree
    private SkillTreeNode magicNode;
    private SkillTreeNode meeleNode;
    private SkillTreeNode rangedNode;

    // Current navigation state, (null = top level, otherwise shows the full tree of the selected category e.g.: magic)
    private SkillTreeNode currentCategoryNode;
    private VBox contentBox; // Container for dynamic content (categories, sub-categories, skills)
    private VBox mainContainer; // Main container to hold title and contentBox
    public SkillTree() {
        super(MenuType.GAME_MENU, "Skill Tree");
        initializeHierarchy();
        setupUI();
    }


    private void initializeHierarchy() {
        // Magic sub-trees
        SkillTreeNode arcaneNode = new SkillTreeNode(SkillType.MAGIC_ARCANE, SkillType.MAGIC, new SkillTreeNode[]{
                new SkillTreeNode(SkillType.ARCANE_LEVITATION, SkillType.MAGIC_ARCANE, null),
                new SkillTreeNode(SkillType.ARCANE_MAGIC_MISSILE, SkillType.MAGIC_ARCANE, null),
                new SkillTreeNode(SkillType.ARCANE_MANA_SHIELD, SkillType.MAGIC_ARCANE, null)
        });

        SkillTreeNode elementalNode = new SkillTreeNode(SkillType.MAGIC_ELEMENTAL, SkillType.MAGIC, new SkillTreeNode[]{
                new SkillTreeNode(SkillType.ELEMENTAL_FIRE_BALL, SkillType.MAGIC_ELEMENTAL, null),
                new SkillTreeNode(SkillType.ELEMENTAL_ICECICLE, SkillType.MAGIC_ELEMENTAL, null),
                new SkillTreeNode(SkillType.ELEMENTAL_POISON_DARTS, SkillType.MAGIC_ELEMENTAL, null)
        });

        SkillTreeNode enchantingNode = new SkillTreeNode(SkillType.MAGIC_ENCHANTING, SkillType.MAGIC, new SkillTreeNode[]{
                new SkillTreeNode(SkillType.ENCHANTING_FIRE_ASPECT, SkillType.MAGIC_ENCHANTING, null),
                new SkillTreeNode(SkillType.ENCHANTING_LIFE_STEAL, SkillType.MAGIC_ENCHANTING, null),
                new SkillTreeNode(SkillType.ENCHANTING_PIERCING, SkillType.MAGIC_ENCHANTING, null)
        });

        magicNode = new SkillTreeNode(SkillType.MAGIC, null, new SkillTreeNode[]{arcaneNode, elementalNode, enchantingNode});

        // Meele sub-trees
        SkillTreeNode swordsNode = new SkillTreeNode(SkillType.MEELE_SWORDS, SkillType.MEELE, new SkillTreeNode[]{
                new SkillTreeNode(SkillType.SWORDS_SHORTSWORD, SkillType.MEELE_SWORDS, null),
                new SkillTreeNode(SkillType.SWORDS_DUAL_WIELDING, SkillType.MEELE_SWORDS, null),
                new SkillTreeNode(SkillType.SWORDS_ZWEIHAENDER, SkillType.MEELE_SWORDS, null)
        });

        SkillTreeNode fisticuffsNode = new SkillTreeNode(SkillType.MEELE_FISTICUFFS, SkillType.MEELE, new SkillTreeNode[]{
                new SkillTreeNode(SkillType.FISTICUFFS_LEATHER, SkillType.MEELE_FISTICUFFS, null),
                new SkillTreeNode(SkillType.FISTICUFFS_METAL_GLOVES, SkillType.MEELE_FISTICUFFS, null),
                new SkillTreeNode(SkillType.FISTICUFFS_SPIKE_GLOVES, SkillType.MEELE_FISTICUFFS, null)
        });

        SkillTreeNode bluntNode = new SkillTreeNode(SkillType.MEELE_BLUNT, SkillType.MEELE, new SkillTreeNode[]{
                new SkillTreeNode(SkillType.BLUNT_HAMMER, SkillType.MEELE_BLUNT, null),
                new SkillTreeNode(SkillType.BLUNT_MORNINGSTAR, SkillType.MEELE_BLUNT, null),
                new SkillTreeNode(SkillType.BLUNT_SPRING_HAMMER, SkillType.MEELE_BLUNT, null)
        });

        meeleNode = new SkillTreeNode(SkillType.MEELE, null, new SkillTreeNode[]{swordsNode, fisticuffsNode, bluntNode});

        // Ranged sub-trees
        SkillTreeNode bowNode = new SkillTreeNode(SkillType.RANGED_BOW, SkillType.RANGED, new SkillTreeNode[]{
                new SkillTreeNode(SkillType.BOW_BONE_BOW, SkillType.RANGED_BOW, null),
                new SkillTreeNode(SkillType.BOW_SHORTBOW, SkillType.RANGED_BOW, null),
                new SkillTreeNode(SkillType.BOW_WAR_BOW, SkillType.RANGED_BOW, null)
        });

        SkillTreeNode crossbowNode = new SkillTreeNode(SkillType.RANGED_CROSSBOW, SkillType.RANGED, new SkillTreeNode[]{
                new SkillTreeNode(SkillType.CROSSBOW_DUAL_CROSSBOW, SkillType.RANGED_CROSSBOW, null),
                new SkillTreeNode(SkillType.CROSSBOW_HEAVY_CROSSBOW, SkillType.RANGED_CROSSBOW, null),
                new SkillTreeNode(SkillType.CROSSBOW_POISON_CROSSBOW, SkillType.RANGED_CROSSBOW, null)
        });

        SkillTreeNode tridentNode = new SkillTreeNode(SkillType.RANGED_TRIDENT, SkillType.RANGED, new SkillTreeNode[]{
                new SkillTreeNode(SkillType.TRIDENT_HEAVY_TRIDENT, SkillType.RANGED_TRIDENT, null),
                new SkillTreeNode(SkillType.TRIDENT_ICE_TRIDENT, SkillType.RANGED_TRIDENT, null),
                new SkillTreeNode(SkillType.TRIDENT_RECALL_TRIDENT, SkillType.RANGED_TRIDENT, null)
        });

        rangedNode = new SkillTreeNode(SkillType.RANGED, null, new SkillTreeNode[]{bowNode, crossbowNode, tridentNode});

        currentCategoryNode = null;
    }


    private void setupUI() {
        double windowWidth = FXGL.getAppWidth();
        root.setLayoutX(windowWidth / 2 - 325);
        root.setLayoutY(50);

        contentBox = new VBox(15);
        contentBox.setAlignment(Pos.TOP_CENTER);
        contentBox.setStyle("-fx-padding: 20;");

        // Display top-level categories
        displayTopLevel();

        mainContainer = new VBox(10);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.getChildren().add(contentBox);

        root.getChildren().clear();
        root.getChildren().add(mainContainer);
    }

    //display 3 main categories of skill tree
    private void displayTopLevel() {
        contentBox.getChildren().clear();
        currentCategoryNode = null;


        Button meeleBtn = createButton(SkillType.MEELE.getName(), SkillType.MEELE);
        Button magicBtn = createButton(SkillType.MAGIC.getName(), SkillType.MAGIC);
        Button rangedBtn = createButton(SkillType.RANGED.getName(), SkillType.RANGED);

        magicBtn.setOnAction(e -> {
            magicNode.unlock();
            displayFullTree(magicNode);
        });
        meeleBtn.setOnAction(e -> {
            meeleNode.unlock();
            displayFullTree(meeleNode);
        });
        rangedBtn.setOnAction(e -> {
            rangedNode.unlock();
            displayFullTree(rangedNode);
        });

        HBox categoryBox = new HBox(20); // Spacing between category buttons
        categoryBox.setAlignment(Pos.CENTER);
        categoryBox.getChildren().addAll(meeleBtn, magicBtn, rangedBtn);

        contentBox.getChildren().add(categoryBox);
    }


    private void displayFullTree(SkillTreeNode categoryNode) {
        contentBox.getChildren().clear();
        currentCategoryNode = categoryNode;

        // Add back button
        Button backBtn = new Button("Go Back");
        backBtn.setPrefWidth((int)(nodeSize * 1.5));
        backBtn.setPrefHeight((int)(nodeSize / 2.5));
        backBtn.setStyle("-fx-font-size: 12px; -fx-background-color: #555; -fx-text-fill: white;");
        backBtn.setOnAction(e -> displayTopLevel());

        // Add title
        Text title = new Text(categoryNode.getSkillType().getName() + " Tree");
        title.setFill(Color.WHITE);
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        VBox titleBox = new VBox(); // Container for back button and title
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(backBtn, title);

        // Display all sub-categories and their skills
        VBox treeBox = new VBox(25);
        treeBox.setAlignment(Pos.TOP_CENTER);

        if (categoryNode.getChildren() != null) {
            for (SkillTreeNode subCategoryNode : categoryNode.getChildren()) {
                VBox subCategoryBox = displaySubCategory(categoryNode, subCategoryNode);
                treeBox.getChildren().add(subCategoryBox);
            }
        }

        contentBox.getChildren().addAll(titleBox, treeBox);
    }


    private VBox displaySubCategory(SkillTreeNode parentNode, SkillTreeNode subCategoryNode) {
        VBox subBox = new VBox(10); // Spacing between sub-category title/button and skills
        subBox.setAlignment(Pos.CENTER);
        subBox.setStyle("-fx-border-color: #666; -fx-border-width: 1; -fx-padding: 15;");

        // Sub-category title
        Text subTitle = new Text(subCategoryNode.getSkillType().getName());
        subTitle.setFill(Color.LIGHTYELLOW);
        subTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Sub-category button
        Button subCategoryBtn = createCategoryButtonWithState(subCategoryNode, parentNode);

        // Skills row
        HBox skillsBox = new HBox(15);
        skillsBox.setAlignment(Pos.CENTER);

        if (subCategoryNode.getChildren() != null) {
            for (SkillTreeNode skillNode : subCategoryNode.getChildren()) {
                Button skillBtn = createSkillButtonWithState(skillNode, subCategoryNode);
                skillsBox.getChildren().add(skillBtn);
            }
        }

        subBox.getChildren().addAll(subTitle, subCategoryBtn, skillsBox);
        return subBox;
    }

    //Button for categories and sub-categories
    private Button createCategoryButtonWithState(SkillTreeNode node, SkillTreeNode parentNode) {
        Button btn = new Button(node.getSkillType().getName());
        btn.setPrefWidth(nodeSize * 2);
        btn.setPrefHeight((int)(nodeSize / 2.2));
        btn.setStyle("-fx-font-size: 14px;");

        boolean parentUnlocked = parentNode.isUnlocked();
        boolean isUnlocked = node.isUnlocked();

        // Determine styling based on unlock state
        String style;
        if (!parentUnlocked) {
            // Parent not unlocked -> can't unlock this
            style = "-fx-font-size: 14px; -fx-background-color: #333; -fx-text-fill: #888; -fx-cursor: not-allowed;";
            btn.setDisable(true);
        } else if (isUnlocked) {
            // Already unlocked -> green
            style = "-fx-font-size: 14px; -fx-background-color: #008000; -fx-text-fill: white;";
        } else {
            // Can be unlocked -> yellow/orange
            style = "-fx-font-size: 14px; -fx-background-color: #FF8C00; -fx-text-fill: white;";
        }

        btn.setStyle(style);

        // Hover effect
        btn.setOnMouseEntered(e -> {
            if (!btn.isDisable()) {
                if (isUnlocked) {
                    btn.setStyle("-fx-font-size: 14px; -fx-background-color: #00a000; -fx-text-fill: white;");
                } else {
                    btn.setStyle("-fx-font-size: 14px; -fx-background-color: #FFB033; -fx-text-fill: white;");
                }
            }
        });

        btn.setOnMouseExited(e -> {
            if (!btn.isDisable()) {
                btn.setStyle(style);
            }
        });

        btn.setOnAction(e -> {
            node.unlock();
            updateFullTree();
        });

        return btn;
    }

    //button for skills
    private Button createSkillButtonWithState(SkillTreeNode skillNode, SkillTreeNode parentSubCategoryNode) {
        Button btn = new Button(skillNode.getSkillType().getName());
        btn.setPrefWidth(nodeSize * 1.8);
        btn.setPrefHeight((int)(nodeSize / 2.5));
        btn.setStyle("-fx-font-size: 12px; -fx-padding: 5;");
        btn.setWrapText(true);

        boolean parentUnlocked = parentSubCategoryNode.isUnlocked();
        boolean isUnlocked = skillNode.isUnlocked();

        // Determine styling based on unlock state
        String style;
        if (!parentUnlocked) {
            // Parent sub-category not unlocked - can't unlock this skill
            style = "-fx-font-size: 12px; -fx-background-color: #333; -fx-text-fill: #888; -fx-cursor: not-allowed;";
            btn.setDisable(true);
        } else if (isUnlocked) {
            // Already unlocked -> green
            style = "-fx-font-size: 12px; -fx-background-color: #008000; -fx-text-fill: white;";
        } else {
            // Can be unlocked -> blue
            style = "-fx-font-size: 12px; -fx-background-color: #0066CC; -fx-text-fill: white;";
        }

        btn.setStyle(style);

        // Hover effect
        btn.setOnMouseEntered(e -> {
            if (!btn.isDisable()) {
                if (isUnlocked) {
                    btn.setStyle("-fx-font-size: 12px; -fx-background-color: #00a000; -fx-text-fill: white;");
                } else {
                    btn.setStyle("-fx-font-size: 12px; -fx-background-color: #0080FF; -fx-text-fill: white;");
                }
            }
        });

        btn.setOnMouseExited(e -> {
            if (!btn.isDisable()) {
                btn.setStyle(style);
            }
        });

        btn.setOnAction(e -> {
            skillList.unlockSkill(skillNode.getSkillType());
            updateFullTree();
        });

        return btn;
    }


    private void updateFullTree() {
        if (currentCategoryNode != null) {
            displayFullTree(currentCategoryNode);
        }
    }

    @Override
    protected void closeMenu() {
        super.closeMenu();
    }
}


