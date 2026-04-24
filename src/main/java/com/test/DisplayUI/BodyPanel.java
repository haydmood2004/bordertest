package com.test.DisplayUI;

public class BodyPanel {
    private CustomPanel bodyPanel = new CustomPanel();
    public BodyPanel() {
        setupBodyPanel();
    }

    public void setupBodyPanel() {
        bodyPanel.getPanel().getBounds();
        bodyPanel.getPanel().getPreferredSize();
    }
}
