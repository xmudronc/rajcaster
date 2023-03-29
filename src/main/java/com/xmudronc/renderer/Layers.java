package com.xmudronc.renderer;

import org.jline.terminal.Size;

public class Layers {
    private RGB[][] layer1;
    private RGB[][] layer2;
    private RGB[][] layer3;
    private RGB[][] layer4;

    public Layers(Size runSize) {
        layer1 = new RGB[runSize.getColumns()][runSize.getRows()*2];
        layer2 = new RGB[runSize.getColumns()][runSize.getRows()*2];
        layer3 = new RGB[runSize.getColumns()][runSize.getRows()*2];
        layer4 = new RGB[runSize.getColumns()][runSize.getRows()*2];
    }

    public RGB[][] getLayer(int layerNumber) {
        switch (layerNumber) {
            case 1:
                return layer1;
            case 2:
                return layer2;
            case 3:
                return layer3;
            case 4:
                return layer4;
            default:
                return null;
        }
    }
}
