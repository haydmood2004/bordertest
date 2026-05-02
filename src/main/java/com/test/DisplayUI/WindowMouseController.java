package com.test.DisplayUI;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class WindowMouseController extends MouseAdapter {
    private static final int drag_top = 5;
    private static final int drag_side = 5;
    private static final int drag_region_bottom = 34;

    private final Display display;

    private Point startDrag;
    private Point startLocation;
    private Point pressPoint;

    private boolean draggingWindow;
    private boolean mousePressed;

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
        mousePressed = true;
        setCursor(e.getComponent(), clickCursor);

        startDrag = e.getLocationOnScreen();
        pressPoint = convertToDisplayPoint(e);
        draggingWindow = isInDragRegion(pressPoint);

        JFrame frame = getFrame();
        if (frame != null) {
            startLocation = frame.getLocation();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!draggingWindow || startDrag == null || startLocation == null) {
            return;
        }

        JFrame frame = getFrame();
        if (frame == null) {
            return;
        }

        Point current = e.getLocationOnScreen();
        int dx = current.x - startDrag.x;
        int dy = current.y - startDrag.y;

        frame.setLocation(startLocation.x + dx, startLocation.y + dy);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (mousePressed) {
            setCursor(e.getComponent(), clickCursor);
        } else {
            setCursor(e.getComponent(), defaultCursor);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
        draggingWindow = false;
        startDrag = null;
        startLocation = null;
        pressPoint = null;

        setCursor(e.getComponent(), defaultCursor);
        display.repaint();
    }

    private boolean isInDragRegion(Point point) {
        if (point == null) {
            return false;
        }

        int width = display.getWidth();

        return point.y >= drag_top
                && point.y <= drag_region_bottom
                && point.x >= drag_side
                && point.x <= Math.max(drag_side, width - drag_side);
    }

    private Point convertToDisplayPoint(MouseEvent e) {
        Component source = e.getComponent();

        if (source == display) {
            return e.getPoint();
        }

        return SwingUtilities.convertPoint(source, e.getPoint(), display);
    }

    private JFrame getFrame() {
        return (JFrame) display.getTopLevelAncestor();
    }

    private void setCursor(Component component, Cursor cursor) {
        if (component != null && cursor != null) {
            component.setCursor(cursor);
        }
    }
}
