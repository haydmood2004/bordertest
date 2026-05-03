package com.test.DisplayUI.Panels;

import java.awt.*;
import java.util.Hashtable;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

import javax.swing.*;

import com.test.DisplayUI.ColorController;
import com.test.DisplayUI.UITheme;
import com.test.DisplayUI.Sliders.*;

public class CustomPanel {
    private static final int SLIDER_COUNT = 9;

    private final RoundedPanel panel = new RoundedPanel();

    private final HueSlider hueSlider = new HueSlider();
    private final SaturationSlider saturationSlider = new SaturationSlider();
    private final BrightnessSlider brightnessSlider = new BrightnessSlider();

    private final JSlider bodySlider = createOptionSlider(7);
    private final JSlider faceSlider = createOptionSlider(11);
    private final JSlider earSlider = createOptionSlider(8);
    private final JSlider noseSlider = createOptionSlider(5);
    private final JSlider mouthSlider = createOptionSlider(8);
    private final JSlider eyeSlider = createOptionSlider(8);

    private final ColorController colorController = new ColorController();

    private final JLabel title = new JLabel("Body");

    private final JLabel hueLabel = new JLabel("Hue");
    private final JLabel satLabel = new JLabel("Saturation");
    private final JLabel brightLabel = new JLabel("Brightness");

    private final JLabel bodyLabel = new JLabel("Body Type");
    private final JLabel faceLabel = new JLabel("Face Type");
    private final JLabel earLabel = new JLabel("Ear Type");
    private final JLabel noseLabel = new JLabel("Nose Type");
    private final JLabel mouthLabel = new JLabel("Mouth Type");
    private final JLabel eyeLabel = new JLabel("Eye Type");

    private final JLabel previewLabel = new JLabel("Preview");
    private final RoundedPreview previewBox = new RoundedPreview();

    private Color selectedColor = Color.WHITE;

    private Consumer<Color> skinColorListener;

    private IntConsumer bodyListener;
    private IntConsumer faceListener;
    private IntConsumer earListener;
    private IntConsumer noseListener;
    private IntConsumer mouthListener;
    private IntConsumer eyeListener;

    public CustomPanel() {
        setup();
        connect();

        hueSlider.setValue(0);
        saturationSlider.setValue(0);
        brightnessSlider.setValue(1000);

        updatePreviewOnly();
        applyCurrentColorToController();
    }

    private void setup() {
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setPanelColor(UITheme.panel_bg);

        JLabel[] labels = {
                title,
                hueLabel,
                satLabel,
                brightLabel,
                bodyLabel,
                faceLabel,
                earLabel,
                noseLabel,
                mouthLabel,
                eyeLabel,
                previewLabel
        };

        for (JLabel label : labels) {
            style(label, label == title ? 18 : 11);
            panel.add(label);
        }

        JSlider[] sliders = getSliders();

        for (JSlider slider : sliders) {
            slider.setOpaque(false);
            panel.add(slider);
        }

        panel.add(previewBox);
    }

    private JSlider[] getSliders() {
        return new JSlider[] {
                hueSlider,
                saturationSlider,
                brightnessSlider,
                bodySlider,
                faceSlider,
                earSlider,
                noseSlider,
                mouthSlider,
                eyeSlider
        };
    }

    private JSlider createOptionSlider(int optionCount) {
        JSlider slider = new JSlider(0, optionCount - 1, 0);

        slider.setOpaque(false);
        slider.setFocusable(false);

        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.setMajorTickSpacing(1);
        slider.setMinorTickSpacing(1);

        slider.setUI(new CustomSliderUI(
                slider,
                "/slider/regular slider track.png",
                "/slider/thumb slider.png"
        ));

        setSliderLabels(slider, optionCount);

        return slider;
    }

    private void setSliderLabels(JSlider slider, int optionCount) {
        Hashtable<Integer, JLabel> labels = new Hashtable<>();

        for (int i = 0; i < optionCount; i++) {
            JLabel label = new JLabel(String.valueOf(i + 1));
            label.setForeground(Color.WHITE);
            label.setFont(new Font("OCR A Extended", Font.BOLD, 10));
            labels.put(i, label);
        }

        slider.setLabelTable(labels);
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

        bodySlider.addChangeListener(e -> fireOnRelease(bodySlider, bodyListener));
        faceSlider.addChangeListener(e -> fireOnRelease(faceSlider, faceListener));
        earSlider.addChangeListener(e -> fireOnRelease(earSlider, earListener));
        noseSlider.addChangeListener(e -> fireOnRelease(noseSlider, noseListener));
        mouthSlider.addChangeListener(e -> fireOnRelease(mouthSlider, mouthListener));
        eyeSlider.addChangeListener(e -> fireOnRelease(eyeSlider, eyeListener));

        colorController.setColorListener(color -> {
            selectedColor = color;
            previewBox.setColor(selectedColor);

            if (skinColorListener != null) {
                skinColorListener.accept(selectedColor);
            }
        });
    }

    private void fireOnRelease(JSlider slider, IntConsumer listener) {
        if (!slider.getValueIsAdjusting() && listener != null) {
            listener.accept(slider.getValue());
        }
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
            previewColor = blend(Color.BLACK, saturatedColor, brightness / 0.5f);
        } else {
            previewColor = blend(saturatedColor, Color.WHITE, (brightness - 0.5f) / 0.5f);
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

    public void showBodyCategory(
            Consumer<Color> skinColorListener,
            IntConsumer bodyListener,
            IntConsumer faceListener,
            IntConsumer earListener,
            IntConsumer noseListener,
            IntConsumer mouthListener,
            IntConsumer eyeListener
    ) {
        title.setText("Body");

        this.skinColorListener = skinColorListener;

        this.bodyListener = bodyListener;
        this.faceListener = faceListener;
        this.earListener = earListener;
        this.noseListener = noseListener;
        this.mouthListener = mouthListener;
        this.eyeListener = eyeListener;

        fireAllCurrentOptions();

        updatePreviewOnly();
        applyCurrentColorToController();

        panel.revalidate();
        panel.repaint();
    }

    private void fireAllCurrentOptions() {
        if (bodyListener != null) bodyListener.accept(bodySlider.getValue());
        if (faceListener != null) faceListener.accept(faceSlider.getValue());
        if (earListener != null) earListener.accept(earSlider.getValue());
        if (noseListener != null) noseListener.accept(noseSlider.getValue());
        if (mouthListener != null) mouthListener.accept(mouthSlider.getValue());
        if (eyeListener != null) eyeListener.accept(eyeSlider.getValue());
    }

    public void updateLayout() {
        int w = panel.getWidth();
        int h = panel.getHeight();

        if (w <= 0 || h <= 0) {
            return;
        }

        int padding = clamp(w / 10, 16, 30);
        int contentWidth = Math.max(1, w - padding * 2);

        int titleHeight = clamp(h / 13, 22, 32);
        int labelHeight = clamp(h / 45, 11, 16);

        int previewBoxHeight = clamp(h / 15, 24, 40);
        int previewGap = 5;

        title.setBounds(padding, padding, contentWidth, titleHeight);

        int y = padding + titleHeight + 6;

        int reservedPreviewSpace =
                labelHeight + 4 + previewBoxHeight + previewGap + padding;

        int available = h - y - reservedPreviewSpace;

        int rowHeight = clamp(available / SLIDER_COUNT, 30, 48);
        int sliderHeight = clamp((int) Math.round(rowHeight * 0.65), 22, 34);

        JLabel[] labels = {
                hueLabel,
                satLabel,
                brightLabel,
                bodyLabel,
                faceLabel,
                earLabel,
                noseLabel,
                mouthLabel,
                eyeLabel
        };

        JSlider[] sliders = getSliders();

        for (int i = 0; i < SLIDER_COUNT; i++) {
            labels[i].setBounds(padding, y, contentWidth, labelHeight);

            sliders[i].setBounds(
                    padding,
                    y + labelHeight + 2,
                    contentWidth,
                    sliderHeight
            );

            y += rowHeight;
        }

        y += previewGap;

        previewLabel.setBounds(padding, y, contentWidth, labelHeight);
        y += labelHeight + 4;

        previewBox.setBounds(padding, y, contentWidth, previewBoxHeight);

        int thumb = clamp(w / 18, 10, 16);
        int track = clamp(w / 45, 4, 8);

        for (JSlider slider : sliders) {
            resize(slider, track, thumb);
        }
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