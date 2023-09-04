package com.xmudronc.generator;

public class MapChunkDimensions {
    private int mapX;      //map chunk width
    private int mapY;      //map chunk height
    private int mapS;      //map cube size

    public MapChunkDimensions(int mapX, int mapY) {
        this.mapX = mapX;
        this.mapY = mapY;
        mapS = mapX * mapY;
    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public int getMapS() {
        return mapS;
    }
}