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
        public BufferedImage j1_c;
        public BufferedImage j2_c;
        public BufferedImage j3_c;
        public BufferedImage j4_c;
        public BufferedImage j5_c;
        public BufferedImage j6_c;
        public BufferedImage j7_c;
        public BufferedImage j8_c;
        public BufferedImage j9_c;
        public BufferedImage j10_c;
        public BufferedImage j11_c;
        public BufferedImage j1_l;
        public BufferedImage j2_l;
        public BufferedImage j3_l;
        public BufferedImage j4_l;
        public BufferedImage j5_l;
        public BufferedImage j6_l;
        public BufferedImage j7_l;
        public BufferedImage j8_l;
        public BufferedImage j9_l;
        public BufferedImage j10_l;
        public BufferedImage j11_l;
    }

    public static class HairImages {
        public BufferedImage hair;
    }

    public static class EyeImages {
        public BufferedImage e1_c;
        public BufferedImage e2_c;
        public BufferedImage e3_c;
        public BufferedImage e4_c;
        public BufferedImage e5_c;
        public BufferedImage e6_c;
        public BufferedImage e7_c;
        public BufferedImage e8_c;
        public BufferedImage e1_l;
        public BufferedImage e2_l;
        public BufferedImage e3_l;
        public BufferedImage e4_l;
        public BufferedImage e5_l;
        public BufferedImage e6_l;
        public BufferedImage e7_l;
        public BufferedImage e8_l;
    }

    public static class ScoleraImages {
        public BufferedImage s1;
        public BufferedImage s2;
        public BufferedImage s3;
        public BufferedImage s4;
        public BufferedImage s5;
        public BufferedImage s6;
        public BufferedImage s7;
        public BufferedImage s8;
    }

    public static class IrisImages {
        public BufferedImage i1_3_c;
        public BufferedImage i4_5_c;
        public BufferedImage i1_l;
        public BufferedImage i2_l;
        public BufferedImage i3_l;
        public BufferedImage i4_l;
        public BufferedImage i5_l;
    }

    public static class MouthImages {
        public BufferedImage l1;
        public BufferedImage l2;
        public BufferedImage l3;
        public BufferedImage l4;
        public BufferedImage l5;
        public BufferedImage l6;
        public BufferedImage l7;
        public BufferedImage l8;
    }

    public static class ClothingImages {
        public BufferedImage top;
        public BufferedImage bottom;
        public BufferedImage fullDress;
        public BufferedImage shoe;
        public BufferedImage accessory;
    }

    public static class NoseImages {
        public BufferedImage n1;
        public BufferedImage n2;
        public BufferedImage n3;
        public BufferedImage n4;
        public BufferedImage n5;
    }

    public static class EarImages {
        public BufferedImage e1_c;
        public BufferedImage e2_c;
        public BufferedImage e3_c;
        public BufferedImage e4_c;
        public BufferedImage e5_c;
        public BufferedImage e6_c;
        public BufferedImage e7_c;
        public BufferedImage e8_c;
        public BufferedImage e1_l;
        public BufferedImage e2_l;
        public BufferedImage e3_l;
        public BufferedImage e4_l;
        public BufferedImage e5_l;
        public BufferedImage e6_l;
        public BufferedImage e7_l;
        public BufferedImage e8_l;
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

    public static MaleBodyImages loadMaleBodyImages() throws IOException {
        MaleBodyImages images = new MaleBodyImages();
        images.m1_c = load("body/male/color/M1 body color.png");
        images.m2_c = load("body/male/color/M2 body color.png");
        images.m3_c = load("body/male/color/M3 body color.png");
        images.m4_c = load("body/male/color/M4 body color.png");
        images.m5_c = load("body/male/color/M5 body color.png");
        images.m6_c = load("body/male/color/M6 body color.png");
        images.m7_c = load("body/male/color/M7 body color.png");
        images.m1_l = load("body/male/line/M1 body line.png");
        images.m2_l = load("body/male/line/M2 body line.png");
        images.m3_l = load("body/male/line/M3 body line.png");
        images.m4_l = load("body/male/line/M4 body line.png");
        images.m5_l = load("body/male/line/M5 body line.png");
        images.m6_l = load("body/male/line/M6 body line.png");
        images.m7_l = load("body/male/line/M7 body line.png");
        return images;
    }

    public static FaceImages loadFaceImages() throws IOException {
        FaceImages images = new FaceImages();
        images.j1_c = load("face/color/J1 color.png");
        images.j2_c = load("face/color/J2 color.png");
        images.j3_c = load("face/color/J3 color.png");
        images.j4_c = load("face/color/J4 color.png");
        images.j5_c = load("face/color/J5 color.png");
        images.j6_c = load("face/color/J6 color.png");
        images.j7_c = load("face/color/J7 color.png");
        images.j8_c = load("face/color/J8 color.png");
        images.j9_c = load("face/color/J9 color.png");
        images.j10_c = load("face/color/J10 color.png");
        images.j11_c = load("face/color/J11 color.png");
        images.j1_l = load("face/line/J1 line.png");
        images.j2_l = load("face/line/J2 line.png");
        images.j3_l = load("face/line/J3 line.png");
        images.j4_l = load("face/line/J4 line.png");
        images.j5_l = load("face/line/J5 line.png");
        images.j6_l = load("face/line/J6 line.png");
        images.j7_l = load("face/line/J7 line.png");
        images.j8_l = load("face/line/J8 line.png");
        images.j9_l = load("face/line/J9 line.png");
        images.j10_l = load("face/line/J10 line.png");
        images.j11_l = load("face/line/J11 line.png");

        return images;
    }

    public static HairImages loadHairImages() throws IOException {
        HairImages images = new HairImages();
        images.hair = load("hair/hair.png");
        return images;
    }

    public static EyeImages loadEyeImages() throws IOException {
        EyeImages images = new EyeImages();
        images.e1_c = load("eye/color/Eye1 color.png");
        images.e2_c = load("eye/color/Eye2 color.png");
        images.e3_c = load("eye/color/Eye3 color.png");
        images.e4_c = load("eye/color/Eye4 color.png");
        images.e5_c = load("eye/color/Eye5 color.png");
        images.e6_c = load("eye/color/Eye6 color.png");
        images.e7_c = load("eye/color/Eye7 color.png");
        images.e8_c = load("eye/color/Eye8 color.png");
        images.e1_l = load("eye/line/Eye1 line.png");
        images.e2_l = load("eye/line/Eye2 line.png");
        images.e3_l = load("eye/line/Eye3 line.png");
        images.e4_l = load("eye/line/Eye4 line.png");
        images.e5_l = load("eye/line/Eye5 line.png");
        images.e6_l = load("eye/line/Eye6 line.png");
        images.e7_l = load("eye/line/Eye7 line.png");
        images.e8_l = load("eye/line/Eye8 line.png");

        return images;
    }

    public static ScoleraImages loadScoleraImages() throws IOException {
        ScoleraImages images = new ScoleraImages();
        images.s1 = load("eye/scolera/S1 color.png");
        images.s2 = load("eye/scolera/S2 color.png");
        images.s3 = load("eye/scolera/S3 color.png");
        images.s4 = load("eye/scolera/S4 color.png");
        images.s5 = load("eye/scolera/S5 color.png");
        images.s6 = load("eye/scolera/S6 color.png");
        images.s7 = load("eye/scolera/S7 color.png");
        images.s8 = load("eye/scolera/S8 color.png");
        return images;
    }

    public static IrisImages loadIrisImages() throws IOException {
        IrisImages images = new IrisImages();
        images.i1_3_c = load("eye/iris/color/I1-3 color.png");
        images.i4_5_c = load("eye/iris/color/I4-5 color.png");
        images.i1_l = load("eye/iris/line/I1 line.png");
        images.i2_l = load("eye/iris/line/I2 line.png");
        images.i3_l = load("eye/iris/line/I3 line.png");
        images.i4_l = load("eye/iris/line/I4 line.png");
        images.i5_l = load("eye/iris/line/I5 line.png");
        return images;
    }

    public static MouthImages loadMouthImages() throws IOException {
        MouthImages images = new MouthImages();
        images.l1 = load("mouth/L1.png");
        images.l2 = load("mouth/L2.png");
        images.l3 = load("mouth/L3.png");
        images.l4 = load("mouth/L4.png");
        images.l5 = load("mouth/L5.png");
        images.l6 = load("mouth/L6.png");
        images.l7 = load("mouth/L7.png");
        images.l8 = load("mouth/L8.png");
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
        images.n1 = load("nose/N1.png");
        images.n2 = load("nose/E2.png");
        images.n3 = load("nose/N3.png");
        images.n4 = load("nose/N4.png");
        images.n5 = load("nose/N5.png");

        return images;
    }

    public static EarImages loadEarImages() throws IOException {
        EarImages images = new EarImages();
        images.e1_c = load("ear/color/E1 color.png");
        images.e2_c = load("ear/color/E2 color.png");
        images.e3_c = load("ear/color/E3 color.png");
        images.e4_c = load("ear/color/E4 color.png");
        images.e5_c = load("ear/color/E5 color.png");
        images.e6_c = load("ear/color/E6 color.png");
        images.e7_c = load("ear/color/E7 color.png");
        images.e8_c = load("ear/color/E8 color.png");
        images.e1_l = load("ear/line/E1 line.png");
        images.e2_l = load("ear/line/E2 line.png");
        images.e3_l = load("ear/line/E3 line.png");
        images.e4_l = load("ear/line/E4 line.png");
        images.e5_l = load("ear/line/E5 line.png");
        images.e6_l = load("ear/line/E6 line.png");
        images.e7_l = load("ear/line/E7 line.png");
        images.e8_l = load("ear/line/E8 line.png");
        return images;
    }

    private static BufferedImage load(String relativePath) throws IOException {
        return ImageIO.read(new File(resource_base + "/" + relativePath));
    }

    public static BufferedImage loadOptional(String path) {
        try {
            return ImageIO.read(new File(resource_base + "/" + path));
        } catch (Exception e) {
            return null;
        }
    }
}
