package com.pilar.composition;

import java.util.List;

interface CompositeBlock extends Block {
    List<Block> getBlocks();
}