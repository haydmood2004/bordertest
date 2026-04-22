package com.test.DisplayUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonControl {
    private static final int ctrlButtonSize = 40;

    public JButton createControlButton(String fallbackText, String tooltip, String regularIconPath, String hoverIconPath,
            String pressedIconPath) {
        JButton button = new JButton(fallbackText);

        ImageIcon regularIcon = loadScaledIcon(regularIconPath, ctrlButtonSize, ctrlButtonSize);
        ImageIcon hoverIcon = loadScaledIcon(hoverIconPath, ctrlButtonSize, ctrlButtonSize);
        ImageIcon pressedIcon = loadScaledIcon(pressedIconPath, ctrlButtonSize, ctrlButtonSize);

        if (regularIcon != null) {
            button.setText("");
            button.setIcon(regularIcon);
        }
        if (hoverIcon != null) {
            button.setRolloverIcon(hoverIcon);
        }
        if (pressedIcon != null) {
            button.setPressedIcon(pressedIcon);
        }

        button.setRolloverEnabled(hoverIcon != null);
        installIconStateMouseHandler(button, regularIcon, hoverIcon, pressedIcon);

        styleControlButton(button, tooltip);
        return button;
    }

    public void installIconStateMouseHandler(JButton button, ImageIcon regularIcon, ImageIcon hoverIcon,
            ImageIcon pressedIcon) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (hoverIcon != null) {
                    button.setIcon(hoverIcon);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (regularIcon != null) {
                    button.setIcon(regularIcon);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (pressedIcon != null) {
                    button.setIcon(pressedIcon);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (hoverIcon != null && button.contains(e.getPoint())) {
                    button.setIcon(hoverIcon);
                } else if (regularIcon != null) {
                    button.setIcon(regularIcon);
                }
            }
        });
    }

    public void styleControlButton(JButton button, String tooltip) {
        button.setFocusable(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setAlignmentY(Component.CENTER_ALIGNMENT);

        Dimension controlSize = new Dimension(ctrlButtonSize, ctrlButtonSize);
        button.setPreferredSize(controlSize);
        button.setMinimumSize(controlSize);
        button.setMaximumSize(controlSize);
        button.setToolTipText(tooltip);
    }

    public ImageIcon loadScaledIcon(String resourcePath, int width, int height) {
        URL resource = ToolBar.class.getResource(resourcePath);
        if (resource == null) {
            return null;
        }
        ImageIcon icon = new ImageIcon(resource);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
