package com.test.DisplayUI;

import java.awt.Color;
import java.awt.Font;

public final class UITheme {
    private UITheme() {}

    public static final Color window_bg = new Color(216, 240, 241);
    public static final Color menu_bg = new Color(245, 175, 201);
    public static final Color panel_bg = new Color(161, 30, 83);
    public static final Color panel_accent = new Color(255, 255, 255, 180);
    public static final Color text_light = Color.WHITE;
    public static final Color text_dark = new Color(90, 65, 80);
    public static final Font display_font = new Font("OCR A Extended", Font.BOLD, 13);

    public static final int space_xs = 4;
    public static final int space_sm = 8;
    public static final int space_md = 12;
    public static final int space_lg = 16;
    public static final int space_xl = 24;
    public static final int space_xxl = 32;

    public static final int border_size = 80;
    public static final int corner_radius = 28;
    public static final int frame_radius = 50;
}

