package com.xmudronc.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MapChunks {
    private List<MapChunk> chunks = new ArrayList<>();

    public MapChunks() {
        createDefaultChunks();
    }

    private void createDefaultChunks() {
        for (Chunk chunk : Chunk.values()) {
            chunks.add(chunk.mapChunk);
        }
    }

    public MapChunk getRandomChunk() {
        Random random = new Random();
        int index = random.nextInt(chunks.size());
        return chunks.get(index);
    }

    private enum Chunk {
        T(new MapChunk(new MapChunkDimensions(8, 7), Arrays.asList(
            Arrays.asList(0,0,0,0,0,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0,0),
            Arrays.asList(1,1,1,0,0,1,1,1),
            Arrays.asList(1,1,1,0,0,1,1,1),
            Arrays.asList(1,1,1,0,0,1,1,1),
            Arrays.asList(1,1,1,0,0,1,1,1),
            Arrays.asList(1,1,1,0,0,1,1,1)
        ))),
        I1(new MapChunk(new MapChunkDimensions(2, 8), Arrays.asList(
            Arrays.asList(0,0),
            Arrays.asList(0,0),
            Arrays.asList(0,0),
            Arrays.asList(0,0),
            Arrays.asList(0,0),
            Arrays.asList(0,0),
            Arrays.asList(0,0),
            Arrays.asList(0,0)
        ))),
        I2(new MapChunk(new MapChunkDimensions(4, 8), Arrays.asList(
            Arrays.asList(0,0,0,0),
            Arrays.asList(0,0,0,0),
            Arrays.asList(0,0,0,0),
            Arrays.asList(0,0,0,0),
            Arrays.asList(0,0,0,0),
            Arrays.asList(0,0,0,0),
            Arrays.asList(0,0,0,0),
            Arrays.asList(0,0,0,0)
        ))),
        L(new MapChunk(new MapChunkDimensions(7, 7), Arrays.asList(
            Arrays.asList(0,0,1,1,1,1,1),
            Arrays.asList(0,0,1,1,1,1,1),
            Arrays.asList(0,0,1,1,1,1,1),
            Arrays.asList(0,0,1,1,1,1,1),
            Arrays.asList(0,0,1,1,1,1,1),
            Arrays.asList(0,0,0,0,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0)
        ))),
        L1(new MapChunk(new MapChunkDimensions(7,7), Arrays.asList(
            Arrays.asList(0,0,0,1,1,1,1),
            Arrays.asList(0,0,0,1,1,1,1),
            Arrays.asList(0,0,0,1,1,1,1),
            Arrays.asList(0,0,0,1,1,1,1),
            Arrays.asList(0,0,0,0,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0)
        ))),
        J1(new MapChunk(new MapChunkDimensions(7, 7), Arrays.asList(
            Arrays.asList(1,1,1,1,1,0,0),
            Arrays.asList(1,1,1,1,1,0,0),
            Arrays.asList(1,1,1,1,1,0,0),
            Arrays.asList(1,1,1,1,1,0,0),
            Arrays.asList(1,1,1,1,1,0,0),
            Arrays.asList(0,0,0,0,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0)
        ))),
        J2(new MapChunk(new MapChunkDimensions(7, 7), Arrays.asList(
            Arrays.asList(1,1,1,1,0,0,0),
            Arrays.asList(1,1,1,1,0,0,0),
            Arrays.asList(1,1,1,1,0,0,0),
            Arrays.asList(1,1,1,1,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0)
        ))),
        Z(new MapChunk(new MapChunkDimensions(8, 6), Arrays.asList(
            Arrays.asList(0,0,0,0,0,1,1,1),
            Arrays.asList(0,0,0,0,0,1,1,1),
            Arrays.asList(1,1,1,0,0,1,1,1),
            Arrays.asList(1,1,1,0,0,1,1,1),
            Arrays.asList(1,1,1,0,0,0,0,0),
            Arrays.asList(1,1,1,0,0,0,0,0)
        ))),
        S(new MapChunk(new MapChunkDimensions(8, 6), Arrays.asList(
            Arrays.asList(1,1,1,0,0,0,0,0),
            Arrays.asList(1,1,1,0,0,0,0,0),
            Arrays.asList(1,1,1,0,0,1,1,1),
            Arrays.asList(1,1,1,0,0,1,1,1),
            Arrays.asList(0,0,0,0,0,1,1,1),
            Arrays.asList(0,0,0,0,0,1,1,1)
        ))),
        O1(new MapChunk(new MapChunkDimensions(8, 8), Arrays.asList(
            Arrays.asList(0,0,0,0,0,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0,0)
        ))),
        o2(new MapChunk(new MapChunkDimensions(8, 8), Arrays.asList(
            Arrays.asList(0,0,0,0,0,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0,0),
            Arrays.asList(0,0,0,1,1,0,0,0),
            Arrays.asList(0,0,0,1,1,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0,0),
            Arrays.asList(0,0,0,0,0,0,0,0)
        )));

        public final MapChunk mapChunk;

        private Chunk(MapChunk mapChunk) {
            this.mapChunk = mapChunk;
        }
    }
}
