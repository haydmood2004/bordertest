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

public class MenuBar extends JPanel{ 
    private final JFrame frame;
    private JPanel menuBar = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = 60;
                int height = frame.getHeight();
                Color color1 = new Color(146, 6,99);
                Color color2 = color1.darker();
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };

    public MenuBar() {
        this.frame = new JFrame();
        this.menuBar = new JPanel();
        menuBar.setLayout(null);
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

    public JPanel getMenuBar() {
        return menuBar;
    }
}
