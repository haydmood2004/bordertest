package com.test.DisplayUI;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

public class ToolBar {
    private JToolBar toolBar;

    public ToolBar() {
        toolBar = new JToolBar();
        // Add buttons or other components to the toolbar as needed
        // make horizonal
        toolBar.setOrientation(JToolBar.HORIZONTAL);
        toolBar.setFloatable(false);
        toolBar.setRollover(false);
        toolBar.setBorderPainted(false);
        toolBar.setOpaque(false);
        toolBar.setAlignmentY(JToolBar.TOP_ALIGNMENT);
        toolBar.setMargin(new Insets(0, 0, 0, 0));
        toolBar.setVisible(true);

        // window control buttons and listeners

        

        JButton closeButton = new JButton("X");
        closeButton.addActionListener(e -> {
            // Close the application when the button is clicked
            System.exit(0);
        });
        closeButton.setFocusable(false);
        closeButton.setFocusPainted(false);
        closeButton.setOpaque(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.setForeground(Color.WHITE);
        closeButton.setMargin(new Insets(0, 6, 0, 6));
        closeButton.setAlignmentY(0.0f);
        closeButton.setToolTipText("Close");
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeButton.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButton.setForeground(Color.WHITE);
            }
        });

        JButton minimizeButton = new JButton("-");
        minimizeButton.addActionListener(e -> {
            JFrame frame = (JFrame) toolBar.getTopLevelAncestor();
            if (frame != null) {
                frame.setExtendedState(frame.getExtendedState() | Frame.ICONIFIED);
                SwingUtilities.invokeLater(() -> frame.setBackground(new Color(0, 0, 0, 0)));
            }
        });
        minimizeButton.setFocusable(false);
        minimizeButton.setFocusPainted(false);
        minimizeButton.setOpaque(false);
        minimizeButton.setContentAreaFilled(false);
        minimizeButton.setBorderPainted(false);
        minimizeButton.setForeground(Color.WHITE);
        minimizeButton.setMargin(new Insets(0, 6, 0, 6));
        minimizeButton.setAlignmentY(0.0f);
        minimizeButton.setToolTipText("Minimize");
        minimizeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                minimizeButton.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                minimizeButton.setForeground(Color.WHITE);
            }
        });


        JButton maximizeButton = new JButton("[]");
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
        maximizeButton.setFocusable(false);
        maximizeButton.setFocusPainted(false);
        maximizeButton.setOpaque(false);
        maximizeButton.setContentAreaFilled(false);
        maximizeButton.setBorderPainted(false);
        maximizeButton.setForeground(Color.WHITE);
        maximizeButton.setMargin(new Insets(0, 6, 0, 6));
        maximizeButton.setAlignmentY(0.0f);
        maximizeButton.setToolTipText("Maximize");
        maximizeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                maximizeButton.setForeground(Color.GREEN);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                maximizeButton.setForeground(Color.WHITE);
            }
        });

        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(minimizeButton);
        toolBar.add(maximizeButton);
        toolBar.add(closeButton);
    }

    public JToolBar getToolBar() {
        return toolBar;
    }
}
