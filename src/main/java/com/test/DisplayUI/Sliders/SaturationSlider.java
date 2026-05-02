package com.test.DisplayUI.Sliders;

import javax.swing.JSlider;

import com.test.DisplayUI.ColorController;

public class SaturationSlider extends JSlider {

    public SaturationSlider() {
        super(0, 1000, 1000);

        setUI(new CustomSliderUI(
                this,
                "/slider/regular slider track.png",
                "/slider/thumb slider.png"
        ));
    }

    public void connectTo(ColorController controller) {
        addChangeListener(e -> controller.setSaturation(getValue() / 1000f));
    }
}