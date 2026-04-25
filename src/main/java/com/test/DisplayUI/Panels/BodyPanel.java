package com.test.DisplayUI.Panels;

import java.awt.Dimension;

public class BodyPanel {
    private CustomPanel bodyPanel = new CustomPanel();
    public BodyPanel() {
        setupBodyPanel();
    }

    public void setupBodyPanel() {
        bodyPanel.getPanel().getLayout();
        bodyPanel.getPanel().getBounds();
        bodyPanel.getPanel().getPreferredSize();
    }

    public CustomPanel getBodyPanel() {
        return bodyPanel;
    }
}
