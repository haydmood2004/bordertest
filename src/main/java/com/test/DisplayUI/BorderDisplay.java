package com.test.DisplayUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class BorderDisplay extends JPanel {

    // constants

    private static final int topBorderHeight = 80;
    private static final int topCornerBorderWidth = 80;
    private static final int toolBarYOffset = -20;
    private static final int FRAME_CORNER_ARC = 50;
    private static final float BACKGROUND_TINT_HUE = Color.RGBtoHSB(233, 0, 0, null)[0];
    private static final float MIN_TINTABLE_SATURATION = 0.08f;
    private static final float WHITE_PRESERVE_BRIGHTNESS = 0.92f;
    private static final float GENERATED_SATURATION_STRENGTH = 0.65f;
    private BufferedImage bt, bb, bl, br, btl, btr, bbl, bbr, bg;
    private static final long serialVersionUID = 1L;     
    private boolean liveResizing = false;

    public BorderDisplay() {
        WindowMouseController mouseController = new WindowMouseController(this);
        addMouseListener(mouseController);
        addMouseMotionListener(mouseController);
        setDoubleBuffered(true);
    }

    public static void main(String[] args) throws IOException {
        System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("sun.java2d.d3d", "False");
        
        JFrame frame = new JFrame("Graphics Practice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderDisplay canvas = new BorderDisplay();
        frame.setSize(720,480);
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0,0));
        frame.setLocationRelativeTo(null);

        canvas.setBackground(new Color(0, 0, 0, 0));
        canvas.setSize(frame.getSize());
        canvas.setPreferredSize(frame.getSize());
        // background
        canvas.bg = ImageIO.read(new File("src/main/resources/background base.png"));
        // edges
        canvas.bt = ImageIO.read(new File("src/main/resources/edges/Border CG T.png"));
        canvas.bb = ImageIO.read(new File("src/main/resources/edges/Border CG B.png"));
        canvas.bl = ImageIO.read(new File("src/main/resources/edges/Border CG L.png"));
        canvas.br = ImageIO.read(new File("src/main/resources/edges/Border CG R.png"));
        // corners
        canvas.btl = ImageIO.read(new File("src/main/resources/corner/Border CG TL - Copy.png"));
        canvas.btr = ImageIO.read(new File("src/main/resources/corner/Border CG TR.png"));
        canvas.bbl = ImageIO.read(new File("src/main/resources/corner/Border CG BL.png"));
        canvas.bbr = ImageIO.read(new File("src/main/resources/corner/Border CG BR.png"));

        JLayeredPane layered = new JLayeredPane();
        layered.setLayout(null);
        layered.setOpaque(false);
        layered.setPreferredSize(frame.getSize());
        frame.setContentPane(layered);
        layered.add(canvas, JLayeredPane.DEFAULT_LAYER);


        ToolBar toolBarWrapper = new ToolBar();
        layered.add(toolBarWrapper.getToolBar(), JLayeredPane.PALETTE_LAYER);

        updateLayout(frame, layered, canvas, toolBarWrapper);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateLayout(frame, layered, canvas, toolBarWrapper);
            }
        });

        frame.addWindowStateListener(e -> {
            frame.setBackground(new Color(0, 0, 0, 0));
            updateLayout(frame, layered, canvas, toolBarWrapper);
        });

        

        frame.pack();
        frame.setVisible(true);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // make resizing the frame faster by making rendering lower quality
        // then restore quality when done dragging
        Object interpolation = liveResizing
            ? RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
            : RenderingHints.VALUE_INTERPOLATION_BILINEAR;
        Object renderMode = liveResizing
            ? RenderingHints.VALUE_RENDER_SPEED
            : RenderingHints.VALUE_RENDER_QUALITY;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, interpolation);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, renderMode);

        // draw background image as a base

        if (bg != null) {
            g2d.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }

        // draw borders and corners. corners remain fixed while edges are stretched to fit
        // the size of the frame as resized.

        if (bt != null && btl != null && btr != null && bb != null && bl != null && br != null && bbl != null && bbr != null) {
            int leftCornerWidth = topCornerBorderWidth; 
            int rightCornerWidth = topCornerBorderWidth; // corners have the same width
            int topBottomHeight = topBorderHeight; // top border is thicker
            int sideHeight = Math.max(1, getHeight() - (topBottomHeight * 2)); // getHeight() used for calculating
                //resizing. Math.max used to prevent a 0 or negative height to prevent errors
            int availableWidth = Math.max(1, getWidth() - leftCornerWidth - rightCornerWidth); // same for width, just different scaling

            int rightCornerX = getWidth() - rightCornerWidth;
            int bottomY = getHeight() - topBottomHeight;

            // draw corners
            g2d.drawImage(btl, 0, 0, leftCornerWidth, topBottomHeight, this);
            g2d.drawImage(btr, rightCornerX, 0, rightCornerWidth, topBottomHeight, this);
            g2d.drawImage(bbl, 0, bottomY, leftCornerWidth, topBottomHeight, this);
            g2d.drawImage(bbr, rightCornerX, bottomY, rightCornerWidth, topBottomHeight, this);

            // draw edges
            int drawX = leftCornerWidth;
            g2d.drawImage(bt, drawX, 0, availableWidth, topBottomHeight, this);
            g2d.drawImage(bb, drawX, bottomY, availableWidth, topBottomHeight, this);
            g2d.drawImage(bl, 0, topBottomHeight, leftCornerWidth, sideHeight, this);
            g2d.drawImage(br, getWidth() - rightCornerWidth, topBottomHeight, rightCornerWidth, sideHeight, this);
            return;
        }
    }

    private static void updateLayout(JFrame frame, JLayeredPane layered, BorderDisplay canvas, ToolBar toolBarWrapper) {
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        // update the bounds of the layers(layered pane, tool bar, canvas) to fit new resize
        layered.setBounds(0, 0, frameWidth, frameHeight);
        canvas.setBounds(0, 0, frameWidth, frameHeight);
        toolBarWrapper.getToolBar().setBounds(
            topCornerBorderWidth/2 - 15,
            toolBarYOffset,
            Math.max(1, frameWidth - topCornerBorderWidth+40),
            topBorderHeight
        );
        applyRoundedFrameShape(frame);
        canvas.repaint();
        toolBarWrapper.getToolBar().repaint();
    }

    private static void applyRoundedFrameShape(JFrame frame) {

        // platform check for pixel transparency support to prevent any errors
        GraphicsDevice device = frame.getGraphicsConfiguration().getDevice();
        if (!device.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSPARENT)) {
            return;
        }

        double width = Math.max(1, frame.getWidth());
        double height = Math.max(1, frame.getHeight());
        double arc = Math.min(FRAME_CORNER_ARC, Math.min(width, height)); // ensure the arc is not larger than the frame dimensions

        frame.setShape(new RoundRectangle2D.Double(0,0,width,height,arc,arc));
    }

    public void setLiveResizing(boolean liveResizing) {
        this.liveResizing = liveResizing;
    }

} 