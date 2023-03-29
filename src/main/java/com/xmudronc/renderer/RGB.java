package com.xmudronc.renderer;

public class RGB {
    private int fR;
    private int fG;
    private int fB;
    private int bR;
    private int bG;
    private int bB;

    public RGB(int R, int G, int B) {
        this.fR = R;
        this.fG = G;
        this.fB = B;
        this.bR = R;
        this.bG = G;
        this.bB = B;
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

    public void setBgRGB(RGB rgb) {
        this.bR = rgb.getR();
        this.bG = rgb.getG();
        this.bB = rgb.getB();
    }

    public String getRGB() {
        return String.format("\u001B[38;2;%d;%d;%dm\u001B[48;2;%d;%d;%dm", bR, bG, bB, fR, fG, fB);
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
