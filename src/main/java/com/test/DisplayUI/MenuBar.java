package com.test.DisplayUI;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.test.DisplayUI.Panels.CustomPanel;

public class MenuBar {
    private final JPanel menuBar = new JPanel();
    private final ButtonControl b = new ButtonControl();
    private final CustomPanel customPanel;

    private final String REG = "/buttons/default button reg.png";
    private final String HOVER = "/buttons/default button hover.png";
    private final String PRESS = "/buttons/default button click.png";

    private int lastIconSize = -1;

    private JButton bodyButton;
    private JButton faceButton;
    private JButton hairButton;
    private JButton topButton;
    private JButton bottomButton;
    private JButton fullDressButton;
    private JButton shoeButton;
    private JButton accessoryButton;

    public MenuBar(CustomPanel customPanel) {
        this.customPanel = customPanel;

        setupMenuBar();
        createButtons();
        addButtons();
    }

    private void setupMenuBar() {
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));
        menuBar.setBackground(new java.awt.Color(245, 175, 201));
        menuBar.setOpaque(true);
        menuBar.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 7));
    }

    private void createButtons() {
        bodyButton = createButton("Customize Body");
        faceButton = createButton("Customize Face");
        hairButton = createButton("Customize Hair");
        topButton = createButton("Customize Top Clothing");
        bottomButton = createButton("Customize Bottom Clothing");
        fullDressButton = createButton("Customize Outfit");
        shoeButton = createButton("Customize Shoes");
        accessoryButton = createButton("Customize Accessories");

        bodyButton.addActionListener(e ->
                customPanel.showCategory("Body", "Body Type", 4, color -> {}, index -> {})
        );

        faceButton.addActionListener(e ->
                customPanel.showCategory("Face", "Face Type", 6, color -> {}, index -> {})
        );

        hairButton.addActionListener(e ->
                customPanel.showCategory("Hair", "Hair Style", 8, color -> {}, index -> {})
        );

        topButton.addActionListener(e ->
                customPanel.showCategory("Top", "Top Type", 8, color -> {}, index -> {})
        );

        bottomButton.addActionListener(e ->
                customPanel.showCategory("Bottom", "Bottom Type", 8, color -> {}, index -> {})
        );

        fullDressButton.addActionListener(e ->
                customPanel.showCategory("Outfit", "Outfit Type", 8, color -> {}, index -> {})
        );

        shoeButton.addActionListener(e ->
                customPanel.showCategory("Shoes", "Shoe Type", 6, color -> {}, index -> {})
        );

        accessoryButton.addActionListener(e ->
                customPanel.showCategory("Accessories", "Accessory Type", 10, color -> {}, index -> {})
        );
    }

    private JButton createButton(String tooltip) {
        JButton button = b.createControlButton(
                "",
                tooltip,
                REG,
                HOVER,
                PRESS,
                40
        );

        button.setAlignmentX(Component.RIGHT_ALIGNMENT);
        return button;
    }

    private void addButtons() {
        menuBar.add(Box.createVerticalGlue());

        addRightAligned(bodyButton);
        menuBar.add(Box.createVerticalGlue());

        addRightAligned(faceButton);
        menuBar.add(Box.createVerticalGlue());

        addRightAligned(hairButton);
        menuBar.add(Box.createVerticalGlue());

        addRightAligned(topButton);
        menuBar.add(Box.createVerticalGlue());

        addRightAligned(bottomButton);
        menuBar.add(Box.createVerticalGlue());

        addRightAligned(fullDressButton);
        menuBar.add(Box.createVerticalGlue());

        addRightAligned(shoeButton);
        menuBar.add(Box.createVerticalGlue());

        addRightAligned(accessoryButton);
        menuBar.add(Box.createVerticalGlue());
    }

    private void addRightAligned(JButton button) {
        button.setAlignmentX(Component.RIGHT_ALIGNMENT);
        menuBar.add(button);
    }

    public void attachWindowMouseController(WindowMouseController mouseController) {
        menuBar.addMouseListener(mouseController);
        menuBar.addMouseMotionListener(mouseController);

        for (Component component : menuBar.getComponents()) {
            if (component instanceof JButton) {
                component.addMouseListener(mouseController);
                component.addMouseMotionListener(mouseController);
            }
        }
    }

    public void setCursorForAllComponents(Cursor cursor) {
        menuBar.setCursor(cursor);

        for (Component component : menuBar.getComponents()) {
            component.setCursor(cursor);
        }
    }

    public void resizeButtons(int size) {
        if (size == lastIconSize) {
            return;
        }

        lastIconSize = size;

        Dimension dim = new Dimension(size, size);

        for (JButton button : getButtons()) {
            button.setPreferredSize(dim);
            button.setMinimumSize(dim);
            button.setMaximumSize(dim);

            b.rescaleButtonIcons(button, REG, HOVER, PRESS, size);
        }

        menuBar.revalidate();
        menuBar.repaint();
    }

    private JButton[] getButtons() {
        return new JButton[] {
                bodyButton,
                faceButton,
                hairButton,
                topButton,
                bottomButton,
                fullDressButton,
                shoeButton,
                accessoryButton
        };
    }

    public JPanel getMenuBar() {
        return menuBar;
    }
}