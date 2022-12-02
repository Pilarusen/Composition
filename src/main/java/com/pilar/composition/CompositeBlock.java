package com.pilar.composition;

import java.util.List;

interface CompositeBlock extends Block {
    List<Block> getBlocks();

    //TODO
    /**
     * During this task I noticed when add Composition to the same composition there is StackOverflow error!
     * Try to prevent from adding Composition to itself.
     * */
}