package com.test.DisplayUI;

import java.awt.Color;
import java.util.function.Consumer;

public class ColorController {
    private float hue = 0.0f;
    private float saturation = 1.0f;
    private float brightness = 0.5f;

    private Consumer<Color> listener;

    public void setHue(float hue) {
        this.hue = clamp01(hue);
        updateColor();
    }

    public void setSaturation(float saturation) {
        this.saturation = clamp01(saturation);
        updateColor();
    }

    public void setBrightness(float brightness) {
        this.brightness = clamp01(brightness);
        updateColor();
    }

    public void setColorListener(Consumer<Color> listener) {
        this.listener = listener;
        updateColor();
    }

    private void updateColor() {
        Color pureHueColor = Color.getHSBColor(hue, 1.0f, 1.0f);
        Color gray = new Color(128, 128, 128);
        Color saturatedColor = blend(gray, pureHueColor, saturation);

        Color finalColor;

        if (brightness < 0.5f) {
            float amount = brightness / 0.5f;
            finalColor = blend(Color.BLACK, saturatedColor, amount);
        } else {
            float amount = (brightness - 0.5f) / 0.5f;
            finalColor = blend(saturatedColor, Color.WHITE, amount);
        }

        if (listener != null) {
            listener.accept(finalColor);
        }
    }

    private Color blend(Color a, Color b, float amount) {
        float t = clamp01(amount);

        int r = interpolate(a.getRed(), b.getRed(), t);
        int g = interpolate(a.getGreen(), b.getGreen(), t);
        int bl = interpolate(a.getBlue(), b.getBlue(), t);

        return new Color(r, g, bl);
    }

    private int interpolate(int a, int b, float amount) {
        return Math.round(a + (b - a) * amount);
    }

    private float clamp01(float value) {
        return Math.max(0f, Math.min(1f, value));
    }
}
