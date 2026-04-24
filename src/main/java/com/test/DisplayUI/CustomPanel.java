package com.test.DisplayUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CustomPanel {
    private JPanel panel = new JPanel();
    private ButtonControl b = new ButtonControl();

    public CustomPanel() {
        setupPanel();
    }

    public void setupPanel() {
        panel.setLayout(null);
        panel.setOpaque(true);
        panel.setBackground(new Color(190, 80, 144));
    }

    public JPanel getPanel() {
        return panel;
    }
}
