package com.test.DisplayUI.Sliders;

import javax.swing.JSlider;

import com.test.DisplayUI.ColorController;

public class HueSlider extends JSlider {

    public HueSlider() {
        super(0, 360, 0);

        setUI(new CustomSliderUI(
                this,
                "/slider/hue slider track.png",
                "/slider/thumb slider.png"
        ));
    }

    public void connectTo(ColorController controller) {
        addChangeListener(e -> {
        if (!getValueIsAdjusting()) {
            controller.setHue(getValue() / 360f);
        }
    });
    }
}