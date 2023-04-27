package com.xmudronc;

public class Map {
    private int mapX = 8;      //map width
    private int mapY = 8;      //map height
    private int mapS = 64;      //map cube size
    private int map[]={          //the map array. Edit to change level but keep the outer walls
        1,1,1,1,1,1,1,1,
        1,0,1,0,0,0,0,1,
        1,0,1,0,0,2,0,1,
        1,0,1,0,0,0,0,1,
        1,0,0,0,0,0,0,1,
        1,0,0,0,0,1,0,1,
        1,0,0,0,0,0,0,1,
        1,1,1,1,1,1,1,1,	
    };

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public int getMapS() {
        return mapS;
    }

    public int[] getMap() {
        return map;
    }
}
