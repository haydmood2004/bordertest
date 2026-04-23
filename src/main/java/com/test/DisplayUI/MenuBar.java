package com.test.DisplayUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Objects;

import javax.swing.*;
import java.awt.*;

public class MenuBar { 
    private JPanel menuBar = new JPanel();
    private Component rightSideGlue;
    private ButtonControl b = new ButtonControl();

    public MenuBar(JPanel menuBar) {
        this.menuBar = menuBar;
    }

public MenuBar() {
    menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));
    menuBar.setBackground(new Color(98, 3, 49));
    menuBar.setOpaque(true);
    menuBar.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 7));

    menuBar.add(Box.createVerticalGlue());

    addRightAligned(menuBar, bodyButton());
    menuBar.add(Box.createVerticalGlue());

    addRightAligned(menuBar, faceButton());
    menuBar.add(Box.createVerticalGlue());

    addRightAligned(menuBar, hairButton());
    menuBar.add(Box.createVerticalGlue());

    addRightAligned(menuBar, topButton());
    menuBar.add(Box.createVerticalGlue());

    addRightAligned(menuBar, bottomButton());
    menuBar.add(Box.createVerticalGlue());

    addRightAligned(menuBar, fullDressButton());
    menuBar.add(Box.createVerticalGlue());

    addRightAligned(menuBar, shoeButton());
    menuBar.add(Box.createVerticalGlue());

    addRightAligned(menuBar, accessoryButton());
    menuBar.add(Box.createVerticalGlue());
}

private void addRightAligned(JPanel panel, JButton button) {
    button.setAlignmentX(Component.RIGHT_ALIGNMENT);
    panel.add(button);
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

        public void setCursorForAllComponents(java.awt.Cursor cursor) {
        menuBar.setCursor(cursor);
        for (Component component : menuBar.getComponents()) {
            component.setCursor(cursor);
        }
    }

    public JButton bodyButton() {
        JButton bodyButton = b.createControlButton(
            "Body",
            "Body",
            "/buttons/default button reg.png",
            "/buttons/default button hover.png",
            "/buttons/default button click.png"
        );
        return bodyButton;
    }

    public JButton faceButton() {
        JButton faceButton = b.createControlButton(
            "Body",
            "Body",
            "/buttons/default button reg.png",
            "/buttons/default button hover.png",
            "/buttons/default button click.png"
        );
        return faceButton;
    }

    public JButton hairButton() {
        JButton hairButton = b.createControlButton(
            "Body",
            "Body",
            "/buttons/default button reg.png",
            "/buttons/default button hover.png",
            "/buttons/default button click.png"
        );
        return hairButton;
    }

    public JButton topButton() {
        JButton topButton = b.createControlButton(
            "Body",
            "Body",
            "/buttons/default button reg.png",
            "/buttons/default button hover.png",
            "/buttons/default button click.png"
        );
        return topButton;
    }

    public JButton bottomButton() {
        JButton bottomButton = b.createControlButton(
            "Body",
            "Body",
            "/buttons/default button reg.png",
            "/buttons/default button hover.png",
            "/buttons/default button click.png"
        );
        return bottomButton;
    }

    public JButton fullDressButton() {
        JButton fullDressButton = b.createControlButton(
            "Body",
            "Body",
            "/buttons/default button reg.png",
            "/buttons/default button hover.png",
            "/buttons/default button click.png"
        );
        return fullDressButton;
    }

    public JButton shoeButton() {
        JButton shoeButton = b.createControlButton(
            "Body",
            "Body",
            "/buttons/default button reg.png",
            "/buttons/default button hover.png",
            "/buttons/default button click.png"
        );
        return shoeButton;
    }

        public JButton accessoryButton() {
        JButton accessoryButton = b.createControlButton(
            "Body",
            "Body",
            "/buttons/default button reg.png",
            "/buttons/default button hover.png",
            "/buttons/default button click.png"
        );
        return accessoryButton;
    }

    public JPanel getMenuBar() {
        return menuBar;
    }

    
}
