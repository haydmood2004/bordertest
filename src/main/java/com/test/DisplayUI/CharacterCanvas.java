package com.test.DisplayUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class CharacterCanvas extends JPanel {
    private BufferedImage bodyColorImage;
    private BufferedImage bodyLineImage;

    private BufferedImage faceColorImage;
    private BufferedImage faceLineImage;

    private BufferedImage scoleraImage;

    private BufferedImage irisColorImage;
    private BufferedImage irisLineImage;

    private BufferedImage eyeColorImage;
    private BufferedImage eyeLineImage;

    private BufferedImage noseImage;
    private BufferedImage mouthImage;

    private BufferedImage earColorImage;
    private BufferedImage earLineImage;

    private BufferedImage tintedBodyImage;
    private BufferedImage tintedFaceImage;
    private BufferedImage tintedEarImage;
    private BufferedImage tintedLidImage;

    private Color skinTintColor = Color.WHITE;

    public CharacterCanvas() {
        setOpaque(false);
        setDoubleBuffered(true);
    }

    public void setMaleBodyImages(BufferedImage colorImage, BufferedImage lineImage) {
        bodyColorImage = colorImage;
        bodyLineImage = lineImage;
        rebuildTintedSkin();
        repaint();
    }

    public void setFace(BufferedImage colorImage, BufferedImage lineImage) {
        faceColorImage = colorImage;
        faceLineImage = lineImage;
        tintedFaceImage = tintImage(faceColorImage, skinTintColor);
        repaint();
    }

    public void setEar(BufferedImage colorImage, BufferedImage lineImage) {
        earColorImage = colorImage;
        earLineImage = lineImage;
        tintedEarImage = tintImage(earColorImage, skinTintColor);
        repaint();
    }

    public void setScolera(BufferedImage image) {
        scoleraImage = image;
        repaint();
    }

    public void setIris(BufferedImage colorImage, BufferedImage lineImage) {
        irisColorImage = colorImage;
        irisLineImage = lineImage;
        repaint();
    }

    public void setEye(BufferedImage colorImage, BufferedImage lineImage) {
        eyeColorImage = colorImage;
        eyeLineImage = lineImage;
        tintedLidImage = tintImage(eyeColorImage, skinTintColor);
        repaint();
    }

    public void setNose(BufferedImage image) {
        noseImage = image;
        repaint();
    }

    public void setMouth(BufferedImage image) {
        mouthImage = image;
        repaint();
    }

    public void setSkinTintColor(Color color) {
        if (color == null || color.equals(skinTintColor)) {
            return;
        }

        skinTintColor = color;

        rebuildTintedSkin();
        repaint();
    }

    private void rebuildTintedSkin() {
        tintedBodyImage = tintImage(bodyColorImage, skinTintColor);
        tintedFaceImage = tintImage(faceColorImage, skinTintColor);
        tintedEarImage = tintImage(earColorImage, skinTintColor);
        tintedLidImage = tintImage(eyeColorImage, skinTintColor);
    }

    private BufferedImage tintImage(BufferedImage source, Color tint) {
        if (source == null) {
            return null;
        }

        if (tint == null) {
            tint = Color.WHITE;
        }

        BufferedImage output = new BufferedImage(
                source.getWidth(),
                source.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = 0; x < source.getWidth(); x++) {
                int rgba = source.getRGB(x, y);

                int a = (rgba >> 24) & 0xff;
                int r = (rgba >> 16) & 0xff;
                int g = (rgba >> 8) & 0xff;
                int b = rgba & 0xff;

                int tr = (int) (r * (tint.getRed() / 255.0));
                int tg = (int) (g * (tint.getGreen() / 255.0));
                int tb = (int) (b * (tint.getBlue() / 255.0));

                int newColor = (a << 24) | (tr << 16) | (tg << 8) | tb;
                output.setRGB(x, y, newColor);
            }
        }

        return output;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage reference = getReferenceImage();

        Rectangle drawArea = getCharacterDrawArea(reference);

        if (reference == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        drawLayer(g2, tintedBodyImage, bodyLineImage, drawArea);

        Rectangle earArea = placePart(drawArea, 276, 253, 0.368, 0.17, 1.15);
        Rectangle faceArea = placePart(drawArea, 384, 357, 0.433, 0.19, 1.15);
        Rectangle eyeArea = placePart(drawArea, 409, 213, 0.435, 0.157, 1.15);
        Rectangle irisArea = placePart(drawArea, 409, 213, 0.435, 0.157, 1.15);
        Rectangle noseArea = placePart(drawArea, 301, 296, 0.4425, 0.164, 1.15);
        Rectangle mouthArea = placePart(drawArea, 301, 207, 0.44, 0.188, 1.15);

        
        drawLayer(g2, tintedFaceImage, faceLineImage, faceArea);

        drawImage(g2, scoleraImage, eyeArea);
        drawLayer(g2, irisColorImage, irisLineImage, irisArea);
        drawLayer(g2, tintedLidImage, eyeLineImage, eyeArea);

        drawImage(g2, noseImage, noseArea);
        drawImage(g2, mouthImage, mouthArea);
        drawLayer(g2, tintedEarImage, earLineImage, earArea);
        g2.dispose();
    }

    private BufferedImage getReferenceImage() {
        if (bodyColorImage != null)
            return bodyColorImage;
        if (bodyLineImage != null)
            return bodyLineImage;
        if (faceColorImage != null)
            return faceColorImage;
        if (faceLineImage != null)
            return faceLineImage;
        if (scoleraImage != null)
            return scoleraImage;
        if (irisColorImage != null)
            return irisColorImage;
        if (irisLineImage != null)
            return irisLineImage;
        if (eyeColorImage != null)
            return eyeColorImage;
        if (eyeLineImage != null)
            return eyeLineImage;
        if (noseImage != null)
            return noseImage;
        if (mouthImage != null)
            return mouthImage;
        if (earColorImage != null)
            return earColorImage;
        if (earLineImage != null)
            return earLineImage;

        return null;
    }

    private Rectangle placePart(Rectangle bodyArea, int partW, int partH, double centerX,
            double centerY, double sizeMultiplier) {
        double scaleX = bodyArea.width / 3524.0;
        double scaleY = bodyArea.height / 4791.0;

        int w = (int) (partW * scaleX);
        int h = (int) (partH * scaleY);

        int x = bodyArea.x + (int) (centerX * bodyArea.width) - w / 2;
        int y = bodyArea.y + (int) (centerY * bodyArea.height) - h / 2;

        return new Rectangle(x, y, w, h);
    }

    private Rectangle getCharacterDrawArea(BufferedImage reference) {
        int imgW = reference.getWidth();
        int imgH = reference.getHeight();

        int maxW = getWidth() / 2;
        int maxH = getHeight() - 120;

        double scale = Math.min(
                ((double) maxW / imgW) * 1.15,
                ((double) maxH / imgH) * 1.15);

        int drawW = (int) (imgW * scale);
        int drawH = (int) (imgH * scale);

        int x = ((getWidth() - drawW) / 2) + (getWidth() / 4);
        int y = (getHeight() - drawH) / 2;

        return new Rectangle(x, y, drawW, drawH);
    }

    private void drawLayer(
            Graphics2D g2,
            BufferedImage colorImage,
            BufferedImage lineImage,
            Rectangle area) {
        drawImage(g2, colorImage, area);
        drawImage(g2, lineImage, area);
    }

    private void drawImage(Graphics2D g2, BufferedImage image, Rectangle area) {
        if (image == null) {
            return;
        }

        g2.drawImage(
                image,
                area.x,
                area.y,
                area.width,
                area.height,
                this);
    }
}