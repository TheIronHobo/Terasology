/*
 * Copyright 2011 Benjamin Glatzel <benjamin.glatzel@me.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.begla.blockmania.generators;

import com.github.begla.blockmania.world.Chunk;
import com.github.begla.blockmania.Configuration;

/**
 * Generates some trees, flowers and high grass.
 *
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
public class ChunkGeneratorForest extends ChunkGeneratorTerrain {

    /**
     * Init. the forest generator.
     * 
     * @param seed
     */
    public ChunkGeneratorForest(String seed) {
        super(seed);
    }

    /**
     * Apply the generation process to the given chunk.
     * 
     * @param c
     */
    @Override
    public void generate(Chunk c) {
        for (int y = 0; y < Configuration.CHUNK_DIMENSIONS.y; y++) {
            for (int x = 0; x < Configuration.CHUNK_DIMENSIONS.x; x++) {
                for (int z = 0; z < Configuration.CHUNK_DIMENSIONS.z; z++) {
                    generateGrassAndFlowers(c, x, y, z);
                    generateTree(c, x, y, z);
                }
            }
        }
    }

    /**
     * 
     * @param c
     * @param x
     * @param y
     * @param z 
     */
    protected void generateGrassAndFlowers(Chunk c, int x, int y, int z) {
        float grassDens = calcGrassDensity(c.getBlockWorldPosX(x), c.getBlockWorldPosY(y), c.getBlockWorldPosZ(z));
        if (c.getBlock(x, y, z) == 0x1 && grassDens > 0.0) {
            /*
             * Generate high grass.
             */
            if (_rand.standNormalDistrDouble() > 0) {
                if (c.canBlockSeeTheSky(x, y + 1, z)) {
                    c.setBlock(x, y + 1, z, (byte) 0xB);
                }
            } else {
                if (c.canBlockSeeTheSky(x, y + 1, z)) {
                    c.setBlock(x, y + 1, z, (byte) 0xC);
                }
            }

            /*
             * Generate flowsers.
             */
            if (_rand.standNormalDistrDouble() < -2) {
                if (_rand.randomBoolean()) {
                    if (c.canBlockSeeTheSky(x, y + 1, z)) {
                        c.setBlock(x, y + 1, z, (byte) 0x9);
                    }
                } else {
                    if (c.canBlockSeeTheSky(x, y + 1, z)) {
                        c.setBlock(x, y + 1, z, (byte) 0xA);
                    }
                }

            }
        }
    }

    /**
     * 
     * @param c
     * @param x
     * @param y
     * @param z 
     */
    protected void generateTree(Chunk c, int x, int y, int z) {
        // Do not create trees too close to surrounding blocks.
        if (c.getParent().isBlockSurrounded(c.getBlockWorldPosX(x), c.getBlockWorldPosY(y + 1), c.getBlockWorldPosZ(z)) || !c.canBlockSeeTheSky(x, y + 1, z)) {
            return;
        }

        float forestDens = calcForestDensity(c.getBlockWorldPosX(x), c.getBlockWorldPosY(y), c.getBlockWorldPosZ(z));
        if (forestDens > 0f && c.getBlock(x, y, z) == 0x1 && y > 32) {
            if (Math.abs(_rand.randomDouble()) < 0.04f) {
                if (_rand.randomBoolean()) {
                    c.setBlock(x, y + 1, z, (byte) 0x0);
                    c.getParent().getGeneratorPineTree().generate(c.getBlockWorldPosX(x), c.getBlockWorldPosY((int) y) + 1, c.getBlockWorldPosZ(z), false);
                } else {
                    c.setBlock(x, y + 1, z, (byte) 0x0);
                    c.getParent().getGeneratorTree().generate(c.getBlockWorldPosX(x), c.getBlockWorldPosY((int) y) + 1, c.getBlockWorldPosZ(z), false);
                }
            }
        }
    }

    /**
     * Returns the cave density for the base terrain.
     * 
     * @param x
     * @param y
     * @param z
     * @return
     */
    protected float calcForestDensity(float x, float y, float z) {
        float result = 0.0f;
        result += _pGen3.noiseWithOctaves(0.04f * x, 0.04f * y, 0.04f * z, 16, 0.5f);
        return result;
    }

    /**
     * 
     * @param x
     * @param y
     * @param z
     * @return
     */
    protected float calcGrassDensity(float x, float y, float z) {
        float result = 0.0f;
        result += _pGen3.noiseWithOctaves(0.8f * x, 0.8f * y, 0.8f * z, 8, 0.5f);
        return result;
    }
}