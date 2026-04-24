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

    public JButton createControlButton(String fallbackText, String tooltip, String regularIconPath, String hoverIconPath, String pressedIconPath, int ctrlButtonSize) {

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

        styleControlButton(button, tooltip, ctrlButtonSize);

        // Let Swing manage state changes
        button.setRolloverEnabled(true);

        return button;
    }

    public void styleControlButton(JButton button, String tooltip, int ctrlButtonSize) {
        button.setFocusable(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setAlignmentY(Component.CENTER_ALIGNMENT);

        // helps rollover/pressed state behave more predictably
        button.setBorder(BorderFactory.createEmptyBorder());

        Dimension controlSize = new Dimension(ctrlButtonSize, ctrlButtonSize);
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
}