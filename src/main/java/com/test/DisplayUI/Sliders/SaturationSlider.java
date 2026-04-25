package com.test.DisplayUI.Sliders;

import javax.swing.JSlider;

import com.test.DisplayUI.ColorController;

public class SaturationSlider extends JSlider {

    public SaturationSlider() {
        super(0, 1000, 1000);
        setupSaturationSlider();
    }

    private void setupSaturationSlider() {
        setUI(new CustomSliderUI(
                this,
                "/slider/regular slider track.png",
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
            float saturation = getValue() / 1000f;
            controller.setSaturation(saturation);
        });
    }

}