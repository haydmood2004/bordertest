package com.test.DisplayUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import com.test.DisplayUI.Panels.CustomPanel;

public class FrameLayoutManager {
    private static final int toolbar_y = -20;
    private static final int min_menu_width = 64;
    private static final int max_menu_width = 96;
    private static final int panel_width_min = 210;
    private static final int panel_width_max = 330;

    private int lastMenuButtonSize = -1;

    private final JFrame frame;
    private final JLayeredPane layeredPane;
    private final Display display;
    private final ToolBar toolbar;
    private final MenuBar menuBar;
    private final CustomPanel customPanel;

    public FrameLayoutManager(JFrame frame, JLayeredPane layeredPane, Display display, ToolBar toolbar,
            MenuBar menuBar, CustomPanel customPanel) {
        this.frame = frame;
        this.layeredPane = layeredPane;
        this.display = display;
        this.toolbar = toolbar;
        this.menuBar = menuBar;
        this.customPanel = customPanel;
        installListeners();
    }

    private void installListeners() {
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateLayout();
            }
        });

        frame.addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                frame.setBackground(UITheme.window_bg);
                updateLayout();
            }
        });
    }

    public void updateLayout() {
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        frame.setBackground(UITheme.window_bg);

        layeredPane.setBounds(0, 0, frameWidth, frameHeight);
        display.setBounds(0, 0, frameWidth, frameHeight);

        layoutToolbar(frameWidth);
        layoutMenu(frameWidth, frameHeight);
        layoutCustomPanel(frameWidth, frameHeight);

        roundFrameShape();
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    private void layoutToolbar(int frameWidth) {
        int toolbarX = UITheme.border_size / 2 - 15;
        int toolbarWidth = Math.max(1, frameWidth - UITheme.border_size + 40);

        toolbar.getToolBar().setBounds(toolbarX, toolbar_y, toolbarWidth, UITheme.border_size
        );
    }

    private void layoutMenu(int frameWidth, int frameHeight) {
        int menuWidth = clamp(frameWidth / 12, min_menu_width, max_menu_width);
        int buttonSize = Math.max(42, menuWidth - 22);

        menuBar.getMenuBar().setBounds(0, 20, menuWidth, frameHeight);
        menuBar.getMenuBar().setPreferredSize(new Dimension(menuWidth, frameHeight));

        if (buttonSize != lastMenuButtonSize) {
            menuBar.resizeButtons(buttonSize);
            lastMenuButtonSize = buttonSize;
        }
    }

    private void layoutCustomPanel(int frameWidth, int frameHeight) {
        int menuWidth = clamp(frameWidth / 12, min_menu_width, max_menu_width);

        int margin = UITheme.space_lg;

        int topInset = 70;
        int bottomInset = 35;

        int availableWidth = frameWidth - menuWidth - margin * 2;
        int availableHeight = frameHeight - topInset - bottomInset;

        int panelWidth = clamp(frameWidth / 3, 260, 420);

        int panelHeight = Math.max(260, availableHeight);

        int x = menuWidth + margin;
        int y = topInset;

        customPanel.getPanel().setBounds(x, y, panelWidth, panelHeight);
        customPanel.updateLayout();
    }

    private void roundFrameShape() {
        if ((frame.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
            frame.setShape(null);
            return;
        }

        GraphicsDevice device = frame.getGraphicsConfiguration().getDevice();
        if (!device.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSPARENT)) {
            return;
        }

        double width = Math.max(1, frame.getWidth());
        double height = Math.max(1, frame.getHeight());
        double arc = Math.min(UITheme.frame_radius, Math.min(width, height));

        frame.setShape(new RoundRectangle2D.Double(0, 0, width, height, arc, arc));
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }
}
