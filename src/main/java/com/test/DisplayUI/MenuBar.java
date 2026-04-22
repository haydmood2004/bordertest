package com.test.DisplayUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Objects;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.*;
import java.awt.*;

public class MenuBar { 
    private JToolBar menuBar = new JToolBar();
    private Component rightSideGlue;
    private ButtonControl b = new ButtonControl();


    public MenuBar(JToolBar menuBar) {
        this.menuBar = menuBar;
    }

    public MenuBar(JButton... instanceButtons) {
        menuBar = createToolBarBase();
        rightSideGlue = Box.createHorizontalGlue();
        menuBar.add(rightSideGlue);
        menuBar.setOrientation(JToolBar.VERTICAL);
        menuBar.setAlignmentY(Component.CENTER_ALIGNMENT);
        menuBar.setMargin(new Insets(0, 0, 0, 0));
        menuBar.setAlignmentX(Component.RIGHT_ALIGNMENT);

        addInstanceButtons(instanceButtons);

        menuBar.add(bodyButton());
        menuBar.addSeparator(new Dimension(5,0));
        menuBar.add(faceButton());
        menuBar.addSeparator(new Dimension(5,0));
        menuBar.add(hairButton());
        menuBar.addSeparator(new Dimension(5,0));
        menuBar.add(topButton());
        menuBar.addSeparator(new Dimension(5,0));
        menuBar.add(bottomButton());
        menuBar.addSeparator(new Dimension(5,0));
        menuBar.add(fullDressButton());
        menuBar.addSeparator(new Dimension(5,0));
        menuBar.add(shoeButton());
        menuBar.addSeparator(new Dimension(5,0));
        menuBar.add(accessoryButton());
    }

    public void addInstanceButton(JButton button) {
        if (button == null) {
            return;
        }

        button.setAlignmentY(Component.CENTER_ALIGNMENT);
        int glueIndex = menuBar.getComponentZOrder(rightSideGlue);
        int insertIndex = (glueIndex >= 0) ? glueIndex : menuBar.getComponentCount();
        menuBar.add(button, insertIndex);
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

    public JToolBar createToolBarBase() {
        JToolBar menu = new JToolBar();
        menu.setOrientation(JToolBar.HORIZONTAL);
        menu.setFloatable(false);
        menu.setRollover(true);
        menu.setBorderPainted(false);
        menu.setOpaque(true);
        menu.setBackground(new Color(98, 3, 49));
        menu.setAlignmentY(Component.CENTER_ALIGNMENT);
        menu.setMargin(new Insets(0, 0, 0, 0));
        menu.setAlignmentX(JToolBar.LEFT);
        menu.setVisible(true);
        return menu;
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

    public JToolBar getMenuBar() {
        return menuBar;
    }

    
}
