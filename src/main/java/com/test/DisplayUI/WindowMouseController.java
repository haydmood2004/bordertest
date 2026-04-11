package com.test.DisplayUI;

import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class WindowMouseController extends MouseAdapter {
    private static final int RESIZE_MARGIN = 14;
    private static final double ASPECT_RATIO = 720.0 / 480.0;
    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;

    private final BorderDisplay display;
    private Point startDrag;
    private Point startLocation;
    private Rectangle startBounds;
    private int resizeDirection = 0;
    private boolean resizingEnabled = true;

    public WindowMouseController(BorderDisplay display) {
        this.display = display;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseMoved(e);
        startDrag = e.getLocationOnScreen();
        display.setLiveResizing(resizeDirection != 0);

        JFrame frame = (JFrame) display.getTopLevelAncestor();
        if (frame != null) {
            startLocation = frame.getLocation();
            startBounds = frame.getBounds();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (startDrag == null) {
            return;
        }

        JFrame frame = (JFrame) display.getTopLevelAncestor();
        if (frame == null || startBounds == null) {
            return;
        }

        Point current = e.getLocationOnScreen();
        int dx = (int) (current.getX() - startDrag.getX());
        int dy = (int) (current.getY() - startDrag.getY());

        if (resizeDirection != 0) {
            resizeFrame(frame, dx, dy);
        } else if (startLocation != null) {
            frame.setLocation(startLocation.x + dx, startLocation.y + dy);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!resizingEnabled) {
            return;
        }

        int x = e.getX();
        int y = e.getY();
        int width = display.getWidth();
        int height = display.getHeight();

        int direction = 0;
        if (y <= RESIZE_MARGIN) {
            direction |= 1;
        } else if (y >= height - RESIZE_MARGIN) {
            direction |= 2;
        }

        if (x <= RESIZE_MARGIN) {
            direction |= 4;
        } else if (x >= width - RESIZE_MARGIN) {
            direction |= 8;
        }

        resizeDirection = direction;
        switch (direction) {
            case 1:
                display.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                break;
            case 2:
                display.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                break;
            case 4:
                display.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                break;
            case 8:
                display.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                break;
            case 5:
                display.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
                break;
            case 9:
                display.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                break;
            case 6:
                display.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
                break;
            case 10:
                display.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                break;
            default:
                display.setCursor(Cursor.getDefaultCursor());
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        startDrag = null;
        startLocation = null;
        startBounds = null;
        display.setLiveResizing(false);
        display.repaint();
    }

    private void resizeFrame(JFrame frame, int dx, int dy) {
        boolean hasHorizontalEdge = (resizeDirection & 4) != 0 || (resizeDirection & 8) != 0;
        boolean hasVerticalEdge = (resizeDirection & 1) != 0 || (resizeDirection & 2) != 0;

        int rawWidth = startBounds.width;
        int rawHeight = startBounds.height;

        if ((resizeDirection & 8) != 0) {
            rawWidth = startBounds.width + dx;
        }
        if ((resizeDirection & 4) != 0) {
            rawWidth = startBounds.width - dx;
        }
        if ((resizeDirection & 2) != 0) {
            rawHeight = startBounds.height + dy;
        }
        if ((resizeDirection & 1) != 0) {
            rawHeight = startBounds.height - dy;
        }

        int width;
        int height;
        if (hasHorizontalEdge && hasVerticalEdge) {
            double widthDelta = Math.abs((double) dx / Math.max(1, startBounds.width));
            double heightDelta = Math.abs((double) dy / Math.max(1, startBounds.height));

            if (widthDelta >= heightDelta) {
                width = Math.max(MIN_WIDTH, rawWidth);
                height = Math.max(MIN_HEIGHT, (int) Math.round(width / ASPECT_RATIO));
                width = Math.max(MIN_WIDTH, (int) Math.round(height * ASPECT_RATIO));
            } else {
                height = Math.max(MIN_HEIGHT, rawHeight);
                width = Math.max(MIN_WIDTH, (int) Math.round(height * ASPECT_RATIO));
                height = Math.max(MIN_HEIGHT, (int) Math.round(width / ASPECT_RATIO));
            }
        } else if (hasHorizontalEdge) {
            width = Math.max(MIN_WIDTH, rawWidth);
            height = Math.max(MIN_HEIGHT, (int) Math.round(width / ASPECT_RATIO));
            width = Math.max(MIN_WIDTH, (int) Math.round(height * ASPECT_RATIO));
        } else {
            height = Math.max(MIN_HEIGHT, rawHeight);
            width = Math.max(MIN_WIDTH, (int) Math.round(height * ASPECT_RATIO));
            height = Math.max(MIN_HEIGHT, (int) Math.round(width / ASPECT_RATIO));
        }

        int x;
        if ((resizeDirection & 4) != 0) {
            x = startBounds.x + (startBounds.width - width);
        } else if ((resizeDirection & 8) != 0) {
            x = startBounds.x;
        } else {
            x = startBounds.x + (startBounds.width - width) / 2;
        }

        int y;
        if ((resizeDirection & 1) != 0) {
            y = startBounds.y + (startBounds.height - height);
        } else if ((resizeDirection & 2) != 0) {
            y = startBounds.y;
        } else {
            y = startBounds.y + (startBounds.height - height) / 2;
        }

        frame.setBounds(x, y, width, height);
    }

    public void setResizingEnabled(boolean resizingEnabled) {
        this.resizingEnabled = resizingEnabled;
        if (!resizingEnabled) {
            resizeDirection = 0;
            display.setCursor(Cursor.getDefaultCursor());
        }
    }

    public boolean isResizingEnabled() {
        return resizingEnabled;
    }
}
