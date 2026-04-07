package com.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GraphicsSandbox extends Canvas{
    private static final int TOP_BORDER_HEIGHT = 80;
    private static final int TOP_CORNER_BORDER_WIDTH = 80;

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private BufferedImage bt, bb, bl, br, btl, btr, bbl, bbr;
    private int width = screenSize.width;
    private int height = screenSize.height;

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Graphics Practice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsSandbox canvas = new GraphicsSandbox();
        frame.setSize(canvas.width, canvas.height);
        frame.setUndecorated(true);
        frame.setBackground(new Color(0,0,0));

        //canvas.setBackground(new Color(0,0,0,0));
        canvas.setSize(canvas.width, canvas.height);
        canvas.setPreferredSize(new Dimension(canvas.width, canvas.height));

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

        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);

    }

    // @Override public void paintComponent(Graphics g) {
    //     super.paintComponent(g);
    //     int actual_width = this.getWidth();
    //     int actual_height = this.getHeight();

    //     int _x = actual_width - corners_width;
    //     int _y = actual_height - corners_height;

    // }

    // The paint method is called whenever the program senses that it's time to redraw
    // what's on the Canvas object.
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (bt != null && btl != null && btr != null && bb != null && bl != null && br != null && bbl != null && bbr != null) {
            int leftCornerWidth = TOP_CORNER_BORDER_WIDTH;
            int rightCornerWidth = TOP_CORNER_BORDER_WIDTH;
            // Calculate the X coordinate for the right corner
            int rightCornerX = getWidth() - rightCornerWidth;
            int leftBottomY = getHeight();
            int rightBottomY = getHeight();

            g2d.drawImage(scale(btl,TOP_CORNER_BORDER_WIDTH,TOP_BORDER_HEIGHT), 0, 0, this);
            g2d.drawImage(scale(btr,TOP_CORNER_BORDER_WIDTH,TOP_BORDER_HEIGHT), rightCornerX, 0, this);
            g2d.drawImage(scale(bbl,TOP_CORNER_BORDER_WIDTH,TOP_BORDER_HEIGHT), 0, leftBottomY - TOP_BORDER_HEIGHT, this);
            g2d.drawImage(scale(bbr,TOP_CORNER_BORDER_WIDTH,TOP_BORDER_HEIGHT), rightCornerX, rightBottomY - TOP_BORDER_HEIGHT, this);

            int availableWidth = getWidth() - leftCornerWidth - rightCornerWidth;
            double widthScale = (double) availableWidth / bt.getWidth();
            double thinScale = (double) TOP_BORDER_HEIGHT / bt.getHeight();
            double scale = Math.min(widthScale, thinScale);

            int drawWidth = availableWidth;
            int drawHeight = Math.max(1, (int) Math.round(bt.getHeight() * scale));
            int drawX = 80;

            g2d.drawImage(scale(bt, drawWidth, drawHeight), drawX, 0, this);
            g2d.drawImage(scale(bb, drawWidth, drawHeight), drawX, getHeight() - drawHeight, this);
            g2d.drawImage(scale(bl, drawHeight, getHeight() - TOP_BORDER_HEIGHT * 2), 0, TOP_BORDER_HEIGHT, this);
            g2d.drawImage(scale(br, drawHeight, getHeight() - TOP_BORDER_HEIGHT * 2), getWidth() - drawHeight, TOP_BORDER_HEIGHT, this);
            return;
        }

        
        

        g2d.drawString("Hello, Graphics! 🙂", 150, 200);
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