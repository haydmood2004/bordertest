package com.test.DisplayUI.Panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.test.DisplayUI.ColorController;
import com.test.DisplayUI.Sliders.BrightnessSlider;
import com.test.DisplayUI.Sliders.HueSlider;
import com.test.DisplayUI.Sliders.SaturationSlider;

public class CustomPanel {
    private JPanel panel = new JPanel();

    private HueSlider hueSlider = new HueSlider();
    private SaturationSlider saturationSlider = new SaturationSlider();
    private ColorController colorController = new ColorController();
    private BrightnessSlider brightnessSlider = new BrightnessSlider();
    private Font labelFont = new Font("OCR A Extended", Font.BOLD, 13);
    private JLabel hueLabel = new JLabel("H");
    private JLabel satLabel = new JLabel("S");
    private JLabel brightLabel = new JLabel("B");
    private JLabel title = new JLabel("Custom Panel");


    public CustomPanel() {
        setupPanel();
    }

    public void updateLayout() {

        int w = panel.getWidth();
        int h = panel.getHeight();

        int labelX = (int)(w * 0.05);
        int labelWidth = (int)(w * 0.20);
        int sliderX = labelX + 20;

        int sliderWidth = w - sliderX - 20;
        int sliderHeight = Math.max(30, (int)(h * 0.08));

        int hueY = (int)(h * 0.20)-(title.getHeight()-10);
        int satY = (int)(h * 0.26)-(title.getHeight()-10);
        int brightY = (int)(h * 0.32)-(title.getHeight()-10);
        int titleY = 40;

        int labelHeight = Math.max(18, (int)(h * 0.05));
        int titleHeight = Math.max(30, (int)(h * 0.08));

        hueSlider.setBounds(sliderX, hueY, sliderWidth, sliderHeight);
        saturationSlider.setBounds(sliderX, satY, sliderWidth, sliderHeight);
        brightnessSlider.setBounds(sliderX, brightY, sliderWidth, sliderHeight);

        Font scaledFont = labelFont.deriveFont(scaleFont(h, 0.025f, 12f, 22f));
        Font titleFont = labelFont.deriveFont(Font.BOLD, scaleFont(h, 0.04f, 18f, 36f));
        hueLabel.setFont(scaledFont);
        satLabel.setFont(scaledFont);
        brightLabel.setFont(scaledFont);
        title.setFont(titleFont);

        title.setBounds(0, titleY, w, titleHeight);
        hueLabel.setBounds(labelX, hueY + (sliderHeight - labelHeight) / 2, labelWidth, labelHeight);
        satLabel.setBounds(labelX, satY + (sliderHeight - labelHeight) / 2, labelWidth, labelHeight);
        brightLabel.setBounds(labelX, brightY + (sliderHeight - labelHeight) / 2, labelWidth, labelHeight);

        panel.revalidate();
        panel.repaint();
    }

    private float scaleFont(int h, float percent, float min, float max) {
        float size = h * percent;
        return Math.max(min, Math.min(size, max));
    }

    private void styleLabel(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setOpaque(false);
        label.setFont(labelFont);
    }

    public void setupPanel() {
        panel.setLayout(null);
        panel.setBackground(new Color(250, 216, 229));
        panel.setOpaque(true);

        styleLabel(hueLabel);
        styleLabel(satLabel);
        styleLabel(brightLabel);
        styleLabel(title);
        title.setHorizontalAlignment(JLabel.CENTER);

        panel.add(hueLabel);
        panel.add(satLabel);
        panel.add(brightLabel);
        panel.add(title);

        panel.add(hueSlider);
        panel.add(saturationSlider);
        panel.add(brightnessSlider);

        hueSlider.connectTo(colorController);
        saturationSlider.connectTo(colorController);
        brightnessSlider.connectTo(colorController);

        colorController.setColorListener(color -> {
            panel.setBackground(color);
        });

        colorController.setHue(hueSlider.getValue() / 3600f);
        colorController.setSaturation(saturationSlider.getValue() / 1000f);
        colorController.setBrightness(brightnessSlider.getValue() / 1000f);
    }

    public JPanel getPanel() {
        return panel;
    }
}