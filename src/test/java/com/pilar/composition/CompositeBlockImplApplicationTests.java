package com.pilar.composition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompositeBlockImplApplicationTests {
// gradle add version for apache commons
    private static final Block BLOCK1 = new BlockImpl("color1", "material1");
    private static final Block BLOCK2 = new BlockImpl("color2", "material2");
    private static final CompositeBlockImpl COMPOSITE_BLOCK3 = new CompositeBlockImpl("color3", "material3");
    private static final CompositeBlockImpl COMPOSITE_BLOCK4 = new CompositeBlockImpl("color4", "material4");

    private Wall wall;

    @BeforeEach
    void setUp() {
        wall = new Wall();
        wall.addBlock(BLOCK1);
        wall.addBlock(BLOCK2);
        wall.addBlock(COMPOSITE_BLOCK3);
        wall.addBlock(COMPOSITE_BLOCK4);
    }

    @Test
    void findByColorEmptyStringProvidedShouldThrowIllegalArgumentException() {
        //given
        String emptyString = "";
        String expectedMessage = "Input must not be null or empty.";
        //when
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> wall.findBlockByColor(emptyString));
        //then
        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test
    void findByMaterialEmptyStringProvidedShouldThrowException() {
        //given
        String emptyString = "";
        String expectedMessage = "Input must not be null or empty.";
        //when
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> wall.findBlocksByMaterial(emptyString));
        //then
        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test
    void finByMaterialWhenThereIsNoBlockWithThatMaterialShouldThrowException() {
        //given
        String sampleInput = "sampleInput";
        String expectedMessage = "There is no block with material:";
        //when
        Exception exception = assertThrows(NoSuchElementException.class,
                () -> wall.findBlocksByMaterial(sampleInput));
        //then
        assertThat(exception.getMessage()).contains(expectedMessage);
    }

    @Test
    void findByColorWhenNoneMatchesShouldReturnEmptyOptional() {
        //given
        String sampleInput = "sampleInput";
        //when
        Optional<Block> result = wall.findBlockByColor(sampleInput);
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    void findByColorInputOKShouldReturnOptionalBlock() {
        //given
        String correctInput = "color1";
        //when
        Optional<Block> result = wall.findBlockByColor(correctInput);
        //then
        assertTrue(result.isPresent());
        assertEquals(BLOCK1, result.get());
    }

    @Test
    void addCompositionToTheSameCompositionShouldThrowException() {
        //given
        String expectedMessage = "Can not add composition to itself.";
        final CompositeBlockImpl COMPOSITE_BLOCK30 = new CompositeBlockImpl("color30", "material30");
        final CompositeBlockImpl COMPOSITE_BLOCK31 = new CompositeBlockImpl("color30", "material30");
        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> COMPOSITE_BLOCK30.addBlock(COMPOSITE_BLOCK31));
        //then
        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test
    void addCompositionToTheSameCompositionWithTheSameBlocksShouldThrowException() {
        //given
        String expectedMessage = "Can not add composition to itself.";
        final CompositeBlockImpl compositeBlock30 = new CompositeBlockImpl("color30", "material30");
        final CompositeBlockImpl compositeBlock31 = new CompositeBlockImpl("color30", "material30");
        final CompositeBlockImpl compositeBlock305 = new CompositeBlockImpl("color30_5", "material30_5");
        //when
        compositeBlock30.addBlock(compositeBlock305);
        compositeBlock31.addBlock(compositeBlock305);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> compositeBlock30.addBlock(compositeBlock31));
        //then
        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test
    void addCompositionComposition__WhenCompositionIsAlreadyInStructureShouldThrowException() {
        //given
        String expectedMessage = "Block you try to add is already in structure.";
        final CompositeBlockImpl compositeBlock30 = new CompositeBlockImpl("color30", "material30");
        final CompositeBlockImpl compositeBlock31 = new CompositeBlockImpl("color31", "material31");
        final CompositeBlockImpl compositeBlock32 = new CompositeBlockImpl("color32", "material32");
        //when
        compositeBlock30.addBlock(compositeBlock31);
        compositeBlock31.addBlock(compositeBlock32);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> compositeBlock32.addBlock(compositeBlock30));
        //then
        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test
    void addCompositionToTheSameCompositionWithDifferentBlocksShouldPass() {
        //given
        final CompositeBlockImpl compositeBlock30 = new CompositeBlockImpl("color30", "material30");
        final CompositeBlockImpl compositeBlock31 = new CompositeBlockImpl("color30", "material30");
        final CompositeBlockImpl compositeBlock32 = new CompositeBlockImpl("color32", "material32");
        final CompositeBlockImpl compositeBlock33 = new CompositeBlockImpl("color33", "material33");
        //when
        compositeBlock30.addBlock(compositeBlock32);
        compositeBlock31.addBlock(compositeBlock33);
        wall.addBlock(compositeBlock30);
        wall.addBlock(compositeBlock31);
        //then
        assertThat(compositeBlock30.getBlocks()).hasSize(1).containsExactly(compositeBlock32);
        assertThat(compositeBlock31.getBlocks()).hasSize(1).containsExactly(compositeBlock33);
    }

    @Test
    void addCompositionToTheSameCompositionWithDifferentBlocksShouldPass__ThenFindByMaterialReturnsListOfTwoBlocks() {
        //given
        final CompositeBlockImpl compositeBlock30 = new CompositeBlockImpl("color30", "material30");
        final CompositeBlockImpl compositeBlock31 = new CompositeBlockImpl("color30", "material30");
        final CompositeBlockImpl compositeBlock32 = new CompositeBlockImpl("color32", "material32");
        final CompositeBlockImpl compositeBlock33 = new CompositeBlockImpl("color33", "material33");
        //when
        compositeBlock30.addBlock(compositeBlock32);
        compositeBlock31.addBlock(compositeBlock33);
        wall.addBlock(compositeBlock30);
        wall.addBlock(compositeBlock31);
        //then
        assertThat(compositeBlock30.getBlocks()).hasSize(1).containsExactly(compositeBlock32);
        assertThat(compositeBlock31.getBlocks()).hasSize(1).containsExactly(compositeBlock33);
        List<Block> result = wall.findBlocksByMaterial("material30");
        assertThat(result).hasSize(2);
        assertThat(result).contains(compositeBlock30).contains(compositeBlock31);
    }

    @Test
    void findByMaterialInputOKShouldReturnListOfOneBlock() {
        //given
        String correctInput = "material1";
        //when
        List<Block> result = wall.findBlocksByMaterial(correctInput);
        //then
        assertThat(result).hasSize(1).containsExactly(BLOCK1);
    }

    @Test
    void findByMaterialInputOKShouldReturnListOfTwoBlocks() {
        //given
        String correctInput = "material1";
        final Block block3 = new BlockImpl("color3", "material1");
        //when
        wall.addBlock(block3);
        List<Block> result = wall.findBlocksByMaterial(correctInput);
        //then
        assertThat(result).hasSize(2);
        assertTrue(result.contains(BLOCK1));
        assertTrue(result.contains(block3));
    }

    @Test
    void findBlockByColorWhenBlockIsInCompositionListShouldPass() {
        //given
        String inputColor = "color21";
        CompositeBlockImpl CompositeBlock = new CompositeBlockImpl("color", "material");
        CompositeBlockImpl CompositeBlock20 = new CompositeBlockImpl("color20", "material20");
        CompositeBlockImpl CompositeBlock21 = new CompositeBlockImpl("color21", "material21");
        //when
        CompositeBlock.addBlock(CompositeBlock20);
        CompositeBlock20.addBlock(CompositeBlock21);
        wall.addBlock(CompositeBlock);
        Optional<Block> result = wall.findBlockByColor(inputColor);
        //then
        assertTrue(result.isPresent());
        assertThat(result.get().getColor()).isEqualTo(inputColor);
        assertEquals(result.get(), CompositeBlock21);
    }

    @Test
    void findCompositionByColorWhenCompositionIsInCompositionListShouldPass() {
        //given
        String inputColor = "color21";
        CompositeBlockImpl compositeBlock = new CompositeBlockImpl("color", "material");
        CompositeBlockImpl compositeBlock20 = new CompositeBlockImpl("color20", "material20");
        CompositeBlockImpl compositeBlock21 = new CompositeBlockImpl("color21", "material21");
        //when
        compositeBlock.addBlock(compositeBlock20);
        compositeBlock20.addBlock(compositeBlock21);
        wall.addBlock(compositeBlock);
        Optional<Block> result = wall.findBlockByColor(inputColor);
        //then
        assertTrue(result.isPresent());
        assertThat(result.get().getColor()).isEqualTo(inputColor);
        assertEquals(result.get(), compositeBlock21);
    }

    @Test
    void findBlockByMaterialWhenBlockIsInCompositionShouldPass() {
        //given
        String inputMaterial = "material22";
        CompositeBlockImpl CompositeBlock20 = new CompositeBlockImpl("color20", "material20");
        Block block22 = new BlockImpl("color22", "material22");
        CompositeBlock20.addBlock(block22);
        wall.addBlock(CompositeBlock20);
        //when
        List<Block> result = wall.findBlocksByMaterial(inputMaterial);
        //then
        assertThat(result).hasSize(1);
        assertTrue(result.stream().findFirst().isPresent());
        assertThat(result.stream().findFirst().get().getMaterial()).isEqualTo(inputMaterial);
        assertEquals(result.stream().findFirst().get(), block22);
    }

    @Test
    void findCompositionByMaterialWhenCompositionIsInCompositionListShouldPass() {
        //given
        String inputMaterial = "material22";
        CompositeBlockImpl CompositeBlock20 = new CompositeBlockImpl("color20", "material20");
        CompositeBlockImpl compositeBlock21 = new CompositeBlockImpl("color21", "material21");
        CompositeBlockImpl compositeBlock22 = new CompositeBlockImpl("color22", "material22");
        //when
        CompositeBlock20.addBlock(compositeBlock21);
        compositeBlock21.addBlock(compositeBlock22);
        wall.addBlock(CompositeBlock20);
        List<Block> result = wall.findBlocksByMaterial(inputMaterial);
        //then
        assertThat(result).hasSize(1);
        assertTrue(result.stream().findFirst().isPresent());
        assertThat(result.stream().findFirst().get().getMaterial()).isEqualTo(inputMaterial);
        assertEquals(result.stream().findFirst().get(), compositeBlock22);
    }

    @Test
    void findCompositionsByMaterialWhenThreeCompositionsMeetsRequirementsShouldPass() {
        //given
        String inputMaterial = "material";
        final CompositeBlockImpl compositeBlock10 = new CompositeBlockImpl("color4", "material");
        final CompositeBlockImpl compositeBlock11 = new CompositeBlockImpl("color5", "material");
        final CompositeBlockImpl compositeBlock12 = new CompositeBlockImpl("color6", "material");
        //when
        wall.addBlock(compositeBlock10);
        wall.addBlock(compositeBlock11);
        wall.addBlock(compositeBlock12);
        List<Block> result = wall.findBlocksByMaterial(inputMaterial);
        //then
        assertThat(result).hasSize(3);
        assertThat(result).contains(compositeBlock10);
        assertThat(result).contains(compositeBlock11);
        assertThat(result).contains(compositeBlock12);
    }

    @Test
    void findCompositionsByMaterialWhenMixedStructureShouldReturnListOfThreeDuplicates() {
        //given
        String colorInput = "color12";
        String inputMaterial = "material";
        final CompositeBlockImpl compositeBlock10 = new CompositeBlockImpl("color4", "material");
        final CompositeBlockImpl compositeBlock11 = new CompositeBlockImpl("color5", "material");
        final CompositeBlockImpl compositeBlock12 = new CompositeBlockImpl(colorInput, "material");
        //when
        compositeBlock10.addBlock(compositeBlock11);
        compositeBlock11.addBlock(compositeBlock12);
        wall.addBlock(compositeBlock10);
        wall.addBlock(compositeBlock11);
        wall.addBlock(compositeBlock12);
        wall.addBlock(compositeBlock12);
        List<Block> result = wall.findBlocksByMaterial(inputMaterial).stream()
                .filter(block -> block.getColor().equals(colorInput)).toList();
        //then
        Stream<Block> filteredResult = result.stream().filter(block -> block.getColor().equals(colorInput));
        assertThat(filteredResult).hasSize(4);
    }

    @Test
    void countBlockNumberComplexStructureShouldBeCorrect() {
        //given
        CompositeBlockImpl compositeBlock20 = new CompositeBlockImpl("color20", "material20");
        CompositeBlockImpl compositeBlock21 = new CompositeBlockImpl("color21", "material21");
        CompositeBlockImpl compositeBlock22 = new CompositeBlockImpl("color22", "material22");
        //when
        compositeBlock20.addBlock(compositeBlock21);
        compositeBlock21.addBlock(compositeBlock22);
        wall.addBlock(compositeBlock20);
        wall.addBlock(compositeBlock21);
        //@BeforeEach count = 4;
        int expected = 9;
        int result = wall.count();
        //then
        assertEquals(expected, result);
    }
}
