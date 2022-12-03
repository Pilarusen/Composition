package com.pilar.composition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompositionApplicationTests {


    private static final Block BLOCK1 = new BlockClass("color1", "material1");
    private static final Block BLOCK2 = new BlockClass("color2", "material2");
    private static final Composition COMPOSITE_BLOCK3 = new Composition("color3", "material3");
    private static final Composition COMPOSITE_BLOCK4 = new Composition("color4", "material4");

    Wall wall;

    @BeforeEach
    void setUp() {
        wall = new Wall();
        wall.addBlock(COMPOSITE_BLOCK4);
        wall.addBlock(BLOCK1);
        wall.addBlock(BLOCK2);
        wall.addBlock(COMPOSITE_BLOCK3);
    }

    @Test
    void findByColorEmptyStringProvidedShouldThrowException() {
        //given
        String emptyString = "";
        //when
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> wall.findBlockByColor(emptyString));
        String expectedMessage = "Input must be a value.";
        //then
        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test
    void findByMaterialEmptyStringProvidedShouldThrowException() {
        //given
        String emptyString = "";
        //when
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> wall.findBlocksByMaterial(emptyString));
        String expectedMessage = "Input must be a value.";
        //then
        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test
    void finByMaterialWhenThereIsNoBlockWithThatMaterialShouldThrowException() {
        //given
        String sampleInput = "sampleInput";
        //when
        Exception exception = assertThrows(NoSuchElementException.class,
                () -> wall.findBlocksByMaterial(sampleInput));
        String expectedMessage = "There is no block with material:";
        //then
        assertThat(exception.getMessage()).contains(expectedMessage);
    }

    @Test
    void findByColorWhenNoneMatchesShouldReturnEmptyOptional() {
        //given
        String sampleInput = "sampleInput";
        //when
        var result = wall.findBlockByColor(sampleInput);
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    void findByColorInputOKShouldReturnOptionalBlock() {
        //given
        String correctInput = "color1";
        //when
        var result = wall.findBlockByColor(correctInput);
        //then
        assertTrue(result.isPresent());
        assertEquals(BLOCK1, result.get());
    }

    @Test
    void addCompositionToTheSameCompositionShouldThrowException() {
        //given
        final Composition COMPOSITE_BLOCK30 = new Composition("color30", "material30");
        final Composition COMPOSITE_BLOCK31 = new Composition("color30", "material30");
        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> COMPOSITE_BLOCK30.addBlock(COMPOSITE_BLOCK31));
        String expectedMessage = "Can not add composition to itself.";
        //then
        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test
    void addCompositionToTheSameCompositionWithTheSameBlocksShouldThrowException() {
        //given
        final Composition compositeBlock30 = new Composition("color30", "material30");
        final Composition compositeBlock31 = new Composition("color30", "material30");
        final Composition compositeBlock305 = new Composition("color30_5", "material30_5");
        //when
        compositeBlock30.addBlock(compositeBlock305);
        compositeBlock31.addBlock(compositeBlock305);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> compositeBlock30.addBlock(compositeBlock31));
        String expectedMessage = "Can not add composition to itself.";
        //then
        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test
    void addCompositionComposition__WhenCompositionIsAlreadyInStructureShouldThrowException() {
        //given
        final Composition compositeBlock30 = new Composition("color30", "material30");
        final Composition compositeBlock31 = new Composition("color31", "material31");
        final Composition compositeBlock32 = new Composition("color32", "material32");
        //when
        compositeBlock30.addBlock(compositeBlock31);
        compositeBlock31.addBlock(compositeBlock32);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> compositeBlock32.addBlock(compositeBlock30));
        String expectedMessage = "Block you try to add is already in structure.";
        //then
        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test
    void addCompositionToTheSameCompositionWithDifferentBlocksShouldPass() {
        //given
        final Composition compositeBlock30 = new Composition("color30", "material30");
        final Composition compositeBlock31 = new Composition("color30", "material30");
        final Composition compositeBlock32 = new Composition("color32", "material32");
        final Composition compositeBlock33 = new Composition("color33", "material33");
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
        final Composition compositeBlock30 = new Composition("color30", "material30");
        final Composition compositeBlock31 = new Composition("color30", "material30");
        final Composition compositeBlock32 = new Composition("color32", "material32");
        final Composition compositeBlock33 = new Composition("color33", "material33");
        //when
        compositeBlock30.addBlock(compositeBlock32);
        compositeBlock31.addBlock(compositeBlock33);
        wall.addBlock(compositeBlock30);
        wall.addBlock(compositeBlock31);
        //then
        assertThat(compositeBlock30.getBlocks()).hasSize(1).containsExactly(compositeBlock32);
        assertThat(compositeBlock31.getBlocks()).hasSize(1).containsExactly(compositeBlock33);
        var result = wall.findBlocksByMaterial("material30");
        assertThat(result).hasSize(2);
        assertThat(result).contains(compositeBlock30).contains(compositeBlock31);
    }

    @Test
    void findByMaterialInputOKShouldReturnListOfOneBlock() {
        //given
        String correctInput = "material1";
        //when
        var result = wall.findBlocksByMaterial(correctInput);
        //then
        assertThat(result).hasSize(1).containsExactly(BLOCK1);
    }

    @Test
    void findByMaterialInputOKShouldReturnListOfTwoBlocks() {
        //given
        String correctInput = "material1";
        final Block block3 = new BlockClass("color3", "material1");
        //when
        wall.addBlock(block3);
        var result = wall.findBlocksByMaterial(correctInput);
        //then
        assertThat(result).hasSize(2);
        assertTrue(result.contains(BLOCK1));
        assertTrue(result.contains(block3));
    }

    @Test
    void findBlockByColorWhenBlockIsInCompositionListShouldPass() {
        //given
        String inputColor = "color21";
        Composition CompositeBlock = new Composition("color", "material");
        Composition CompositeBlock20 = new Composition("color20", "material20");
        Composition CompositeBlock21 = new Composition("color21", "material21");
        //when
        CompositeBlock.addBlock(CompositeBlock20);
        CompositeBlock20.addBlock(CompositeBlock21);
        wall.addBlock(CompositeBlock);
        var result = wall.findBlockByColor(inputColor);
        //then
        assertThat(result.get().getColor()).isEqualTo(inputColor);
        assertEquals(result.get(), CompositeBlock21);
    }

    @Test
    void findCompositionByColorWhenCompositionIsInCompositionListShouldPass() {
        //given
        String inputColor = "color21";
        Composition compositeBlock = new Composition("color", "material");
        Composition compositeBlock20 = new Composition("color20", "material20");
        Composition compositeBlock21 = new Composition("color21", "material21");
        //when
        compositeBlock.addBlock(compositeBlock20);
        compositeBlock20.addBlock(compositeBlock21);
        wall.addBlock(compositeBlock);
        var result = wall.findBlockByColor(inputColor);
        //then
        assertThat(result.get().getColor()).isEqualTo(inputColor);
        assertEquals(result.get(), compositeBlock21);
    }

    @Test
    void findBlockByMaterialWhenBlockIsInCompositionShouldPass() {
        //given
        String inputMaterial = "material22";
        Composition CompositeBlock20 = new Composition("color20", "material20");
        Block block22 = new BlockClass("color22", "material22");
        CompositeBlock20.addBlock(block22);
        wall.addBlock(CompositeBlock20);
        //when
        var result = wall.findBlocksByMaterial(inputMaterial);
        //then
        assertThat(result).hasSize(1);
        assertThat(result.stream().findFirst().get().getMaterial()).isEqualTo(inputMaterial);
        assertEquals(result.stream().findFirst().get(), block22);
    }

    @Test
    void findCompositionByMaterialWhenCompositionIsInCompositionListShouldPass() {
        //given
        String inputMaterial = "material22";
        Composition CompositeBlock20 = new Composition("color20", "material20");
        Composition compositeBlock21 = new Composition("color21", "material21");
        Composition compositeBlock22 = new Composition("color22", "material22");
        //when
        CompositeBlock20.addBlock(compositeBlock21);
        compositeBlock21.addBlock(compositeBlock22);
        wall.addBlock(CompositeBlock20);
        var result = wall.findBlocksByMaterial(inputMaterial);
        //then
        assertThat(result).hasSize(1);
        assertThat(result.stream().findFirst().get().getMaterial()).isEqualTo(inputMaterial);
        assertEquals(result.stream().findFirst().get(), compositeBlock22);
    }

    @Test
    void findCompositionsByMaterialWhenThreeCompositionsMeetsRequirementsShouldPass() {
        //given
        final Composition compositeBlock10 = new Composition("color4", "material");
        final Composition compositeBlock11 = new Composition("color5", "material");
        final Composition compositeBlock12 = new Composition("color6", "material");
        String inputMaterial = "material";
        //when
        wall.addBlock(compositeBlock10);
        wall.addBlock(compositeBlock11);
        wall.addBlock(compositeBlock12);
        var result = wall.findBlocksByMaterial(inputMaterial);
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
        final Composition compositeBlock10 = new Composition("color4", "material");
        final Composition compositeBlock11 = new Composition("color5", "material");
        final Composition compositeBlock12 = new Composition(colorInput, "material");
        String inputMaterial = "material";
        //when
        compositeBlock10.addBlock(compositeBlock11);
        compositeBlock11.addBlock(compositeBlock12);
        wall.addBlock(compositeBlock10);
        wall.addBlock(compositeBlock11);
        wall.addBlock(compositeBlock12);
        wall.addBlock(compositeBlock12);
        var result = wall.findBlocksByMaterial(inputMaterial).stream()
                .filter(block -> block.getColor().equals(colorInput)).toList();
        //then
        var filteredResult = result.stream().filter(block -> block.getColor().equals(colorInput));
        assertThat(filteredResult).hasSize(4);
    }

    @Test
    void countBlockNumberComplexStructureShouldBeCorrect() {
        //given
        Composition compositeBlock20 = new Composition("color20", "material20");
        Composition compositeBlock21 = new Composition("color21", "material21");
        Composition compositeBlock22 = new Composition("color22", "material22");
        //when
        compositeBlock20.addBlock(compositeBlock21);
        compositeBlock21.addBlock(compositeBlock22);
        wall.addBlock(compositeBlock20);
        wall.addBlock(compositeBlock21);
        //@BeforeEach count = 4;
        int expected = 9;
        var result = wall.count();
        //then
        assertEquals(expected, result);
    }
}
