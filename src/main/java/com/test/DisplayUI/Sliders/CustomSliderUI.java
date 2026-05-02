package com.test.DisplayUI.Sliders;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class CustomSliderUI extends BasicSliderUI {

    private Image trackImage;
    private Image thumbImage;

    private int trackHeight = 12;
    private int thumbSize = 18;

    private boolean hovered = false;
    private boolean pressed = false;

    public CustomSliderUI(
            JSlider slider,
            String trackPath,
            String thumbPath
    ) {
        super(slider);

        this.trackImage = load(trackPath);
        this.thumbImage = load(thumbPath);

        slider.setOpaque(false);
        slider.setFocusable(false);
        slider.setBorder(null);

        installMouseTracking(slider);
    }

    private void installMouseTracking(JSlider targetSlider) {
    targetSlider.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            pressed = true;
            targetSlider.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            pressed = false;
            targetSlider.repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
                hovered = true;
                targetSlider.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
                hovered = false;
                targetSlider.repaint();
        }
        });
    }

    private Image load(String path) {
        if (path == null) return null;
        URL url = getClass().getResource(path);
        return url == null ? null : new ImageIcon(url).getImage();
    }

    public void setSizes(int trackHeight, int thumbSize) {
        this.trackHeight = trackHeight;
        this.thumbSize = thumbSize;
        slider.repaint();
    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(thumbSize, thumbSize);
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int y = trackRect.y + trackRect.height / 2 - trackHeight / 2;

        // fallback modern track
        g2.setColor(new Color(255,255,255,120));
        g2.fillRoundRect(trackRect.x, y, trackRect.width, trackHeight, trackHeight, trackHeight);

        if (trackImage != null) {
            g2.drawImage(trackImage, trackRect.x, y, trackRect.width, trackHeight, slider);
        }

        g2.dispose();
    }

    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        int size = thumbSize + (pressed ? 2 : hovered ? 1 : 0);

        int x = thumbRect.x + (thumbRect.width - size) / 2;
        int y = thumbRect.y + (thumbRect.height - size) / 2;

        if (thumbImage != null) {
            g2.drawImage(thumbImage, x, y, size, size, slider);
        } else {
            g2.setColor(Color.WHITE);
            g2.fillOval(x, y, size, size);
        }

        g2.dispose();
    }

    @Override
    public void setThumbLocation(int x, int y) {
        super.setThumbLocation(x, y);
        slider.repaint();
    }
}