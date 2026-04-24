package com.test.DisplayUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Objects;

import javax.swing.*;
import java.awt.*;

public class MenuBar {
    private JPanel menuBar = new JPanel();
    private ButtonControl b = new ButtonControl();
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

    public MenuBar() {
        setupMenuBar();
        createButtons();
        addButtons();
    }

    private void setupMenuBar() {
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));
        menuBar.setBackground(new Color(245, 175, 201));
        menuBar.setOpaque(true);
        menuBar.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 7));
    }

    public void addInstanceButton(JButton button) {
        if (button == null) {
            return;
        }

        button.setAlignmentX(Component.RIGHT_ALIGNMENT);
        menuBar.add(button);

        menuBar.revalidate();
        menuBar.repaint();
    }


    public void addInstanceButtons(JButton... buttons) {
        if (buttons == null) {
            return;
        }
        for (JButton button : buttons) {
            addInstanceButton(button);
        }
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
    }

    private JButton createButton(String tooltip) {
        return b.createControlButton(
            "",
            tooltip,
            "/buttons/default button reg.png",
            "/buttons/default button hover.png",
            "/buttons/default button click.png",
            40
        );
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
        JButton[] buttons = {
            bodyButton,
            faceButton,
            hairButton,
            topButton,
            bottomButton,
            fullDressButton,
            shoeButton,
            accessoryButton
        };

        if (size == lastIconSize) {
            return;
        }

        lastIconSize = size;

        Dimension dim = new Dimension(size, size);

        for (JButton button : buttons) {
            if (button != null) {
                button.setPreferredSize(dim);
                button.setMinimumSize(dim);
                button.setMaximumSize(dim);

                b.rescaleButtonIcons(button, REG, HOVER, PRESS, size);
            }
        }

        menuBar.revalidate();
        menuBar.repaint();
    }

    public JPanel getMenuBar() {
        return menuBar;
    }

    
}
