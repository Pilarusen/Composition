package com.pilar.composition;

import java.util.stream.Stream;

interface Block {
    String getColor();

    String getMaterial();

    Stream<Block> flatByStream();
}
