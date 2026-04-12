package com.test.DisplayUI;

import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class WindowMouseController extends MouseAdapter {
    private static final int resizeMargin = 14;
    private static final int dragTopInset = 5;
    private static final int dragSideInset = 5;
    private static final double aspectRatio = 720.0 / 480.0;
    private static final int minWidth = 300;
    private static final int minHeight = 200;

    private final Display display;
    private Point startDrag;
    private Point startLocation;
    private Rectangle startBounds;
    private Point pressPoint;
    private int resizeDirection = 0;
    private boolean resizingEnabled = true;
    private boolean draggingWindow = false;
    private boolean isMousePressed = false;
    private static Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Cursor defaultCursor;
    private Cursor clickCursor;

    public WindowMouseController(Display display) {
        this.display = display;
    }

    public void setCursors(Cursor defaultCursor, Cursor clickCursor) {
        this.defaultCursor = defaultCursor;
        this.clickCursor = clickCursor;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        isMousePressed = true;
        Point displayPoint = getDisplayPoint(e);
        
        // Set click cursor on the component that was clicked
        if (clickCursor != null) {
            e.getComponent().setCursor(clickCursor);
        }

        if (e.getComponent() == display) {
            mouseMoved(e);
        }

        startDrag = e.getLocationOnScreen();
        pressPoint = displayPoint;
        draggingWindow = resizeDirection == 0 && isInDragRegion(pressPoint);
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
        } else if (draggingWindow && startLocation != null) {
            frame.setLocation(startLocation.x + dx, startLocation.y + dy);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!resizingEnabled) {
            return;
        }

        if (e.getComponent() != display) {
            resizeDirection = 0;
            // For buttons and toolbar, show default cursor
            if (defaultCursor != null) {
                e.getComponent().setCursor(defaultCursor);
            }
            return;
        }

        int x = e.getX();
        int y = e.getY();
        int width = display.getWidth();
        int height = display.getHeight();

        int direction = 0;
        if (y <= resizeMargin) {
            direction |= 1;
        } else if (y >= height - resizeMargin) {
            direction |= 2;
        }

        if (x <= resizeMargin) {
            direction |= 4;
        } else if (x >= width - resizeMargin) {
            direction |= 8;
        }

        resizeDirection = direction;
        
        // Update cursor based on current position, even while dragging
        if (direction != 0) {
            // At an edge - show resize cursor regardless of mouse state
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
            }
        } else {
            // In the middle area
            if (isMousePressed && clickCursor != null) {
                // While dragging in middle, show click cursor
                display.setCursor(clickCursor);
            } else if (!isMousePressed && defaultCursor != null) {
                // When not pressed, show default cursor in middle
                display.setCursor(defaultCursor);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isMousePressed = false;
        // Restore default cursor on the component that was clicked
        if (defaultCursor != null) {
            e.getComponent().setCursor(defaultCursor);
        }
        startDrag = null;
        startLocation = null;
        startBounds = null;
        pressPoint = null;
        draggingWindow = false;
        display.setLiveResizing(false);
        display.repaint();
    }

    private boolean isInDragRegion(Point point) {
        if (point == null) {
            return false;
        }

        int width = display.getWidth();
        int dragRegionBottom = 30;
        return point.y >= dragTopInset
            && point.y <= dragRegionBottom
            && point.x >= dragSideInset
            && point.x <= Math.max(dragSideInset, width - dragSideInset);
    }

    private Point getDisplayPoint(MouseEvent e) {
        Component source = e.getComponent();
        if (source == display) {
            return e.getPoint();
        }
        return SwingUtilities.convertPoint(source, e.getPoint(), display);
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
                width = Math.max(minWidth, rawWidth);
                height = Math.max(minHeight, (int) Math.round(width / aspectRatio));
                width = Math.max(minWidth, (int) Math.round(height * aspectRatio));
            } else {
                height = Math.max(minHeight, rawHeight);
                width = Math.max(minWidth, (int) Math.round(height * aspectRatio));
                height = Math.max(minHeight, (int) Math.round(width / aspectRatio));
            }
        } else if (hasHorizontalEdge) {
            width = Math.max(minWidth, rawWidth);
            height = Math.max(minHeight, (int) Math.round(width / aspectRatio));
            width = Math.max(minWidth, (int) Math.round(height * aspectRatio));
        } else {
            height = Math.max(minHeight, rawHeight);
            width = Math.max(minWidth, (int) Math.round(height * aspectRatio));
            height = Math.max(minHeight, (int) Math.round(width / aspectRatio));
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
