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

public class FrameLayoutManager {
    private static final int topBorderHeight = 80;
    private static final int topCornerBorderWidth = 80;
    private static final int toolbarYOffset = -20;
    private static final int cornerRound = 50;
    private int lastMenuWidth = -1;
    private int lastMenuButtonSize = -1;

    private final JFrame frame;
    private final JLayeredPane layeredPane;
    private final Display display;
    private final ToolBar toolbar;
    private final MenuBar menuBar;
    private final CustomPanel customPanel;


    public FrameLayoutManager(JFrame frame, JLayeredPane layeredPane, Display display, ToolBar toolbar, MenuBar menuBar, CustomPanel customPanel) {
        this.frame = frame;
        this.layeredPane = layeredPane;
        this.display = display;
        this.toolbar = toolbar;
        this.menuBar = menuBar;
        this.customPanel = customPanel;
        setupListeners();
    }

    private void setupListeners() {
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateLayout();
            }
        });

        frame.addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                frame.setBackground(new Color(216, 240, 241));
                updateLayout();
            }
        });
    }

    public void updateLayout() {
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();


        frame.setBackground(new Color(216,240,241));

        layeredPane.setBounds(0, 0, frameWidth, frameHeight);
        display.setBounds(0, 0, frameWidth, frameHeight);
        toolbar.getToolBar().setBounds(
            topCornerBorderWidth / 2 - 15,
            toolbarYOffset,
            Math.max(1, frameWidth - topCornerBorderWidth + 40),
            topBorderHeight
        );;

        int menuWidth = Math.max(60, frameWidth / 12);
        int menuButtonSize = Math.max(40, menuWidth - 20);

        if (menuWidth != lastMenuWidth) {
            menuBar.getMenuBar().setPreferredSize(new Dimension(menuWidth, frameHeight));
            menuBar.getMenuBar().setBounds(0, 0, menuWidth, frameHeight);
            lastMenuWidth = menuWidth;
        } else {
            menuBar.getMenuBar().setBounds(0, 0, menuWidth, frameHeight);
        }

        if (menuButtonSize != lastMenuButtonSize) {
            menuBar.resizeButtons(menuButtonSize);
            lastMenuButtonSize = menuButtonSize;
        }

        customPanel.getPanel().setPreferredSize(new Dimension(frameWidth/4, frameHeight));
        customPanel.getPanel().setBounds(60, 0, frameWidth/4, frameHeight);

        roundFrameShape();
        layeredPane.revalidate();
        layeredPane.repaint();
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
        double arc = Math.min(50, Math.min(width, height));

        frame.setShape(new RoundRectangle2D.Double(0, 0, width, height, arc, arc));
    }

    public int getTopborderheight() {
        return topBorderHeight;
    }
}
