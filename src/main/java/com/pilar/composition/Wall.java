package com.pilar.composition;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
public class Wall implements Structure {
    private final List<Block> blocks = new ArrayList<>();

    @Override
    public Optional<Block> findBlockByColor(String color) {
        checkIsNullOrEmpty(color);
        return blocksToFlatStream()
                .filter(block -> color.equals(block.getColor()))
                .findFirst();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        log.info("Finding blocks by material {}.", material);
        checkIsNullOrEmpty(material);
        List<Block> result =
                blocksToFlatStream()
                        .filter(block -> material.equals(block.getMaterial()))
                        .toList();
        checkIsEmpty(material, result);
        log.info("Number of found blocks {}.", result.size());
        return result;
    }

    private Stream<Block> blocksToFlatStream() {
        return blocks.stream().flatMap(Block::toStream);
    }

    private void checkIsEmpty(String material, List<Block> blockList) {
        if (blockList.isEmpty()) {
            String message = String.format("There is no block with material: %s.", material);
            log.warn(message);
            throw new NoSuchElementException(message);
        }
    }

    private void checkIsNullOrEmpty(String input) {
        if (StringUtils.isBlank(input)) {
            String message = "Input must not be null or empty.";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    public void addBlock(Block block) {
        blocks.add(block);
        log.info("Block {} was added.", block.toString());
    }

    @Override
    public int count() {
        return (int) blocksToFlatStream().count();
    }
}