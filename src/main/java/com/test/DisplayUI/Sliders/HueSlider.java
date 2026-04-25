package com.test.DisplayUI.Sliders;

import javax.swing.JSlider;

import com.test.DisplayUI.ColorController;

public class HueSlider extends JSlider {

    public HueSlider() {
        super(0, 3600, 0); 
        setupHueSlider();
    }

    private void setupHueSlider() {
        setUI(new CustomSliderUI(
                this,
                "/slider/hue slider track.png",
                "/slider/thumb slider.png",
                14,
                14,
                20
        ));

        setOpaque(false);
        setFocusable(false);
        setBorder(null);
    }

    public void resizeUI(int trackHeight, int thumbW, int thumbH) {
        CustomSliderUI ui = (CustomSliderUI) getUI();
        ui.setTrackHeight(trackHeight);
        ui.setThumbSize(thumbW, thumbH);
    }

    public void connectTo(ColorController controller) {
        addChangeListener(e -> {
            float hue = getValue() / 3600f;
            controller.setHue(hue);
        });
    }
}