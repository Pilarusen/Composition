package com.pilar.composition;

import java.util.stream.Stream;

interface Block {
    String getColor();

    String getMaterial();

    //Flat structure by flatMap
    Stream<Block> flatByStream();
}
