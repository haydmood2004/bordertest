package com.test.DisplayUI;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class CursorManager {
    private static final Toolkit toolkit = Toolkit.getDefaultToolkit();
    private static final int cursorSize = 32;

    public static Cursor createCustomCursor(BufferedImage cursorImage, Point hotspot) {
        if (cursorImage == null) {
            return Cursor.getDefaultCursor();
        }

        try {
            Image scaledImage = cursorImage.getScaledInstance(cursorSize, cursorSize, Image.SCALE_SMOOTH);
            BufferedImage scaledCursor = new BufferedImage(cursorSize, cursorSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = scaledCursor.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();
            
            return toolkit.createCustomCursor(scaledCursor, hotspot, "custom");
        } catch (Exception e) {
            System.err.println("Failed to create custom cursor: " + e.getMessage());
            return Cursor.getDefaultCursor();
        }
    }
}
