package com.test.DisplayUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonControl {

    public JButton createControlButton(
            String fallbackText,
            String tooltip,
            String regularIconPath,
            String hoverIconPath,
            String pressedIconPath,
            int controlSize
    ) {
        JButton button = new JButton(fallbackText);

        ImageIcon regularIcon = loadScaledIcon(regularIconPath, controlSize, controlSize);
        ImageIcon hoverIcon = loadScaledIcon(hoverIconPath, controlSize, controlSize);
        ImageIcon pressedIcon = loadScaledIcon(pressedIconPath, controlSize, controlSize);

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

        styleControlButton(button, tooltip, new Dimension(controlSize, controlSize));
        button.setRolloverEnabled(true);

        return button;
    }

    public void styleControlButton(JButton button, String tooltip, Dimension controlSize) {
        button.setFocusable(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setAlignmentY(Component.CENTER_ALIGNMENT);
        button.setRolloverEnabled(true);

        button.setBorder(BorderFactory.createEmptyBorder());

        button.setPreferredSize(controlSize);
        button.setMinimumSize(controlSize);
        button.setMaximumSize(controlSize);
        button.setToolTipText(tooltip);
    }

    public ImageIcon loadScaledIcon(String resourcePath, int width, int height) {
        URL resource = ButtonControl.class.getResource(resourcePath);

        if (resource == null) {
            System.out.println("Missing resource: " + resourcePath);
            return null;
        }

        ImageIcon icon = new ImageIcon(resource);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public void rescaleButtonIcons(
            JButton button,
            String regularPath,
            String hoverPath,
            String pressedPath,
            int size
    ) {
        ImageIcon regular = loadScaledIcon(regularPath, size, size);
        ImageIcon hover = loadScaledIcon(hoverPath, size, size);
        ImageIcon pressed = loadScaledIcon(pressedPath, size, size);

        if (regular != null) {
            button.setIcon(regular);
        }

        if (hover != null) {
            button.setRolloverIcon(hover);
        }

        if (pressed != null) {
            button.setPressedIcon(pressed);
        }
    }
}