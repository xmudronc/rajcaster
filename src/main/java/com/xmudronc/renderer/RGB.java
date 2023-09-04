package com.xmudronc.renderer;

import java.awt.Color;

public class RGB {
    private boolean opaque;
    private Color fColor;
    private Color bColor;


    public RGB(int R, int G, int B) {
        this.opaque = true;
        this.fColor = new Color(clampValue(R), clampValue(G), clampValue(B));
        this.bColor = new Color(clampValue(R), clampValue(G), clampValue(B));
    }

    public RGB(Color color) {
        this.opaque = true;
        this.fColor = color;
        this.bColor = color;
    }

    public RGB(int R, int G, int B, boolean opaque) {
        this.opaque = opaque;
        this.fColor = new Color(clampValue(R), clampValue(G), clampValue(B));
        this.bColor = new Color(clampValue(R), clampValue(G), clampValue(B));
    }

    private int clampValue(int value) {
        if (value < 0)
            return 0;
        if (value > 255)
            return 255;
        return value;
    }

    public Color getFgColor() {
        return fColor;
    }

    public Color getBgColor() {
        return bColor;
    }

    public int getR() {
        return fColor.getRed();
    }

    public int getG() {
        return fColor.getGreen();
    }

    public int getB() {
        return fColor.getBlue();
    }

    public boolean isOpaque() {
        return opaque;
    }

    public void setBgRGB(RGB rgb) {
        this.bColor = new Color(rgb.getR(), rgb.getG(), rgb.getB());
    }

    public String getRGB() {
        return String.format(
            "\u001B[38;2;%d;%d;%dm\u001B[48;2;%d;%d;%dm",
            bColor.getRed(),
            bColor.getGreen(),
            bColor.getBlue(),
            fColor.getRed(),
            fColor.getGreen(),
            fColor.getBlue()
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        final RGB other = (RGB) obj;
        
        if (this.fColor.getRed() != other.fColor.getRed()) {
            return false;
        }
        if (this.fColor.getGreen() != other.fColor.getGreen()) {
            return false;
        }
        if (this.fColor.getBlue() != other.fColor.getBlue()) {
            return false;
        }

        if (this.bColor.getRed() != other.bColor.getRed()) {
            return false;
        }
        if (this.bColor.getGreen() != other.bColor.getGreen()) {
            return false;
        }
        if (this.bColor.getBlue() != other.bColor.getBlue()) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(
            String.valueOf(fColor.getRed()) 
            + String.valueOf(fColor.getGreen()) 
            + String.valueOf(fColor.getBlue()) 
            + String.valueOf(bColor.getRed()) 
            + String.valueOf(bColor.getGreen()) 
            + String.valueOf(bColor.getBlue())
        );
    }
}
