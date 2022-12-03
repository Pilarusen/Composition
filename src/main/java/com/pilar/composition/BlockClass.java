package com.pilar.composition;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class BlockClass implements Block {

    private final String color;
    private final String material;

    @Override
    public Stream<Block> flatByStream() {
        return Stream.of(this);
    }
}
