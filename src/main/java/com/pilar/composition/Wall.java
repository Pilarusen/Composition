package com.pilar.composition;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class Wall implements Structure {
    private List<Block> blocks;

    @Override
    public Optional<Block> findBlockByColor(String color) {
        if (Objects.isNull(color)) {
            String message = "Color must not be null.";
            log.error(message);
            throw new NullPointerException("Color must not be null.");
        }
        return blocks.stream().filter(block -> block.getColor().equals(color)).findFirst();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        if (Objects.isNull(material)) {
            String message = "Material must not be null.";
            log.error(message);
            throw new NullPointerException(message);
        }
        var result = blocks.stream().filter(block -> block.getColor().equals(material)).toList();
        if (result.isEmpty()) {
            String message = String.format("There is no block with material: %s", material);
            log.warn(message);
            throw new NoSuchElementException(message);
        }
        return result;
    }

    @Override
    public int count() {
        return 0;
//        //TODO not implemented yet;
    }
}