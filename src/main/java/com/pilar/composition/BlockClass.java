package com.pilar.composition;

import java.util.Objects;
import java.util.stream.Stream;

public class BlockClass implements Block {

    private final String color;
    private final String material;

    public BlockClass(String color, String material) {
        this.color = color;
        this.material = material;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String getMaterial() {
        return material;
    }

    @Override
    public Stream flatByStream() {
        return Stream.of(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlockClass)) return false;
        BlockClass that = (BlockClass) o;
        return color.equals(that.color) && material.equals(that.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, material);
    }
}
