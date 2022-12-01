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
        return null;
        //TODO not implemented yet;
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        return null;
        //TODO not implemented yet;
    }

    @Override
    public int count() {
        return 0;
        //TODO not implemented yet;
    }
}