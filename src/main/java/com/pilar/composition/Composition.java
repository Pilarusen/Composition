package com.pilar.composition;

import java.util.List;

public class Composition extends BlockClass implements CompositeBlock {

    private List<Block> blocks;

    public Composition(String color, String material) {
        super(color, material);
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    @Override
    public List<Block> getBlocks() {
        return null;
    }

    public int countBlocks() {
        return 0;
        //TODO not implemented yet
    }
}
