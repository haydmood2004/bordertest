package com.test.DisplayUI;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class CursorManager {
    private static final Toolkit toolkit = Toolkit.getDefaultToolkit();
    private static final int cursor_size = 32;

    private CursorManager() {
    }

    public static Cursor createCustomCursor(BufferedImage cursorImage, Point hotspot) {
        if (cursorImage == null) {
            return Cursor.getDefaultCursor();
        }

        try {
            Image scaledImage = cursorImage.getScaledInstance(cursor_size, cursor_size, Image.SCALE_SMOOTH);
            BufferedImage scaledCursor = new BufferedImage(cursor_size, cursor_size, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = scaledCursor.createGraphics();
            g2.drawImage(scaledImage, 0, 0, null);
            g2.dispose();

            return toolkit.createCustomCursor(scaledCursor, hotspot, "custom-cursor");
        } catch (Exception e) {
            System.err.println("Failed to create custom cursor: " + e.getMessage());
            return Cursor.getDefaultCursor();
        }
    }
}
