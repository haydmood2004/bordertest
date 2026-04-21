package com.test.DisplayUI;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class WindowMouseController extends MouseAdapter {
    private static final int dragTopInset = 5;
    private static final int dragSideInset = 5;

    private final Display display;
    private Point startDrag;
    private Point startLocation;
    private Point pressPoint;
    private boolean draggingWindow = false;
    private boolean isMousePressed = false;
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
        draggingWindow = isInDragRegion(pressPoint);

        JFrame frame = (JFrame) display.getTopLevelAncestor();
        if (frame != null) {
            startLocation = frame.getLocation();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (startDrag == null) {
            return;
        }

        JFrame frame = (JFrame) display.getTopLevelAncestor();
        if (frame == null) {
            return;
        }

        Point current = e.getLocationOnScreen();
        int dx = (int) (current.getX() - startDrag.getX());
        int dy = (int) (current.getY() - startDrag.getY());

        if (draggingWindow && startLocation != null) {
            frame.setLocation(startLocation.x + dx, startLocation.y + dy);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getComponent() != display) {
            // For buttons and toolbar, show default cursor
            if (defaultCursor != null) {
                e.getComponent().setCursor(defaultCursor);
            }
            return;
        }
        
        // Show appropriate cursor based on mouse state
        if (isMousePressed && clickCursor != null) {
            // While dragging in middle, show click cursor
            display.setCursor(clickCursor);
        } else if (!isMousePressed && defaultCursor != null) {
            // When not pressed, show default cursor
            display.setCursor(defaultCursor);
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
        pressPoint = null;
        draggingWindow = false;
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
}
