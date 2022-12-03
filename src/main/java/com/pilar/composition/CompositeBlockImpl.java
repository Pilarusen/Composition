package com.pilar.composition;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Getter
@Slf4j
public class CompositeBlockImpl extends BlockImpl implements CompositeBlock {

    private final List<Block> blocks = new ArrayList<>();

    public CompositeBlockImpl(String color, String material) {
        super(color, material);
    }

    public void addBlock(Block block) {
        checkIsEqual(block);
        checkIsBlockPresentInStructure(block);
        blocks.add(block);
    }

    /**
     * Methods checkIsEqual(block) and checkIsBlockPresentInStructure(block) are to avoid from add
     * composition to another composition, due to avoid StackOverflowError when using Wall.class methods:
     * - findBlockByColor(String color)
     * - findBlocksByMaterial(String material)
     * - count().
     * The reason is that flatByStream() which is essential to do above operations
     * is providing to StackOverflowError when:
     * - composition is a block of itself (checkIsEqual(block)),
     * - composition is already in structure of composition (checkIsBlockPresentInStructure(block))*
     */
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

    //checks if composition has this
    private boolean isBlockPresent(Block block) {
        boolean result = false;
        if (block instanceof CompositeBlock) {
            log.info("Checking if composition is already in structure.");
            result = ((CompositeBlock) block).getBlocks()
                    .stream()
                    .flatMap(Block::toStream)
                    .anyMatch(composition -> composition.equals(this));
        }
        return result;
    }

    @Override
    public Stream<Block> toStream() {
        return Stream.of(super.toStream(),
                        blocks.stream().flatMap(Block::toStream))
                .flatMap(block -> block);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CompositeBlockImpl that = (CompositeBlockImpl) o;
        return blocks.equals(that.blocks);
    }
}
