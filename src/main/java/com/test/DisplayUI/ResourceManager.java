package com.test.DisplayUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ResourceManager {
    private static final String resourceBase = "src/main/resources";
    
    public static class BorderImages {
        public BufferedImage top, bottom, left, right;
        public BufferedImage topLeft, topRight, bottomLeft, bottomRight;
    }

    public static class CursorImages {
        public BufferedImage regular, click;
    }

    public static BorderImages loadBorderImages() throws IOException {
        BorderImages borders = new BorderImages();
        borders.top = ImageIO.read(new File(resourceBase + "/edges/Border CG T.png"));
        borders.bottom = ImageIO.read(new File(resourceBase + "/edges/Border CG B.png"));
        borders.left = ImageIO.read(new File(resourceBase + "/edges/Border CG L.png"));
        borders.right = ImageIO.read(new File(resourceBase + "/edges/Border CG R.png"));
        borders.topLeft = ImageIO.read(new File(resourceBase + "/corner/Border CG TL - Copy.png"));
        borders.topRight = ImageIO.read(new File(resourceBase + "/corner/Border CG TR.png"));
        borders.bottomLeft = ImageIO.read(new File(resourceBase + "/corner/Border CG BL.png"));
        borders.bottomRight = ImageIO.read(new File(resourceBase + "/corner/Border CG BR.png"));
        return borders;
    }

    public static CursorImages loadCursorImages() throws IOException {
        CursorImages cursors = new CursorImages();
        cursors.regular = ImageIO.read(new File(resourceBase + "/cursor/cursor reg.png"));
        cursors.click = ImageIO.read(new File(resourceBase + "/cursor/cursor click.png"));
        return cursors;
    }
}
