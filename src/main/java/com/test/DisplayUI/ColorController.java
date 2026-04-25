package com.test.DisplayUI;

import java.awt.Color;
import java.util.function.Consumer;

public class ColorController {

    private float hue = 0.0f;       
    private float saturation = 1.0f;  
    private float brightness = 0.5f;   

    private Consumer<Color> listener;

    public void setHue(float hue) {
        this.hue = hue;
        updateColor();
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
        updateColor();
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
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
        amount = Math.max(0, Math.min(1, amount));

        int r = (int)(a.getRed()   + (b.getRed()   - a.getRed())   * amount);
        int g = (int)(a.getGreen() + (b.getGreen() - a.getGreen()) * amount);
        int bl = (int)(a.getBlue() + (b.getBlue()  - a.getBlue())  * amount);

        return new Color(r, g, bl);
    }
}