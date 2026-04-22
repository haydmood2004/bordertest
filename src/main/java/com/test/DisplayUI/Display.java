package com.test.DisplayUI;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Display extends JPanel {
    private static final long serialVersionUID = 1L;

    // Border images
    private BufferedImage borderTop, borderBottom, borderLeft, borderRight;
    private BufferedImage borderTopLeft, borderTopRight, borderBottomLeft, borderBottomRight;

    public Display() {
        setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (allBordersLoaded()) {
            drawBordersAndCorners(g2d);
        }
    }

    private void drawBordersAndCorners(Graphics2D g2d) {
        int leftCornerWidth = 80;
        int rightCornerWidth = 80;
        int topBottomHeight = 80;
        int sideHeight = Math.max(1, getHeight() - (topBottomHeight * 2));
        int availableWidth = Math.max(1, getWidth() - leftCornerWidth - rightCornerWidth);
        int rightCornerX = getWidth() - rightCornerWidth;
        int bottomY = getHeight() - topBottomHeight;

        g2d.drawImage(borderTopLeft, 0, 0, leftCornerWidth, topBottomHeight, this);
        g2d.drawImage(borderTopRight, rightCornerX, 0, rightCornerWidth, topBottomHeight, this);
        g2d.drawImage(borderBottomLeft, 0, bottomY, leftCornerWidth, topBottomHeight, this);
        g2d.drawImage(borderBottomRight, rightCornerX, bottomY, rightCornerWidth, topBottomHeight, this);

        int drawX = leftCornerWidth;
        g2d.drawImage(borderTop, drawX, 0, availableWidth, topBottomHeight, this);
        g2d.drawImage(borderBottom, drawX, bottomY, availableWidth, topBottomHeight, this);
        g2d.drawImage(borderLeft, 0, topBottomHeight, leftCornerWidth, sideHeight, this);
        g2d.drawImage(borderRight, getWidth() - rightCornerWidth, topBottomHeight, rightCornerWidth, sideHeight, this);
    }

    private boolean allBordersLoaded() {
        return borderTop != null && borderTopLeft != null && borderTopRight != null && borderBottom != null &&
               borderLeft != null && borderRight != null && borderBottomLeft != null && borderBottomRight != null;
    }

    public void setBorderImages(ResourceManager.BorderImages borders) {
        this.borderTop = borders.top;
        this.borderBottom = borders.bottom;
        this.borderLeft = borders.left;
        this.borderRight = borders.right;
        this.borderTopLeft = borders.topLeft;
        this.borderTopRight = borders.topRight;
        this.borderBottomLeft = borders.bottomLeft;
        this.borderBottomRight = borders.bottomRight;
    }

    public void attachWindowMouseController(WindowMouseController mouseController) {
        addMouseListener(mouseController);
        addMouseMotionListener(mouseController);
    }

} 
