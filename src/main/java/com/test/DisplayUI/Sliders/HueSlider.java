package com.test.DisplayUI.Sliders;

import javax.swing.JSlider;

import com.test.DisplayUI.ColorController;

public class HueSlider extends JSlider {

    public HueSlider() {
        super(0, 3600, 0);

        setUI(new CustomSliderUI(
                this,
                "/slider/hue slider track.png",
                "/slider/thumb slider.png"
        ));
    }

    public void connectTo(ColorController controller) {
        addChangeListener(e -> controller.setHue(getValue() / 3600f));
    }
}