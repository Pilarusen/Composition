package com.pilar.composition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Composition extends BlockClass implements CompositeBlock {

    private List<Block> blocks = new ArrayList<>();

    public Composition(String color, String material) {
        super(color, material);
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    @Override
    public List<Block> getBlocks() {
        return blocks;
    }
}
