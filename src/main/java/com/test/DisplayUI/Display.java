package com.test.DisplayUI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Display extends JPanel {
    private static final long serialVersionUID = 1L;

    private BufferedImage borderTop;
    private BufferedImage borderBottom;
    private BufferedImage borderLeft;
    private BufferedImage borderRight;
    private BufferedImage borderTopLeft;
    private BufferedImage borderTopRight;
    private BufferedImage borderBottomLeft;
    private BufferedImage borderBottomRight;

    public Display() {
        setDoubleBuffered(true);
        setLayout(null);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);

        if (!allBordersLoaded()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        drawBordersAndCorners(g2);
        g2.dispose();
    }

    private void drawBordersAndCorners(Graphics2D g2) {
        int cornerSize = UITheme.border_size;
        int borderThickness = UITheme.border_size;

        int width = getWidth();
        int height = getHeight();

        int sideHeight = Math.max(1, height - borderThickness * 2);
        int middleWidth = Math.max(1, width - cornerSize * 2);
        int rightX = width - cornerSize;
        int bottomY = height - borderThickness;

        g2.drawImage(borderTopLeft, 0, 0, cornerSize, borderThickness, this);
        g2.drawImage(borderTopRight, rightX, 0, cornerSize, borderThickness, this);
        g2.drawImage(borderBottomLeft, 0, bottomY, cornerSize, borderThickness, this);
        g2.drawImage(borderBottomRight, rightX, bottomY, cornerSize, borderThickness, this);

        g2.drawImage(borderTop, cornerSize, 0, middleWidth, borderThickness, this);
        g2.drawImage(borderBottom, cornerSize, bottomY, middleWidth, borderThickness, this);
        g2.drawImage(borderLeft, 0, borderThickness, cornerSize, sideHeight, this);
        g2.drawImage(borderRight, rightX, borderThickness, cornerSize, sideHeight, this);
    }

    private boolean allBordersLoaded() {
        return borderTop != null
                && borderBottom != null
                && borderLeft != null
                && borderRight != null
                && borderTopLeft != null
                && borderTopRight != null
                && borderBottomLeft != null
                && borderBottomRight != null;
    }

    public void setBorderImages(ResourceManager.BorderImages borders) {
        borderTop = borders.top;
        borderBottom = borders.bottom;
        borderLeft = borders.left;
        borderRight = borders.right;
        borderTopLeft = borders.topLeft;
        borderTopRight = borders.topRight;
        borderBottomLeft = borders.bottomLeft;
        borderBottomRight = borders.bottomRight;
    }

    public void attachWindowMouseController(WindowMouseController mouseController) {
        addMouseListener(mouseController);
        addMouseMotionListener(mouseController);
    }

    @Override
    public boolean contains(int x, int y) {
        return false;
    }
}
