package com.pilar.composition;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Wall implements Structure {
    private List<Block> blocks = new ArrayList<>();

    @Override
    public Optional<Block> findBlockByColor(String color) {
        checkIsNullOrEmpty(color);
        return blocksToStream()
                .filter(block -> block.getColor().equals(color))
                .findFirst();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        checkIsNullOrEmpty(material);
        var result = blocksToStream()
                .filter(block -> block.getMaterial().equals(material))
                .collect(Collectors.toList());
        checkIsEmpty(material, result);
        return result;
    }

    private void checkIsEmpty(String material, List<Block> blockList) {
        if (blockList.isEmpty()) {
            String message = String.format("There is no block with material: %s.", material);
            log.warn(message);
            throw new NoSuchElementException(message);
        }
    }

    private void checkIsNullOrEmpty(String input) {
        if (Objects.isNull(input) || input.isEmpty()) {
            String message = "Input must be a value.";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    private Stream<Block> blocksToStream() {
        return blocks.stream().flatMap(Block::flatByStream);
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    @Override
    public int count() {
        return (int) blocksToStream().count();
    }
}