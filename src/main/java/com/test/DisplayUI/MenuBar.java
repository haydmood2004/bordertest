package com.test.DisplayUI;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.test.DisplayUI.Panels.CustomPanel;

public class MenuBar {
    private final JPanel menuBar = new JPanel();
    private final ButtonControl b = new ButtonControl();
    private final CustomPanel customPanel;

    private final String REG = "/buttons/default button reg.png";
    private final String HOVER = "/buttons/default button hover.png";
    private final String PRESS = "/buttons/default button click.png";

    private int lastIconSize = -1;

    private JButton bodyButton;
    private JButton faceButton;
    private JButton hairButton;
    private JButton topButton;
    private JButton bottomButton;
    private JButton fullDressButton;
    private JButton shoeButton;
    private JButton accessoryButton;
    private final CharacterCanvas characterCanvas;
    private final ResourceManager.MaleBodyImages maleImages;
    private final ResourceManager.FaceImages faceImages;
    private final ResourceManager.ScoleraImages scoleraImages;
    private final ResourceManager.IrisImages irisImages;
    private final ResourceManager.EyeImages eyeImages;
    private final ResourceManager.NoseImages noseImages;
    private final ResourceManager.MouthImages mouthImages;
    private final ResourceManager.EarImages earImages;

    public MenuBar(CustomPanel customPanel,
                CharacterCanvas characterCanvas, ResourceManager.MaleBodyImages maleImages, ResourceManager.FaceImages faceImages, ResourceManager.ScoleraImages scoleraImages,
                ResourceManager.IrisImages irisImages, ResourceManager.EyeImages eyeImages, ResourceManager.NoseImages noseImages, ResourceManager.MouthImages mouthImages,
                ResourceManager.EarImages earImages) {

        this.customPanel = customPanel;
        this.characterCanvas = characterCanvas;
        this.maleImages = maleImages;
        this.faceImages = faceImages;
        this.scoleraImages = scoleraImages;
        this.irisImages = irisImages;
        this.eyeImages = eyeImages;
        this.noseImages = noseImages;
        this.mouthImages = mouthImages;
        this.earImages = earImages;

        setupMenuBar();
        createButtons();
        addButtons();
        bodyButton.doClick();
    }

    private void setupMenuBar() {
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));
        menuBar.setBackground(new java.awt.Color(245, 175, 201));
        menuBar.setOpaque(true);
        menuBar.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 7));
    }

    private void createButtons() {
        bodyButton = createButton("Customize Body");
        faceButton = createButton("Customize Face");
        hairButton = createButton("Customize Hair");
        topButton = createButton("Customize Top Clothing");
        bottomButton = createButton("Customize Bottom Clothing");
        fullDressButton = createButton("Customize Outfit");
        shoeButton = createButton("Customize Shoes");
        accessoryButton = createButton("Customize Accessories");
        
        bodyButton.addActionListener(e ->
        customPanel.showBodyCategory(
            color -> characterCanvas.setSkinTintColor(color),
            bodyIndex -> {
                switch (bodyIndex) {
                    case 0 -> characterCanvas.setMaleBodyImages(maleImages.m1_c, maleImages.m1_l);
                    case 1 -> characterCanvas.setMaleBodyImages(maleImages.m2_c, maleImages.m2_l);
                    case 2 -> characterCanvas.setMaleBodyImages(maleImages.m3_c, maleImages.m3_l);
                    case 3 -> characterCanvas.setMaleBodyImages(maleImages.m4_c, maleImages.m4_l);
                    case 4 -> characterCanvas.setMaleBodyImages(maleImages.m5_c, maleImages.m5_l);
                    case 5 -> characterCanvas.setMaleBodyImages(maleImages.m6_c, maleImages.m6_l);
                    case 6 -> characterCanvas.setMaleBodyImages(maleImages.m7_c, maleImages.m7_l);
                    default -> characterCanvas.setMaleBodyImages(maleImages.m1_c, maleImages.m1_l);
                }
            },
            faceIndex -> {
                switch (faceIndex) {
                    case 0 -> characterCanvas.setFace(faceImages.j1_c, faceImages.j1_l);
                    case 1 -> characterCanvas.setFace(faceImages.j2_c, faceImages.j2_l);
                    case 2 -> characterCanvas.setFace(faceImages.j3_c, faceImages.j3_l);
                    case 3 -> characterCanvas.setFace(faceImages.j4_c, faceImages.j4_l);
                    case 4 -> characterCanvas.setFace(faceImages.j5_c, faceImages.j5_l);
                    case 5 -> characterCanvas.setFace(faceImages.j6_c, faceImages.j6_l);
                    case 6 -> characterCanvas.setFace(faceImages.j7_c, faceImages.j7_l);
                    case 7 -> characterCanvas.setFace(faceImages.j8_c, faceImages.j8_l);
                    case 8 -> characterCanvas.setFace(faceImages.j9_c, faceImages.j9_l);
                    case 9 -> characterCanvas.setFace(faceImages.j10_c, faceImages.j10_l);
                    case 10 -> characterCanvas.setFace(faceImages.j11_c, faceImages.j11_l);
                    default -> characterCanvas.setFace(faceImages.j1_c, faceImages.j1_l);
                }
            },
            earIndex -> {
                switch (earIndex) {
                    case 0 -> characterCanvas.setEar(earImages.e1_c, earImages.e1_l);
                    case 1 -> characterCanvas.setEar(earImages.e2_c, earImages.e2_l);
                    case 2 -> characterCanvas.setEar(earImages.e3_c, earImages.e3_l);
                    case 3 -> characterCanvas.setEar(earImages.e4_c, earImages.e4_l);
                    case 4 -> characterCanvas.setEar(earImages.e5_c, earImages.e5_l);
                    case 5 -> characterCanvas.setEar(earImages.e6_c, earImages.e6_l);
                    case 6 -> characterCanvas.setEar(earImages.e7_c, earImages.e7_l);
                    case 7 -> characterCanvas.setEar(earImages.e8_c, earImages.e8_l);
                    default -> characterCanvas.setEar(earImages.e1_c, earImages.e1_l);
                }
            },
            noseIndex -> {
                switch (noseIndex) {
                    case 0 -> characterCanvas.setNose(noseImages.n1);
                    case 1 -> characterCanvas.setNose(noseImages.n2);
                    case 2 -> characterCanvas.setNose(noseImages.n3);
                    case 3 -> characterCanvas.setNose(noseImages.n4);
                    case 4 -> characterCanvas.setNose(noseImages.n5);
                    default -> characterCanvas.setNose(noseImages.n1);
                }
            },
            mouthIndex -> {
                switch (mouthIndex) {
                    case 0 -> characterCanvas.setMouth(mouthImages.l1);
                    case 1 -> characterCanvas.setMouth(mouthImages.l2);
                    case 2 -> characterCanvas.setMouth(mouthImages.l3);
                    case 3 -> characterCanvas.setMouth(mouthImages.l4);
                    case 4 -> characterCanvas.setMouth(mouthImages.l5);
                    case 5 -> characterCanvas.setMouth(mouthImages.l6);
                    case 6 -> characterCanvas.setMouth(mouthImages.l7);
                    case 7 -> characterCanvas.setMouth(mouthImages.l8);
                    default -> characterCanvas.setMouth(mouthImages.l1);
                }
            },
            eyeIndex -> {
                switch (eyeIndex) {
                    case 0 -> characterCanvas.setEye(eyeImages.e1_c, eyeImages.e1_l);
                    case 1 -> characterCanvas.setEye(eyeImages.e2_c, eyeImages.e2_l);
                    case 2 -> characterCanvas.setEye(eyeImages.e3_c, eyeImages.e3_l);
                    case 3 -> characterCanvas.setEye(eyeImages.e4_c, eyeImages.e4_l);
                    case 4 -> characterCanvas.setEye(eyeImages.e5_c, eyeImages.e5_l);
                    case 5 -> characterCanvas.setEye(eyeImages.e6_c, eyeImages.e6_l);
                    case 6 -> characterCanvas.setEye(eyeImages.e7_c, eyeImages.e7_l);
                    case 7 ->characterCanvas.setEye(eyeImages.e8_c, eyeImages.e8_l);
                    default -> characterCanvas.setEye(eyeImages.e1_c, eyeImages.e1_l);
                }
            }
        ));

        // faceButton.addActionListener(e ->
        //         customPanel.showCategory("Face", "Face Type", 6, color -> {}, index -> {})
        // );

        // hairButton.addActionListener(e ->
        //         customPanel.showCategory("Hair", "Hair Style", 8, color -> {}, index -> {})
        // );

        // topButton.addActionListener(e ->
        //         customPanel.showCategory("Top", "Top Type", 8, color -> {}, index -> {})
        // );

        // bottomButton.addActionListener(e ->
        //         customPanel.showCategory("Bottom", "Bottom Type", 8, color -> {}, index -> {})
        // );

        // fullDressButton.addActionListener(e ->
        //         customPanel.showCategory("Outfit", "Outfit Type", 8, color -> {}, index -> {})
        // );

        // shoeButton.addActionListener(e ->
        //         customPanel.showCategory("Shoes", "Shoe Type", 6, color -> {}, index -> {})
        // );

        // accessoryButton.addActionListener(e ->
        //         customPanel.showCategory("Accessories", "Accessory Type", 10, color -> {}, index -> {})
        // );
    }

    private JButton createButton(String tooltip) {
        JButton button = b.createControlButton(
                "",
                tooltip,
                REG,
                HOVER,
                PRESS,
                40
        );

        button.setAlignmentX(Component.RIGHT_ALIGNMENT);
        return button;
    }

    private void addButtons() {
        menuBar.add(Box.createVerticalGlue());

        addRightAligned(bodyButton);
        menuBar.add(Box.createVerticalGlue());

        addRightAligned(faceButton);
        menuBar.add(Box.createVerticalGlue());

        addRightAligned(hairButton);
        menuBar.add(Box.createVerticalGlue());

        addRightAligned(topButton);
        menuBar.add(Box.createVerticalGlue());

        addRightAligned(bottomButton);
        menuBar.add(Box.createVerticalGlue());

        addRightAligned(fullDressButton);
        menuBar.add(Box.createVerticalGlue());

        addRightAligned(shoeButton);
        menuBar.add(Box.createVerticalGlue());

        addRightAligned(accessoryButton);
        menuBar.add(Box.createVerticalGlue());
    }

    private void addRightAligned(JButton button) {
        button.setAlignmentX(Component.RIGHT_ALIGNMENT);
        menuBar.add(button);
    }

    public void attachWindowMouseController(WindowMouseController mouseController) {
        menuBar.addMouseListener(mouseController);
        menuBar.addMouseMotionListener(mouseController);

        for (Component component : menuBar.getComponents()) {
            if (component instanceof JButton) {
                component.addMouseListener(mouseController);
                component.addMouseMotionListener(mouseController);
            }
        }
    }

    public void setCursorForAllComponents(Cursor cursor) {
        menuBar.setCursor(cursor);

        for (Component component : menuBar.getComponents()) {
            component.setCursor(cursor);
        }
    }

    public void resizeButtons(int size) {
        if (size == lastIconSize) {
            return;
        }

        lastIconSize = size;

        Dimension dim = new Dimension(size, size);

        for (JButton button : getButtons()) {
            button.setPreferredSize(dim);
            button.setMinimumSize(dim);
            button.setMaximumSize(dim);

            b.rescaleButtonIcons(button, REG, HOVER, PRESS, size);
        }

        menuBar.revalidate();
        menuBar.repaint();
    }

    private JButton[] getButtons() {
        return new JButton[] {
                bodyButton,
                faceButton,
                hairButton,
                topButton,
                bottomButton,
                fullDressButton,
                shoeButton,
                accessoryButton
        };
    }

    public JPanel getMenuBar() {
        return menuBar;
    }
}