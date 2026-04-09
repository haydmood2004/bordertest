package com.test.DisplayUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class BorderDisplay extends JPanel implements MouseListener, MouseMotionListener {

    // constants

    private static final int TOP_BORDER_HEIGHT = 80;
    private static final int TOP_CORNER_BORDER_WIDTH = 80;
    private static final int TOOLBAR_Y_OFFSET = 5;
    private static final int TOOLBAR_X_OFFSET = 50;
    private static final int TOOLBAR_RIGHT_OVERHANG = 12;
    private BufferedImage bt, bb, bl, br, btl, btr, bbl, bbr;
    private static final long serialVersionUID = 1L;     
    private Point start_drag;   
    private Point start_loc;  
    private Rectangle startBounds;
    private boolean liveResizing = false;
    private static final int RESIZE_MARGIN = 14;
    private static final double ASPECT_RATIO = 720.0 / 480.0;
    private boolean resizing = true;
    private int resizeDirection = 0; 

    public BorderDisplay() {
        addMouseListener(this);
        addMouseMotionListener(this);
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
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.setLocationRelativeTo(null);

        canvas.setBackground(new Color(0, 0, 0, 0));
        canvas.setSize(frame.getSize());
        canvas.setPreferredSize(frame.getSize());

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

        // Use faster interpolation while the user is actively resizing for smoother feedback.
        Object interpolation = liveResizing
            ? RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
            : RenderingHints.VALUE_INTERPOLATION_BILINEAR;
        Object renderMode = liveResizing
            ? RenderingHints.VALUE_RENDER_SPEED
            : RenderingHints.VALUE_RENDER_QUALITY;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, interpolation);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, renderMode);

        if (bt != null && btl != null && btr != null && bb != null && bl != null && br != null && bbl != null && bbr != null) {
            int leftCornerWidth = TOP_CORNER_BORDER_WIDTH;
            int rightCornerWidth = TOP_CORNER_BORDER_WIDTH;
            int topBottomHeight = TOP_BORDER_HEIGHT;
            int sideHeight = Math.max(1, getHeight() - (topBottomHeight * 2));
            int availableWidth = Math.max(1, getWidth() - leftCornerWidth - rightCornerWidth);

            int rightCornerX = getWidth() - rightCornerWidth;
            int bottomY = getHeight() - topBottomHeight;

            g2d.drawImage(btl, 0, 0, leftCornerWidth, topBottomHeight, this);
            g2d.drawImage(btr, rightCornerX, 0, rightCornerWidth, topBottomHeight, this);
            g2d.drawImage(bbl, 0, bottomY, leftCornerWidth, topBottomHeight, this);
            g2d.drawImage(bbr, rightCornerX, bottomY, rightCornerWidth, topBottomHeight, this);

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

        layered.setBounds(0, 0, frameWidth, frameHeight);
        canvas.setBounds(0, 0, frameWidth, frameHeight);
        toolBarWrapper.getToolBar().setBounds(
            TOP_CORNER_BORDER_WIDTH/2 - 15,
            TOOLBAR_Y_OFFSET,
            Math.max(1, frameWidth - TOP_CORNER_BORDER_WIDTH+40),
            TOP_BORDER_HEIGHT
        );
        canvas.repaint();
        toolBarWrapper.getToolBar().repaint();
    }


    public void mousePressed(MouseEvent e) {
        mouseMoved(e);
        start_drag = e.getLocationOnScreen();
        liveResizing = resizeDirection != 0;
        JFrame frame = (JFrame) this.getTopLevelAncestor();
        if (frame != null) {
            start_loc = frame.getLocation();
            startBounds = frame.getBounds();
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (start_drag == null) {
            return;
        }

        JFrame frame = (JFrame) this.getTopLevelAncestor();
        if (frame == null || startBounds == null) {
            return;
        }

        Point current = e.getLocationOnScreen();
        int dx = (int) (current.getX() - start_drag.getX());
        int dy = (int) (current.getY() - start_drag.getY());

        if (resizeDirection != 0) {
            int minW = 300;
            int minH = 200;

            boolean hasHorizontalEdge = (resizeDirection & 4) != 0 || (resizeDirection & 8) != 0;
            boolean hasVerticalEdge = (resizeDirection & 1) != 0 || (resizeDirection & 2) != 0;

            int rawW = startBounds.width;
            int rawH = startBounds.height;

            if ((resizeDirection & 8) != 0) {
                rawW = startBounds.width + dx;
            }
            if ((resizeDirection & 4) != 0) {
                rawW = startBounds.width - dx;
            }
            if ((resizeDirection & 2) != 0) {
                rawH = startBounds.height + dy;
            }
            if ((resizeDirection & 1) != 0) {
                rawH = startBounds.height - dy;
            }

            int w;
            int h;
            if (hasHorizontalEdge && hasVerticalEdge) {
                double widthDelta = Math.abs((double) dx / Math.max(1, startBounds.width));
                double heightDelta = Math.abs((double) dy / Math.max(1, startBounds.height));

                if (widthDelta >= heightDelta) {
                    w = Math.max(minW, rawW);
                    h = Math.max(minH, (int) Math.round(w / ASPECT_RATIO));
                    w = Math.max(minW, (int) Math.round(h * ASPECT_RATIO));
                } else {
                    h = Math.max(minH, rawH);
                    w = Math.max(minW, (int) Math.round(h * ASPECT_RATIO));
                    h = Math.max(minH, (int) Math.round(w / ASPECT_RATIO));
                }
            } else if (hasHorizontalEdge) {
                w = Math.max(minW, rawW);
                h = Math.max(minH, (int) Math.round(w / ASPECT_RATIO));
                w = Math.max(minW, (int) Math.round(h * ASPECT_RATIO));
            } else {
                h = Math.max(minH, rawH);
                w = Math.max(minW, (int) Math.round(h * ASPECT_RATIO));
                h = Math.max(minH, (int) Math.round(w / ASPECT_RATIO));
            }

            int x;
            if ((resizeDirection & 4) != 0) {
                x = startBounds.x + (startBounds.width - w);
            } else if ((resizeDirection & 8) != 0) {
                x = startBounds.x;
            } else {
                x = startBounds.x + (startBounds.width - w) / 2;
            }

            int y;
            if ((resizeDirection & 1) != 0) {
                y = startBounds.y + (startBounds.height - h);
            } else if ((resizeDirection & 2) != 0) {
                y = startBounds.y;
            } else {
                y = startBounds.y + (startBounds.height - h) / 2;
            }

            frame.setBounds(x, y, w, h);
        } else if (start_loc != null) {
            frame.setLocation(start_loc.x + dx, start_loc.y + dy);
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (!resizing) return;
        int x = e.getX();
        int y = e.getY();
        int width = getWidth();
        int height = getHeight();
        int direction = 0;
        if (y <= RESIZE_MARGIN) direction |= 1; // N
        else if (y >= height - RESIZE_MARGIN) direction |= 2; // S
        if (x <= RESIZE_MARGIN) direction |= 4; // W
        else if (x >= width - RESIZE_MARGIN) direction |= 8; // E
        resizeDirection = direction;
        switch (direction) {
            case 1: setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR)); break;
            case 2: setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR)); break;
            case 4: setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR)); break;
            case 8: setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR)); break;
            case 5: setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR)); break;
            case 9: setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR)); break;
            case 6: setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR)); break;
            case 10: setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR)); break;
            default: setCursor(Cursor.getDefaultCursor()); 
            break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        start_drag = null;
        start_loc = null;
        startBounds = null;
        liveResizing = false;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
} 