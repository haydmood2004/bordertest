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
        layeredPane.setPreferredSize(frame.getSize());
        frame.setContentPane(layeredPane);

        ToolBar toolbar = new ToolBar();
        MenuBar menuBar = new MenuBar();
        CustomPanel customPanel = new CustomPanel();
        WindowMouseController mouseController = new WindowMouseController(display);

        display.attachWindowMouseController(mouseController);
        toolbar.attachWindowMouseController(mouseController);
        menuBar.attachWindowMouseController(mouseController);
        layeredPane.add(menuBar.getMenuBar(), JLayeredPane.PALETTE_LAYER);
        layeredPane.add(customPanel.getPanel(), JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(display, JLayeredPane.MODAL_LAYER);
        layeredPane.add(toolbar.getToolBar(), JLayeredPane.DRAG_LAYER);
        //layeredPane.setLayer(frame.getContentPane(), JLayeredPane.POPUP_LAYER);

        FrameLayoutManager layoutManager = new FrameLayoutManager(frame, layeredPane, display, toolbar, menuBar, customPanel);
        layoutManager.updateLayout();

        Cursor defaultCursor = CursorManager.createCustomCursor(cursorImages.regular, new Point(0, 0));
        Cursor clickCursor = CursorManager.createCustomCursor(cursorImages.click, new Point(0, 0));

        applyCursorsToAllComponents(frame, layeredPane, display, toolbar, defaultCursor, menuBar);
        mouseController.setCursors(defaultCursor, clickCursor);

        frame.pack();
        frame.setVisible(true);
    }

    private static JFrame createFrame() {
        JFrame frame = new JFrame(windowTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(windowWidth, windowHeight);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        return frame;
    }

    private static void initializeDisplay(Display display) {
        display.setBackground(new Color(0, 0,0,0));
        display.setSize(windowWidth, windowHeight);
        display.setPreferredSize(display.getSize());
    }

    private static void applyCursorsToAllComponents(JFrame frame, JLayeredPane layeredPane, 
                                                     Display display, ToolBar toolbar, Cursor cursor, MenuBar menuBar) {
        frame.setCursor(cursor);
        layeredPane.setCursor(cursor);
        display.setCursor(cursor);
        toolbar.setCursorForAllComponents(cursor);
        menuBar.setCursorForAllComponents(cursor);
    }
}
