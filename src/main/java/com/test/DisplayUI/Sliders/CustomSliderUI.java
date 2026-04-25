package com.test.DisplayUI.Sliders;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.net.URL;

public class CustomSliderUI extends BasicSliderUI {

    private Image trackImage;
    private Image thumbImage;

    private int trackHeight;
    private Dimension thumbSize;

    public CustomSliderUI(
            JSlider slider,
            String trackImagePath,
            String thumbImagePath,
            int trackHeight,
            int thumbWidth,
            int thumbHeight
    ) {
        super(slider);

        this.trackImage = loadImage(trackImagePath);
        this.thumbImage = loadImage(thumbImagePath);
        this.trackHeight = trackHeight;
        this.thumbSize = new Dimension(thumbWidth, thumbHeight);

        slider.setOpaque(false);
        slider.setBorder(null);
        slider.setFocusable(false);
    }

    private Image loadImage(String path) {
        if (path == null || path.isBlank()) return null;

        URL url = getClass().getResource(path);

        if (url == null) {
            System.out.println("Missing slider image: " + path);
            return null;
        }

        return new ImageIcon(url).getImage();
    }

    public void setTrackHeight(int trackHeight) {
        this.trackHeight = trackHeight;
        slider.repaint();
    }

    public void setThumbSize(int width, int height) {
        this.thumbSize = new Dimension(width, height);
        slider.revalidate();
        slider.repaint();
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );

        int y = trackRect.y + (trackRect.height / 2) - (trackHeight / 2);

        if (trackImage != null) {
            g2.drawImage(
                    trackImage,
                    trackRect.x,
                    y,
                    trackRect.width,
                    trackHeight,
                    slider
            );
        }

        g2.dispose();
    }

    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        if (thumbImage != null) {
            g2.drawImage(
                    thumbImage,
                    thumbRect.x,
                    thumbRect.y,
                    thumbRect.width,
                    thumbRect.height,
                    slider
            );
        }

        g2.dispose();
    }

    @Override
    protected Dimension getThumbSize() {
        return thumbSize;
    }
}