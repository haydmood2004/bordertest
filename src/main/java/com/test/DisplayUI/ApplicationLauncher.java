package com.test.DisplayUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class ApplicationLauncher {
    private static final int windowWidth = 720;
    private static final int windowHeight = 480;
    private static final String windowTitle = "Graphics Practice";
    private static final int menuBarWidth = 60;

    public static void main(String[] args) throws IOException {
        JFrame frame = createFrame();

        Display display = new Display();
        initializeDisplay(display);

        ResourceManager.BorderImages borderImages = ResourceManager.loadBorderImages();
        ResourceManager.CursorImages cursorImages = ResourceManager.loadCursorImages();
        display.setBorderImages(borderImages);
        display.setOpaque(false);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setOpaque(false);
        layeredPane.setBackground(new Color(0,0,0,0));
        layeredPane.setPreferredSize(frame.getSize());
        frame.setContentPane(layeredPane);
        layeredPane.add(display, JLayeredPane.PALETTE_LAYER);

        ToolBar toolbar = new ToolBar();
        MenuBar menuBar = new MenuBar();
        WindowMouseController mouseController = new WindowMouseController(display);

        display.attachWindowMouseController(mouseController);
        toolbar.attachWindowMouseController(mouseController);
        menuBar.attachWindowMouseController(mouseController);
        layeredPane.add(menuBar.getMenuBar(), JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(toolbar.getToolBar(), JLayeredPane.POPUP_LAYER);
        layeredPane.setLayer(frame.getContentPane(), JLayeredPane.DEFAULT_LAYER);

        FrameLayoutManager layoutManager = new FrameLayoutManager(frame, layeredPane, display, toolbar, menuBar);
        layoutManager.updateLayout();

        frame.setBackground(Color.WHITE);

        Cursor defaultCursor = CursorManager.createCustomCursor(cursorImages.regular, new Point(0, 0));
        Cursor clickCursor = CursorManager.createCustomCursor(cursorImages.click, new Point(0, 0));

        applyCursorsToAllComponents(frame, layeredPane, display, toolbar, defaultCursor);
        mouseController.setCursors(defaultCursor, clickCursor);

        frame.pack();
        frame.setVisible(true);
    }

    private static JFrame createFrame() {
        JFrame frame = new JFrame(windowTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(windowWidth, windowHeight);
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.setLocationRelativeTo(null);
        return frame;
    }

    private static void initializeDisplay(Display display) {
        display.setBackground(new Color(0, 0,0,0));
        display.setSize(windowWidth, windowHeight);
        display.setPreferredSize(display.getSize());
    }

    private static void applyCursorsToAllComponents(JFrame frame, JLayeredPane layeredPane, 
                                                     Display display, ToolBar toolbar, Cursor cursor) {
        frame.setCursor(cursor);
        layeredPane.setCursor(cursor);
        display.setCursor(cursor);
        toolbar.setCursorForAllComponents(cursor);
    }
}
