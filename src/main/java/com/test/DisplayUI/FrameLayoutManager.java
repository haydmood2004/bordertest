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

    private final JFrame frame;
    private final JLayeredPane layeredPane;
    private final Display display;
    private final ToolBar toolbar;
    private final MenuBar menuBar;


    public FrameLayoutManager(JFrame frame, JLayeredPane layeredPane, Display display, ToolBar toolbar, MenuBar menuBar) {
        this.frame = frame;
        this.layeredPane = layeredPane;
        this.display = display;
        this.toolbar = toolbar;
        this.menuBar = menuBar;
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
                frame.setBackground(new Color(0, 0, 0, 0));
                updateLayout();
            }
        });
    }

    public void updateLayout() {
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        layeredPane.setBounds(0, 0, frameWidth, frameHeight);
        display.setBounds(0, 0, frameWidth, frameHeight);
        toolbar.getToolBar().setBounds(
            topCornerBorderWidth / 2 - 15,
            toolbarYOffset,
            Math.max(1, frameWidth - topCornerBorderWidth + 40),
            topBorderHeight
        );
        menuBar.getMenuBar().setPreferredSize(new Dimension (frameWidth / 12, frameHeight));
        menuBar.getMenuBar().setBounds(0, 0, 60, frameHeight);

        roundFrameShape();
        layeredPane.revalidate();
        display.revalidate();
        display.repaint();
        toolbar.getToolBar().repaint();
        menuBar.getMenuBar().repaint();
    }

    private void roundFrameShape() {
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
