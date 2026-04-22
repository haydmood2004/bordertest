package com.test.DisplayUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Objects;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

public class ToolBar {
    private static final int ctrlButtonSize = 25;
    private ButtonControl b = new ButtonControl();
    private static final String defaultTitle = "Character Generator";

    private final JToolBar toolBar;
    private final Component rightSideGlue;
    private final JLabel titleLabel;

    public ToolBar() {
        this(defaultTitle);
    }

    public ToolBar(String title, JButton... instanceButtons) {
        toolBar = createToolBarBase();
        titleLabel = createTitleLabel(title);
        toolBar.add(titleLabel);
        rightSideGlue = Box.createHorizontalGlue();
        toolBar.add(rightSideGlue);

        addInstanceButtons(instanceButtons);

        toolBar.add(createMinimizeButton());
        toolBar.addSeparator(new Dimension(5,0));
        toolBar.add(createMaximizeButton());
        toolBar.addSeparator(new Dimension(5, 0));
        toolBar.add(createCloseButton());
        toolBar.addSeparator(new Dimension(10,0));
    }

    public void addInstanceButton(JButton button) {
        if (button == null) {
            return;
        }

        button.setAlignmentY(Component.CENTER_ALIGNMENT);
        int glueIndex = toolBar.getComponentZOrder(rightSideGlue);
        int insertIndex = (glueIndex >= 0) ? glueIndex : toolBar.getComponentCount();
        toolBar.add(button, insertIndex);
        toolBar.revalidate();
        toolBar.repaint();
    }

    public JToolBar getToolBar() {
        return toolBar;
    }

    public void attachWindowMouseController(WindowMouseController mouseController) {
        toolBar.addMouseListener(mouseController);
        toolBar.addMouseMotionListener(mouseController);
        titleLabel.addMouseListener(mouseController);
        titleLabel.addMouseMotionListener(mouseController);

        for (Component component : toolBar.getComponents()) {
            if (component instanceof JButton) {
                component.addMouseListener(mouseController);
                component.addMouseMotionListener(mouseController);
            }
        }
    }

    public void setCursorForAllComponents(java.awt.Cursor cursor) {
        toolBar.setCursor(cursor);
        titleLabel.setCursor(cursor);
        for (Component component : toolBar.getComponents()) {
            component.setCursor(cursor);
        }
    }

    public JToolBar createToolBarBase() {
        JToolBar bar = new JToolBar();
        bar.setOrientation(JToolBar.HORIZONTAL);
        bar.setFloatable(false);
        bar.setRollover(true);
        bar.setBorderPainted(false);
        bar.setOpaque(false);
        bar.setAlignmentY(Component.CENTER_ALIGNMENT);
        bar.setMargin(new Insets(0, 0, 0, 0));
        bar.setAlignmentX(JToolBar.CENTER);
        bar.setVisible(true);
        return bar;
    }

    private JLabel createTitleLabel(String title) {
        JLabel titleLabel = new JLabel(Objects.requireNonNullElse(title, defaultTitle));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.LEFT);
        titleLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new java.awt.Font("OCR A Extended", java.awt.Font.BOLD, 12));
        return titleLabel;
    }

    public void addInstanceButtons(JButton... buttons) {
        if (buttons == null) {
            return;
        }
        for (JButton button : buttons) {
            addInstanceButton(button);
        }
    }

    private JButton createCloseButton() {
        JButton closeButton = b.createControlButton(
            "X",
            "Close",
            "/buttons/window-close-normal.png",
            "/buttons/window-close-hover.png",
            "/buttons/window-close-pressed.png"
        );
        closeButton.addActionListener(e -> System.exit(0));
        return closeButton;
    }

    private JButton createMinimizeButton() {
        JButton minimizeButton = b.createControlButton(
            "-",
            "Minimize",
            "/buttons/window-minimize-normal.png",
            "/buttons/window-minimize-hover.png",
            "/buttons/window-minimize-pressed.png"
        );
        minimizeButton.addActionListener(e -> {
            JFrame frame = (JFrame) toolBar.getTopLevelAncestor();
            if (frame != null) {
                frame.setExtendedState(frame.getExtendedState() | Frame.ICONIFIED);
                SwingUtilities.invokeLater(() -> frame.setBackground(new Color(0, 0, 0, 0)));
            }
        });

        return minimizeButton;
    }

    private JButton createMaximizeButton() {
        JButton maximizeButton = b.createControlButton(
            "[]",
            "Maximize",
            "/buttons/window-maximize-normal.png",
            "/buttons/window-maximize-hover.png",
            "/buttons/window-maximize-pressed.png"
        );
        maximizeButton.addActionListener(e -> {
            JFrame frame = (JFrame) toolBar.getTopLevelAncestor();
            if (frame != null) {
                int state = frame.getExtendedState();
                if ((state & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
                    frame.setExtendedState(Frame.NORMAL);
                } else {
                    frame.setExtendedState(state | Frame.MAXIMIZED_BOTH);
                }
                SwingUtilities.invokeLater(() -> frame.setBackground(new Color(0, 0, 0, 0)));
            }
        });
        return maximizeButton;
    }
}
