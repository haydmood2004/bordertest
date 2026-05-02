package com.test.DisplayUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

public class ToolBar {
    private static final String default_title = "Character Generator";
    private static final int window_button_size = 25;

    private final ButtonControl buttonControl = new ButtonControl();
    private final JToolBar toolBar;
    private final Component rightSideGlue;
    private final JLabel titleLabel;

    public ToolBar() {
        this(default_title);
    }

    public ToolBar(String title, JButton... instanceButtons) {
        toolBar = createToolBarBase();
        titleLabel = createTitleLabel(title);

        toolBar.add(Box.createHorizontalStrut(UITheme.space_lg));
        toolBar.add(titleLabel);

        rightSideGlue = Box.createHorizontalGlue();
        toolBar.add(rightSideGlue);

        addInstanceButtons(instanceButtons);
        addWindowControls();
    }

    private JToolBar createToolBarBase() {
        JToolBar bar = new JToolBar();
        bar.setOrientation(JToolBar.HORIZONTAL);
        bar.setFloatable(false);
        bar.setRollover(true);
        bar.setBorderPainted(false);
        bar.setOpaque(false);
        bar.setMargin(new Insets(0, 0, 0, 0));
        bar.setAlignmentY(Component.CENTER_ALIGNMENT);
        return bar;
    }

    private JLabel createTitleLabel(String title) {
        JLabel label = new JLabel(title == null ? default_title : title);
        label.setForeground(UITheme.text_light);
        label.setFont(UITheme.display_font.deriveFont(Font.BOLD, 12f));
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        return label;
    }

    private void addWindowControls() {
        toolBar.add(createMinimizeButton());
        addButtonGap(5);
        toolBar.add(createMaximizeButton());
        addButtonGap(5);
        toolBar.add(createCloseButton());
        addButtonGap(10);
    }

    private void addButtonGap(int width) {
        toolBar.add(Box.createRigidArea(new Dimension(width, 0)));
    }

    public void addInstanceButtons(JButton... buttons) {
        if (buttons == null) {
            return;
        }

        for (JButton button : buttons) {
            addInstanceButton(button);
        }
    }

    public void addInstanceButton(JButton button) {
        if (button == null) {
            return;
        }

        button.setAlignmentY(Component.CENTER_ALIGNMENT);

        int glueIndex = toolBar.getComponentZOrder(rightSideGlue);
        int insertIndex = glueIndex >= 0 ? glueIndex : toolBar.getComponentCount();

        toolBar.add(button, insertIndex);
        toolBar.revalidate();
        toolBar.repaint();
    }

    private JButton createCloseButton() {
        JButton button = createWindowButton(
                "Close",
                "/buttons/window-close-normal.png",
                "/buttons/window-close-hover.png",
                "/buttons/window-close-pressed.png"
        );
        button.addActionListener(e -> System.exit(0));
        return button;
    }

    private JButton createMinimizeButton() {
        JButton button = createWindowButton(
                "Minimize",
                "/buttons/window-minimize-normal.png",
                "/buttons/window-minimize-hover.png",
                "/buttons/window-minimize-pressed.png"
        );
        button.addActionListener(e -> {
            JFrame frame = getFrame();
            if (frame != null) {
                frame.setExtendedState(frame.getExtendedState() | Frame.ICONIFIED);
                SwingUtilities.invokeLater(() -> frame.setBackground(UITheme.window_bg));
            }
        });
        return button;
    }

    private JButton createMaximizeButton() {
        JButton button = createWindowButton(
                "Maximize",
                "/buttons/window-maximize-normal.png",
                "/buttons/window-maximize-hover.png",
                "/buttons/window-maximize-pressed.png"
        );
        button.addActionListener(e -> {
            JFrame frame = getFrame();
            if (frame == null) {
                return;
            }

            int state = frame.getExtendedState();
            boolean maximized = (state & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH;

            frame.setExtendedState(maximized ? Frame.NORMAL : state | Frame.MAXIMIZED_BOTH);
            SwingUtilities.invokeLater(() -> frame.setBackground(UITheme.window_bg));
        });
        return button;
    }

    private JButton createWindowButton(String tooltip, String normal, String hover, String pressed) {
        return buttonControl.createControlButton(
            "", 
            tooltip, 
            normal, 
            hover, 
            pressed, 
            window_button_size);
    }

    private JFrame getFrame() {
        return (JFrame) toolBar.getTopLevelAncestor();
    }

    public void attachWindowMouseController(WindowMouseController mouseController) {
        toolBar.addMouseListener(mouseController);
        toolBar.addMouseMotionListener(mouseController);
        titleLabel.addMouseListener(mouseController);
        titleLabel.addMouseMotionListener(mouseController);

        for (Component component : toolBar.getComponents()) {
            component.addMouseListener(mouseController);
            component.addMouseMotionListener(mouseController);
        }
    }

    public void setCursorForAllComponents(Cursor cursor) {
        toolBar.setCursor(cursor);
        titleLabel.setCursor(cursor);

        for (Component component : toolBar.getComponents()) {
            component.setCursor(cursor);
        }
    }

    public JToolBar getToolBar() {
        return toolBar;
    }
}
