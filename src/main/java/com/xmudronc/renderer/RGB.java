package com.xmudronc.renderer;

import java.awt.Color;

public class RGB {
    private int fR;
    private int fG;
    private int fB;
    private int bR;
    private int bG;
    private int bB;
    private boolean opaque;

    public RGB(int R, int G, int B) {
        this.fR = clampValue(R);
        this.fG = clampValue(G);
        this.fB = clampValue(B);
        this.bR = clampValue(R);
        this.bG = clampValue(G);
        this.bB = clampValue(B);
        this.opaque = true;
    }

    public RGB(int R, int G, int B, boolean opaque) {
        this.fR = clampValue(R);
        this.fG = clampValue(G);
        this.fB = clampValue(B);
        this.bR = clampValue(R);
        this.bG = clampValue(G);
        this.bB = clampValue(B);
        this.opaque = opaque;
    }

    private int clampValue(int value) {
        if (value < 0)
            return 0;
        if (value > 255)
            return 255;
        return value;
    }

    public int getR() {
        return fR;
    }

    public int getG() {
        return fG;
    }

    public int getB() {
        return fB;
    }

    public boolean isOpaque() {
        return opaque;
    }

    public void setBgRGB(RGB rgb) {
        this.bR = rgb.getR();
        this.bG = rgb.getG();
        this.bB = rgb.getB();
    }

    public String getRGB() {
        return String.format("\u001B[38;2;%d;%d;%dm\u001B[48;2;%d;%d;%dm", bR, bG, bB, fR, fG, fB);
    }

    public Color getColor() {
        return new Color(fR, fG, fB);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        final RGB other = (RGB) obj;
        
        if (this.fR != other.fR) {
            return false;
        }
        if (this.fG != other.fG) {
            return false;
        }
        if (this.fB != other.fB) {
            return false;
        }

        if (this.bR != other.bR) {
            return false;
        }
        if (this.bG != other.bG) {
            return false;
        }
        if (this.bB != other.bB) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(
            String.valueOf(fR) 
            + String.valueOf(fG) 
            + String.valueOf(fB) 
            + String.valueOf(bR) 
            + String.valueOf(bG) 
            + String.valueOf(bB)
        );
    }
}
