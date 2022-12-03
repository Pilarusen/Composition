package com.pilar.composition;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@Slf4j
public class Composition extends BlockClass implements CompositeBlock {

    private List<Block> blocks = new ArrayList<>();

    public Composition(String color, String material) {
        super(color, material);
    }

    public void addBlock(Block block) {
        checkIsEqual(block);
        checkIsBlockPresentInStructure(block);
        blocks.add(block);
    }

    private void checkIsEqual(Block block) {
        if (this.equals(block)) {
            String message = "Can not add composition to itself.";
            log.error(message);
            throw new IllegalArgumentException("Can not add composition to itself.");
        }
    }

    private void checkIsBlockPresentInStructure(Block block) {
        if (isBlockPresent(block)) {
            String message = "Block you try to add is already in structure.";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    private boolean isBlockPresent(Block block) {
        //TODO check if block has this
        //TODO think about add custom exception
        boolean result = false;
        if (block instanceof CompositeBlock) {
            log.info("Checking if composition is already in structure.");
            result = ((CompositeBlock) block).getBlocks()
                    .stream()
                    .flatMap(Block::flatByStream)
                    .anyMatch(composition -> composition.equals(this));
        }
        return result;
    }

    @Override
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
