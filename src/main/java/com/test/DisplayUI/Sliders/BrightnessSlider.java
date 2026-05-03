package com.test.DisplayUI.Sliders;

import javax.swing.JSlider;

import com.test.DisplayUI.ColorController;

public class BrightnessSlider extends JSlider {

    public BrightnessSlider() {
        super(0, 1000, 1000);

        setUI(new CustomSliderUI(
                this,
                "/slider/brightness slider track.png",
                "/slider/thumb slider.png"
        ));
    }

    public void connectTo(ColorController controller) {
        addChangeListener(e -> {
        if (!getValueIsAdjusting()) {
            controller.setBrightness(getValue() / 1000f);
        }
    });
    }
}