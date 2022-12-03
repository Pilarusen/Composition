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
            throw new IllegalArgumentException("Can not add composition to itself.");
        }
    }

    private void checkIsBlockPresentInStructure(Block block) {
        if (isBlockPresent(block)) {
            throw new IllegalArgumentException("Block you try to add is already in structure.");
        }
    }

    private boolean isBlockPresent(Block block) {
        //TODO check if block has this
        //TODO think about add custom exception
        if (block instanceof CompositeBlock) {
            log.info("checking composition");

           var compositionList = ((CompositeBlock) block).getBlocks();

            boolean result = compositionList.stream()
                    .flatMap(Block::flatByStream)
                    .anyMatch(composition -> composition.equals(this));

            log.info("==============================");
            log.info(String.valueOf(result));
            log.info("==============================");

            if (result) {
                log.error("error");
                throw new IllegalArgumentException();
            }
        } else {
            System.out.println("It is not a composition");
        }
        return false;
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
