package com.test.DisplayUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResourceManager {
    private static final String resource_base = "src/main/resources";

    private ResourceManager() {}

    public static class BorderImages {
        public BufferedImage top;
        public BufferedImage bottom;
        public BufferedImage left;
        public BufferedImage right;
        public BufferedImage topLeft;
        public BufferedImage topRight;
        public BufferedImage bottomLeft;
        public BufferedImage bottomRight;
    }

    public static class CursorImages {
        public BufferedImage regular;
        public BufferedImage click;
    }

    public static class FemaleBodyImages {
        public BufferedImage body;
        public BufferedImage face;
        public BufferedImage hair;
    }

    public static class MaleBodyImages {
        public BufferedImage m1_c;
        public BufferedImage m2_c;
        public BufferedImage m3_c;
        public BufferedImage m4_c;
        public BufferedImage m5_c;
        public BufferedImage m6_c;
        public BufferedImage m7_c;
        public BufferedImage m1_l;
        public BufferedImage m2_l;
        public BufferedImage m3_l;
        public BufferedImage m4_l;
        public BufferedImage m5_l;
        public BufferedImage m6_l;
        public BufferedImage m7_l;
    }

    public static class FaceImages {
        public BufferedImage eyes;
        public BufferedImage mouth;
    }

    public static class HairImages {
        public BufferedImage hair;
    }

    public static class EyeImages {
        public BufferedImage eyes;
    }

    public static class MouthImages {
        public BufferedImage mouth;
    }

    public static class ClothingImages {
        public BufferedImage top;
        public BufferedImage bottom;
        public BufferedImage fullDress;
        public BufferedImage shoe;
        public BufferedImage accessory;
    }

    public static class NoseImages {
        public BufferedImage nose;
    }

    public static class EarImages {
        public BufferedImage ears;
    }

    public static class TailImages {
        public BufferedImage tail;
    }

    public static class WingImages {
        public BufferedImage wings;
    }

    public static class AccessoryImages {
        public BufferedImage accessory;
    }

    public static BorderImages loadBorderImages() throws IOException {
        BorderImages images = new BorderImages();
        images.top = load("edges/Border CG T.png");
        images.bottom = load("edges/Border CG B.png");
        images.left = load("edges/Border CG L.png");
        images.right = load("edges/Border CG R.png");
        images.topLeft = load("corner/Border CG TL - Copy.png");
        images.topRight = load("corner/Border CG TR.png");
        images.bottomLeft = load("corner/Border CG BL.png");
        images.bottomRight = load("corner/Border CG BR.png");
        return images;
    }

    public static CursorImages loadCursorImages() throws IOException {
        CursorImages images = new CursorImages();
        images.regular = load("cursor/cursor reg.png");
        images.click = load("cursor/cursor click.png");
        return images;
    }

    public static FemaleBodyImages loadFemaleBodyImages() throws IOException {
        FemaleBodyImages images = new FemaleBodyImages();
        images.body = load("body/female_body.png");
        images.face = load("body/female_face.png");
        images.hair = load("body/female_hair.png");
        return images;
    }

    public static MaleBodyImages loadMaleBodyImages() throws IOException {
        MaleBodyImages images = new MaleBodyImages();
        images.m1_c = load("M1 body color.png");
        images.m2_c = load("M2 body color.png");
        images.m3_c = load("M3 body color.png");
        images.m4_c = load("M4 body color.png");
        images.m5_c = load("M5 body color.png");
        images.m6_c = load("M6 body color.png");
        images.m7_c = load("M7 body color.png");
        images.m1_l = load("M1 body line.png");
        images.m2_l = load("M2 body line.png");
        images.m3_l = load("M3 body line.png");
        images.m4_l = load("M4 body line.png");
        images.m5_l = load("M5 body line.png");
        images.m6_l = load("M6 body line.png");
        images.m7_l = load("M7 body line.png");
        return images;
    }

    public static FaceImages loadFaceImages() throws IOException {
        FaceImages images = new FaceImages();
        images.eyes = load("face/eyes.png");
        images.mouth = load("face/mouth.png");
        return images;
    }

    public static HairImages loadHairImages() throws IOException {
        HairImages images = new HairImages();
        images.hair = load("hair/hair.png");
        return images;
    }

    public static EyeImages loadEyeImages() throws IOException {
        EyeImages images = new EyeImages();
        images.eyes = load("eyes/eyes.png");
        return images;
    }

    public static MouthImages loadMouthImages() throws IOException {
        MouthImages images = new MouthImages();
        images.mouth = load("mouth/mouth.png");
        return images;
    }

    public static ClothingImages loadClothingImages() throws IOException {
        ClothingImages images = new ClothingImages();
        images.top = load("clothing/top.png");
        images.bottom = load("clothing/bottom.png");
        images.fullDress = load("clothing/full dress.png");
        images.shoe = load("clothing/shoe.png");
        images.accessory = load("clothing/accessory.png");
        return images;
    }

    public static NoseImages loadNoseImages() throws IOException {
        NoseImages images = new NoseImages();
        images.nose = load("nose/nose.png");
        return images;
    }

    public static EarImages loadEarImages() throws IOException {
        EarImages images = new EarImages();
        images.ears = load("ears/ears.png");
        return images;
    }

    public static TailImages loadTailImages() throws IOException {
        TailImages images = new TailImages();
        images.tail = load("tail/tail.png");
        return images;
    }

    public static WingImages loadWingImages() throws IOException {
        WingImages images = new WingImages();
        images.wings = load("wings/wings.png");
        return images;
    }

    public static AccessoryImages loadAccessoryImages() throws IOException {
        AccessoryImages images = new AccessoryImages();
        images.accessory = load("accessory/accessory.png");
        return images;
    }

    private static BufferedImage load(String relativePath) throws IOException {
        return ImageIO.read(new File(resource_base + "/" + relativePath));
    }
}
