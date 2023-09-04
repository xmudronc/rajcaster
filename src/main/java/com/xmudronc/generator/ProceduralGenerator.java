package com.xmudronc.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProceduralGenerator {
    private static final int DEFAULT_CHUNKS = 8;
    private static final int MIN_CHUNKS = 4;
    private static final int MAX_CHUNKS = 16;
    private static final int CHUNK_SIZE = 8;

    public MapChunk generateLevel(int chunks, MapStyle style) {
        if (chunks < MIN_CHUNKS) {
            chunks = MIN_CHUNKS;
        }

        if (chunks > MAX_CHUNKS) {
            chunks = MAX_CHUNKS;
        }

        return generateMapFromChunks(chunks, style);
    }
    
    public MapChunk generateLevel() {
        return generateLevel(DEFAULT_CHUNKS, MapStyle.WIDE);
    }

    private MapChunk generateMapFromChunks(int chunks, MapStyle style) {
        if (chunks % 2 != 0 && chunks % 3 != 0) {
            chunks++;
        }

        if (style == MapStyle.COMPACT) {
            int divider = pickDivider(chunks);
            int mapX = (divider * CHUNK_SIZE) + 2;
            int mapY = ((chunks / divider) * CHUNK_SIZE) + 2;
            List<List<Integer>> map = new ArrayList<>();
            MapChunkDimensions dimensions = new MapChunkDimensions(mapX, mapY);
            for (int y = 0; y < dimensions.getMapY(); y++) {
                List<Integer> tmpList = new ArrayList<>();
                for (int x = 0; x < dimensions.getMapX(); x++) {
                    if (x == 0 || x == dimensions.getMapX() - 1 || y == 0 || y == dimensions.getMapY() - 1) {
                        tmpList.add(1);
                    } else {
                        tmpList.add(0);
                    }
                }
                map.add(tmpList);
            }
            MapChunk mapChunk = new MapChunk(dimensions, map);
            MapChunks mapChunks = new MapChunks();
            mapChunk.placeChunk(1, 1, mapChunks.getRandomChunk());
            mapChunk.placeChunk(8, 1, mapChunks.getRandomChunk());
            mapChunk.placeChunk(1, 8, mapChunks.getRandomChunk());
            mapChunk.placeChunk(8, 8, mapChunks.getRandomChunk());
            mapChunk.debugPrint();
            return mapChunk;
        } else {
            //TODO
            return generateMapFromChunks(chunks, MapStyle.COMPACT);
        }
    }

    private int pickDivider(int chunks) {
        if (Arrays.asList(16, 12).contains(chunks)) {
            return 4;
        }
        if (Arrays.asList(15, 9).contains(chunks)) {
            return 3;
        }
        return 2;
    }
}
