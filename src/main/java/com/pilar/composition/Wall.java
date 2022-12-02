package com.pilar.composition;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class Wall implements Structure {
    private List<Block> blocks = new ArrayList<>();

    @Override
    public Optional<Block> findBlockByColor(String color) {
        if (Objects.isNull(color) || color.isEmpty()) {
            String message = "Color must be a value.";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        var result = blocks.stream().flatMap(Block::flatByStream)
                .filter(block -> block.getColor().equals(color))
                .findFirst();

        return result;
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        if (Objects.isNull(material) || material.isEmpty()) {
            String message = "Material must be a value.";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        var result = blocks.stream().flatMap(Block::flatByStream)
                .filter(block -> block.getMaterial().equals(material)).collect(Collectors.toList());
        if (result.isEmpty()) {
            String message = String.format("There is no block with material: %s.", material);
            log.warn(message);
            throw new NoSuchElementException(message);
        }
        return (List<Block>) result;
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    @Override
    public int count() {
        return (int) blocks.stream().flatMap(block -> block.flatByStream()).count();
    }
}