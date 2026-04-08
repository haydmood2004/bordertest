package com.test.DisplayUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class BorderDisplay extends JPanel {
    private static final int TOP_BORDER_HEIGHT = 80;
    private static final int TOP_CORNER_BORDER_WIDTH = 80;
    private static final int TOOLBAR_Y_OFFSET = 5;
    private static final int TOOLBAR_X_OFFSET = 50;
    private static final int TOOLBAR_RIGHT_OVERHANG = 12;

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private BufferedImage bt, bb, bl, br, btl, btr, bbl, bbr;
    private int width = screenSize.width;
    private int height = screenSize.height;

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Graphics Practice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderDisplay canvas = new BorderDisplay();
        frame.setSize(720,480);
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));

        canvas.setBackground(new Color(0, 0, 0, 0));
        canvas.setSize(frame.getSize());
        canvas.setPreferredSize(frame.getSize());

        // edges
        canvas.bt = ImageIO.read(new File("src/main/resources/edges/Border CG T.png"));
        canvas.bb = ImageIO.read(new File("src/main/resources/edges/Border CG B.png"));
        canvas.bl = ImageIO.read(new File("src/main/resources/edges/Border CG L.png"));
        canvas.br = ImageIO.read(new File("src/main/resources/edges/Border CG R.png"));
        // corners
        canvas.btl = ImageIO.read(new File("src/main/resources/corner/Border CG TL - Copy.png"));
        canvas.btr = ImageIO.read(new File("src/main/resources/corner/Border CG TR.png"));
        canvas.bbl = ImageIO.read(new File("src/main/resources/corner/Border CG BL.png"));
        canvas.bbr = ImageIO.read(new File("src/main/resources/corner/Border CG BR.png"));

        JLayeredPane layered = new JLayeredPane();
        layered.setLayout(null);
        layered.setOpaque(false);
        layered.setPreferredSize(frame.getSize());
        frame.setContentPane(layered);
        layered.add(canvas, JLayeredPane.DEFAULT_LAYER);


        ToolBar toolBarWrapper = new ToolBar();
        layered.add(toolBarWrapper.getToolBar(), JLayeredPane.PALETTE_LAYER);

        updateLayout(frame, layered, canvas, toolBarWrapper);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateLayout(frame, layered, canvas, toolBarWrapper);
            }
        });

        frame.addWindowStateListener(e -> {
            frame.setBackground(new Color(0, 0, 0, 0));
            updateLayout(frame, layered, canvas, toolBarWrapper);
        });

        frame.pack();
        frame.setVisible(true);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (bt != null && btl != null && btr != null && bb != null && bl != null && br != null && bbl != null && bbr != null) {
            int leftCornerWidth = TOP_CORNER_BORDER_WIDTH;
            int rightCornerWidth = TOP_CORNER_BORDER_WIDTH;
            int topBottomHeight = TOP_BORDER_HEIGHT;
            int sideHeight = Math.max(1, getHeight() - (topBottomHeight * 2));
            int availableWidth = Math.max(1, getWidth() - leftCornerWidth - rightCornerWidth);

            int rightCornerX = getWidth() - rightCornerWidth;
            int bottomY = getHeight() - topBottomHeight;

            g2d.drawImage(scale(btl, leftCornerWidth, topBottomHeight), 0, 0, this);
            g2d.drawImage(scale(btr, rightCornerWidth, topBottomHeight), rightCornerX, 0, this);
            g2d.drawImage(scale(bbl, leftCornerWidth, topBottomHeight), 0, bottomY, this);
            g2d.drawImage(scale(bbr, rightCornerWidth, topBottomHeight), rightCornerX, bottomY, this);

            int drawX = leftCornerWidth;
            g2d.drawImage(scale(bt, availableWidth, topBottomHeight), drawX, 0, this);
            g2d.drawImage(scale(bb, availableWidth, topBottomHeight), drawX, bottomY, this);
            g2d.drawImage(scale(bl, leftCornerWidth, sideHeight), 0, topBottomHeight, this);
            g2d.drawImage(scale(br, rightCornerWidth, sideHeight), getWidth() - rightCornerWidth, topBottomHeight, this);
            return;
        }
    }

    private static void updateLayout(JFrame frame, JLayeredPane layered, BorderDisplay canvas, ToolBar toolBarWrapper) {
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        layered.setBounds(0, 0, frameWidth, frameHeight);
        canvas.setBounds(0, 0, frameWidth, frameHeight);
        toolBarWrapper.getToolBar().setBounds(
            TOP_CORNER_BORDER_WIDTH + TOOLBAR_X_OFFSET,
            TOOLBAR_Y_OFFSET,
            Math.max(1, frameWidth - (TOP_CORNER_BORDER_WIDTH * 2) + TOOLBAR_RIGHT_OVERHANG),
            TOP_BORDER_HEIGHT
        );

        canvas.revalidate();
        canvas.repaint();
    }


    private static BufferedImage scale(BufferedImage src, int width, int height) {
    BufferedImage dst = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    Graphics2D g2 = dst.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.drawImage(src, 0, 0, width, height, null);
    g2.dispose();

    return dst;
    }
}