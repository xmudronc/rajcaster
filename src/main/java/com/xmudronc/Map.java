package com.xmudronc;

public class Map {
    private int map[][]={          //the map array. Edit to change level but keep the outer walls
        {1,1,1,1,1,1,1,1},
        {1,0,1,0,0,2,0,1},
        {1,0,1,0,0,0,0,1},
        {1,0,1,0,0,0,0,1},
        {1,0,0,0,0,0,0,1},
        {1,0,0,0,0,1,0,1},
        {1,0,0,0,0,0,0,1},
        {1,1,1,1,1,1,1,1},	
    };

    public int getMapX() {
        return map.length;
    }

    public int getMapY() {
        return map[0].length;
    }

    public int getMapS() {
        return map.length * map[0].length;
    }

    public int[][] getMap() {
        return map;
    }

    public int[] getMap1D() {
        int[] array = new int[getMapS()];
        for (int x = 0; x < getMapX(); x++) {
            for (int y = 0; y < getMapY(); y++) {
                array[y + (x * getMapX())] = map[x][y];
            }
        }
        return array;
    }
}
