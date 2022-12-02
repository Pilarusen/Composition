package com.pilar.composition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public Stream<Block> flatByStream() {
        return Stream.of(super.flatByStream(),
                        blocks.stream().flatMap(Block::flatByStream))
                .flatMap(block -> block);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Composition that = (Composition) o;
        return blocks.equals(that.blocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), blocks);
    }
}
