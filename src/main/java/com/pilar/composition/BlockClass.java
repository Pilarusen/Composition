package com.pilar.composition;

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
}
