package com.xmudronc.generator;

import java.util.ArrayList;
import java.util.List;

public class MapChunk {
    private MapChunkDimensions dimensions;
    private List<List<Integer>> map = new ArrayList<>();

    public MapChunk(MapChunkDimensions dimensions, List<List<Integer>> map) {
        this.dimensions = dimensions;
        this.map = map;
    }

    public void debugPrint() {
        for (List<Integer> y : map) {
            for (Integer x : y) {
                System.out.print(x);
            }
            System.out.println();
        }
    }

    public void placeChunk(int x, int y, MapChunk chunk) {
        for (int chunkY = 0; chunkY < chunk.getMap2D().size(); chunkY++) {
            for (int chunkX = 0; chunkX < chunk.getMap2D().get(chunkY).size(); chunkX++) {
                map.get(y + chunkY).set(x + chunkX, chunk.getMap2D().get(chunkY).get(chunkX));
            }
        }
    }

    public MapChunkDimensions getDimensions() {
        return dimensions;
    }

    public int[] getMap1D() {
        int[] map1d = new int[map.size()*map.get(0).size()];

        int index = 0;
        for (List<Integer> y : map) {
            for (Integer x : y) {
                map1d[index]=x;
                index++;
            }
        }
        return map1d;
    }

    public List<List<Integer>> getMap2D() {
        return map;
    }

    public int getMapX() {
        return dimensions.getMapX();
    }

    public int getMapY() {
        return dimensions.getMapY();
    }

    public int getMapS() {
        return dimensions.getMapS();
    }
}
