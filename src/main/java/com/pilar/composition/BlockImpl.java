package com.pilar.composition;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class BlockImpl implements Block {

    private final String color;
    private final String material;

    @Override
    public Stream<Block> toStream() {
        return Stream.of(this);
    }
}
