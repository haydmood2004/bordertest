package com.test.DisplayUI.Panels;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.Hashtable;

import javax.swing.*;

import com.test.DisplayUI.ColorController;
import com.test.DisplayUI.UITheme;
import com.test.DisplayUI.Sliders.*;

public class CustomPanel {
    private static final int slider_count = 4;

    private final RoundedPanel panel = new RoundedPanel();

    private final HueSlider hueSlider = new HueSlider();
    private final SaturationSlider saturationSlider = new SaturationSlider();
    private final BrightnessSlider brightnessSlider = new BrightnessSlider();
    private final JSlider extraSlider = new JSlider();
    private final ColorController colorController = new ColorController();
    private final JLabel title = new JLabel("Customize");
    private final JLabel hueLabel = new JLabel("Hue");
    private final JLabel satLabel = new JLabel("Saturation");
    private final JLabel brightLabel = new JLabel("Brightness");
    private final JLabel extraLabel = new JLabel("Extra");
    private final JLabel previewLabel = new JLabel("Preview");
    private final RoundedPreview previewBox = new RoundedPreview();
    private Color selectedColor = Color.BLUE;
    private Consumer<Color> colorListener;
    private IntConsumer optionListener;

    public CustomPanel() {
        setup();
        connect();

        showCategory("Body", "Body Type", 7, color -> {}, index -> {});
    }

    private void setup() {
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setPanelColor(UITheme.panel_bg);

        style(title, 18);
        style(hueLabel, 12);
        style(satLabel, 12);
        style(brightLabel, 12);
        style(extraLabel, 12);
        style(previewLabel, 12);

        extraSlider.setOpaque(false);
        extraSlider.setPaintTicks(false);
        extraSlider.setPaintLabels(false);
        extraSlider.setSnapToTicks(true);
        extraSlider.setMajorTickSpacing(1);
        extraSlider.setMinorTickSpacing(1);
        extraSlider.setUI(new CustomSliderUI(
                extraSlider,
                "/slider/regular slider track.png",
                "/slider/thumb slider.png"
        ));

        extraSlider.setPaintLabels(true);

        Hashtable<Integer, JLabel> labels = new Hashtable<>();

        for (int i = 0; i < 7; i++) {
            JLabel label = new JLabel(String.valueOf(i + 1));
            label.setForeground(Color.WHITE);
            label.setFont(new Font("OCR A Extended", Font.BOLD, 14));
            labels.put(i, label);
        }

        extraSlider.setLabelTable(labels);

        panel.add(title);

        panel.add(hueLabel);
        panel.add(satLabel);
        panel.add(brightLabel);
        panel.add(extraLabel);

        panel.add(hueSlider);
        panel.add(saturationSlider);
        panel.add(brightnessSlider);
        panel.add(extraSlider);

        panel.add(previewLabel);
        panel.add(previewBox);
    }

    private void connect() {
        hueSlider.addChangeListener(e -> {
            updatePreviewOnly();

            if (!hueSlider.getValueIsAdjusting()) {
                applyCurrentColorToController();
            }
        });

        saturationSlider.addChangeListener(e -> {
            updatePreviewOnly();

            if (!saturationSlider.getValueIsAdjusting()) {
                applyCurrentColorToController();
            }
        });

        brightnessSlider.addChangeListener(e -> {
            updatePreviewOnly();

            if (!brightnessSlider.getValueIsAdjusting()) {
                applyCurrentColorToController();
            }
        });

        extraSlider.addChangeListener(e -> {
            if (!extraSlider.getValueIsAdjusting() && optionListener != null) {
                optionListener.accept(extraSlider.getValue());
            }
        });

        colorController.setColorListener(color -> {
            selectedColor = color;
            previewBox.setColor(selectedColor);

            if (colorListener != null) {
                colorListener.accept(selectedColor);
            }
        });
    }

    private void applyCurrentColorToController() {
        colorController.setHue(hueSlider.getValue() / 360f);
        colorController.setSaturation(saturationSlider.getValue() / 1000f);
        colorController.setBrightness(brightnessSlider.getValue() / 1000f);
    }

    private void updatePreviewOnly() {
        float hue = hueSlider.getValue() / 360f;
        float saturation = saturationSlider.getValue() / 1000f;
        float brightness = brightnessSlider.getValue() / 1000f;

        Color pureHueColor = Color.getHSBColor(hue, 1.0f, 1.0f);
        Color gray = new Color(128, 128, 128);
        Color saturatedColor = blend(gray, pureHueColor, saturation);

        Color previewColor;

        if (brightness < 0.5f) {
            float amount = brightness / 0.5f;
            previewColor = blend(Color.BLACK, saturatedColor, amount);
        } else {
            float amount = (brightness - 0.5f) / 0.5f;
            previewColor = blend(saturatedColor, Color.WHITE, amount);
        }

        previewBox.setColor(previewColor);
    }

    private Color blend(Color a, Color b, float amount) {
        float t = Math.max(0f, Math.min(1f, amount));

        int r = Math.round(a.getRed() + (b.getRed() - a.getRed()) * t);
        int g = Math.round(a.getGreen() + (b.getGreen() - a.getGreen()) * t);
        int bl = Math.round(a.getBlue() + (b.getBlue() - a.getBlue()) * t);

        return new Color(r, g, bl);
    }

    public void showCategory(
            String titleText,
            String extraText,
            int optionCount,
            Consumer<Color> colorListener,
            IntConsumer optionListener
    ) {
        title.setText(titleText);
        extraLabel.setText(extraText);

        this.colorListener = colorListener;
        this.optionListener = optionListener;

        extraSlider.setMinimum(0);
        extraSlider.setMaximum(optionCount - 1);
        extraSlider.setValue(0);

        if (optionListener != null) {
            optionListener.accept(extraSlider.getValue());
        }

        updatePreviewOnly();
        applyCurrentColorToController();
        panel.revalidate();
        panel.repaint();
    }

    public void updateLayout() {
        int w = panel.getWidth();
        int h = panel.getHeight();

        if (w <= 0 || h <= 0) {
            return;
        }

        int padding = clamp(w / 8, 20, 40);
        int contentWidth = Math.max(1, w - padding * 2);

        int titleHeight = clamp(h / 10, 24, 34);
        int labelHeight = clamp(h / 34, 14, 20);

        int previewBoxHeight = clamp(h / 12, 28, 46);
        int previewGap = 6;

        title.setBounds(padding, padding, contentWidth, titleHeight);

        int y = padding + titleHeight + 10;

        int reservedPreviewSpace =
                labelHeight + 4 + previewBoxHeight + previewGap + padding;

        int available = h - y - reservedPreviewSpace;

        int rowHeight = clamp(available / slider_count, 46, 64);
        int sliderHeight = clamp((int) Math.round(rowHeight * 0.65), 28, 42);

        JLabel[] labels = {
                hueLabel,
                satLabel,
                brightLabel,
                extraLabel
        };

        JComponent[] sliders = {
                hueSlider,
                saturationSlider,
                brightnessSlider,
                extraSlider
        };

        for (int i = 0; i < slider_count; i++) {
            labels[i].setBounds(padding, y, contentWidth, labelHeight);

            sliders[i].setBounds(
                    padding,
                    y + labelHeight + 4,
                    contentWidth,
                    sliderHeight
            );

            y += rowHeight;
        }

        y += previewGap;

        previewLabel.setBounds(padding, y, contentWidth, labelHeight);
        y += labelHeight + 4;

        previewBox.setBounds(padding, y, contentWidth, previewBoxHeight);

        int thumb = clamp(w / 16, 12, 18);
        int track = clamp(w / 40, 5, 9);

        resize(hueSlider, track, thumb);
        resize(saturationSlider, track, thumb);
        resize(brightnessSlider, track, thumb);
        resize(extraSlider, track, thumb);
    }

    private void resize(JSlider slider, int track, int thumb) {
        if (slider.getUI() instanceof CustomSliderUI ui) {
            ui.setSizes(track, thumb);
        }
    }

    private void style(JLabel label, int size) {
        label.setForeground(Color.WHITE);
        label.setFont(new Font("OCR A Extended", Font.BOLD, size));
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public JPanel getPanel() {
        return panel;
    }

    public ColorController getColorController() {
        return colorController;
    }

    private class RoundedPanel extends JPanel {
        private Color bg = UITheme.panel_bg;

        public void setPanelColor(Color color) {
            bg = color;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

            g2.setColor(new Color(255, 255, 255, 80));
            g2.setStroke(new BasicStroke(2f));
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 25, 25);

            g2.dispose();
        }
    }

    private class RoundedPreview extends JPanel {
        private Color color = selectedColor;

        public RoundedPreview() {
            setOpaque(false);
        }

        public void setColor(Color color) {
            this.color = color;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(new Color(255, 255, 255, 110));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);

            int inset = 6;

            g2.setColor(color);
            g2.fillRoundRect(
                    inset,
                    inset,
                    getWidth() - inset * 2,
                    getHeight() - inset * 2,
                    14,
                    14
            );

            g2.dispose();
        }
    }
}