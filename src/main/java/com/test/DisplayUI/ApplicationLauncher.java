package com.test.DisplayUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import com.test.DisplayUI.Panels.CustomPanel;

public class ApplicationLauncher {
    private static final int windowWidth = 720;
    private static final int windowHeight = 480;
    private static final String windowTitle = "Graphics Practice";

    public static void main(String[] args) throws IOException {
        JFrame frame = createFrame();

        Display display = new Display();

        CharacterCanvas characterCanvas = new CharacterCanvas();
        ResourceManager.MaleBodyImages maleImages = ResourceManager.loadMaleBodyImages();
        ResourceManager.FaceImages faceImages = ResourceManager.loadFaceImages();
        ResourceManager.ScoleraImages scoleraImages = ResourceManager.loadScoleraImages();
        ResourceManager.IrisImages irisImages = ResourceManager.loadIrisImages();
        ResourceManager.EyeImages eyeImages = ResourceManager.loadEyeImages();
        ResourceManager.NoseImages noseImages = ResourceManager.loadNoseImages();
        ResourceManager.MouthImages mouthImages = ResourceManager.loadMouthImages();
        ResourceManager.EarImages earImages = ResourceManager.loadEarImages();
        characterCanvas.setMaleBodyImages(maleImages.m1_c, maleImages.m1_l);
        characterCanvas.setFace(faceImages.j1_c, faceImages.j1_l);
        characterCanvas.setScolera(scoleraImages.s1);
        characterCanvas.setIris(irisImages.i1_3_c, irisImages.i1_l);
        characterCanvas.setEye(eyeImages.e1_c, eyeImages.e1_l);
        characterCanvas.setNose(noseImages.n1);
        characterCanvas.setMouth(mouthImages.l1);
        characterCanvas.setEar(earImages.e1_c,earImages.e1_l);

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
        CustomPanel customPanel = new CustomPanel();
        MenuBar menuBar = new MenuBar(customPanel, characterCanvas, maleImages, faceImages, scoleraImages, irisImages, eyeImages, noseImages, mouthImages, earImages);

        WindowMouseController mouseController = new WindowMouseController(display);
        customPanel.getColorController().setColorListener(color -> {
            characterCanvas.setSkinTintColor(color);
        });

        display.attachWindowMouseController(mouseController);
        toolbar.attachWindowMouseController(mouseController);
        menuBar.attachWindowMouseController(mouseController);

        layeredPane.add(characterCanvas, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(customPanel.getPanel(), JLayeredPane.PALETTE_LAYER);
        layeredPane.add(menuBar.getMenuBar(), JLayeredPane.PALETTE_LAYER);
        layeredPane.add(display, JLayeredPane.MODAL_LAYER);
        layeredPane.add(toolbar.getToolBar(), JLayeredPane.DRAG_LAYER);

        FrameLayoutManager layoutManager = new FrameLayoutManager(frame, layeredPane, display, toolbar, menuBar,
                customPanel, characterCanvas);
        layoutManager.updateLayout();

        Cursor defaultCursor = CursorManager.createCustomCursor(cursorImages.regular, new Point(4, 2));
        Cursor clickCursor = CursorManager.createCustomCursor(cursorImages.click, new Point(4, 2));

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
        display.setBackground(new Color(0, 0, 0, 0));
        display.setSize(windowWidth, windowHeight);
        display.setPreferredSize(display.getSize());
    }

    private static void applyCursorsToAllComponents(
            JFrame frame,
            JLayeredPane layeredPane,
            Display display,
            ToolBar toolbar,
            Cursor cursor,
            MenuBar menuBar) {
        frame.setCursor(cursor);
        layeredPane.setCursor(cursor);
        display.setCursor(cursor);
        toolbar.setCursorForAllComponents(cursor);
        menuBar.setCursorForAllComponents(cursor);
    }
}