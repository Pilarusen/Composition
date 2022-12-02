package com.pilar.composition;

import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompositionApplicationTests {


	private static final Block BLOCK1 = new BlockClass("color1", "material1");
	private static final Block BLOCK2 = new BlockClass("color2", "material2");

	private static final Composition COMPOSITE_BLOCK4 = new Composition("color4", "material4");
	private static final Composition COMPOSITE_BLOCK5 = new Composition("color5", "material5");
	private static final Composition COMPOSITE_BLOCK6 = new Composition("color6", "material6");

	Wall wall;
	LogCaptor logCaptor;
	@BeforeEach
	void setUp() {
		wall = new Wall();
		wall.addBlock(COMPOSITE_BLOCK4);
		wall.addBlock(BLOCK1);
	}
	@Test
	void findByColorEmptyStringProvidedShouldThrowException() {
		//given
		String emptyString = "";
		LogCaptor logCaptor = LogCaptor.forClass(Wall.class);
		//when
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> wall.findBlockByColor(emptyString));
		String expectedMessage = "Color must be a value.";
		//then
		assertEquals(exception.getMessage(), expectedMessage);
		assertThat(logCaptor.getLogs()).hasSize(1);
	}

	@Test
	void findByMaterialEmptyStringProvidedShouldThrowException() {
		//given
		String emptyString = "";
		LogCaptor logCaptor = LogCaptor.forClass(Wall.class);
		//when
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> wall.findBlocksByMaterial(emptyString));
		String expectedMessage = "Material must be a value.";
		//then
		assertEquals(exception.getMessage(), expectedMessage);
		assertThat(logCaptor.getLogs()).hasSize(1);
	}

	@Test
	void finByMaterialWhenThereIsNoBlockWithThatMaterialShouldThrowException() {
		//given
		String sampleInput = "sampleInput";
		LogCaptor logCaptor = LogCaptor.forClass(Wall.class);
		//when
		Exception exception = assertThrows(NoSuchElementException.class,
				() -> wall.findBlocksByMaterial(sampleInput));
		String expectedMessage = "There is no block with material:";
		//then
		assertThat(exception.getMessage()).contains(expectedMessage);
		assertThat(logCaptor.getLogs()).hasSize(1);
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
		assertThat(BLOCK1.equals(result));
		//TODO implement equals
	}

	@Test
	void addCompositionToTheSameCompositionShouldThrowError() {
		try {
			final Composition COMPOSITE_BLOCK30 = new Composition("color30", "material30");
			COMPOSITE_BLOCK30.addBlock(COMPOSITE_BLOCK30);

			wall.addBlock(COMPOSITE_BLOCK30);
			wall.findBlockByColor("color");
			//TODO implement equals and hashCode, then improve addBlock()
		} catch (Exception e) {
			System.out.println("exception");

		}
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
		String inputColor = "color2";
		Composition COMPOSITE_BLOCK20 = new Composition("color20", "material20");
		//when
		COMPOSITE_BLOCK4.addBlock(COMPOSITE_BLOCK20);
		COMPOSITE_BLOCK20.addBlock(BLOCK2);
		var result = wall.findBlockByColor(inputColor);
		//then
		assertThat(result.get().getColor()).isEqualTo(inputColor);
		assertEquals(result.get(), BLOCK2);
	}

	@Test
	void findCompositionByColorWhenCompositionIsInCompositionListShouldPass() {
		//given
		String inputColor = "color21";
		Composition COMPOSITE_BLOCK20 = new Composition("color20", "material20");
		Composition COMPOSITE_BLOCK21 = new Composition("color21", "material21");
		//when
		COMPOSITE_BLOCK4.addBlock(COMPOSITE_BLOCK20);
		COMPOSITE_BLOCK20.addBlock(COMPOSITE_BLOCK21);
		var result = wall.findBlockByColor(inputColor);
		//then
		assertThat(result.get().getColor()).isEqualTo(inputColor);
		assertEquals(result.get(), COMPOSITE_BLOCK21);
	}

	@Test
	void findBlockByMaterialWhenBlockIsInCompositionShouldPass() {
		//given
		String inputMaterial = "material22";
		Composition COMPOSITE_BLOCK20 = new Composition("color20", "material20");
		Block BLOCK22 = new BlockClass("color22", "material22");
		COMPOSITE_BLOCK20.addBlock(BLOCK22);
		wall.addBlock(COMPOSITE_BLOCK20);
		//when
		var result = wall.findBlocksByMaterial(inputMaterial);
		//then
		assertThat(result).hasSize(1);
		assertThat(result.stream().findFirst().get().getMaterial()).isEqualTo(inputMaterial);
		assertEquals(result.stream().findFirst().get(), BLOCK22);
	}

	@Test
	void findCompositionByMaterialWhenCompositionIsInCompositionListShouldPass() {
		//given
		String inputMaterial = "material22";
		Composition COMPOSITE_BLOCK20 = new Composition("color20", "material20");
		Composition COMPOSITE_BLOCK21 = new Composition("color21", "material21");
		Composition COMPOSITE_BLOCK22 = new Composition("color22", "material22");
		//when
		COMPOSITE_BLOCK20.addBlock(COMPOSITE_BLOCK21);
		COMPOSITE_BLOCK21.addBlock(COMPOSITE_BLOCK22);
		wall.addBlock(COMPOSITE_BLOCK20);
		var result = wall.findBlocksByMaterial(inputMaterial);
		//then
		assertThat(result).hasSize(1);
		assertThat(result.stream().findFirst().get().getMaterial()).isEqualTo(inputMaterial);
		assertEquals(result.stream().findFirst().get(), COMPOSITE_BLOCK22);
	}

	@Test
	void findCompositionsByMaterialWhenThreeCompositionsMeetsRequirementsShouldPass() {
		//given
		final Composition COMPOSITE_BLOCK10 = new Composition("color4", "material");
		final Composition COMPOSITE_BLOCK11 = new Composition("color5", "material");
		final Composition COMPOSITE_BLOCK12 = new Composition("color6", "material");
		String inputMaterial = "material";
		//when
		COMPOSITE_BLOCK4.addBlock(COMPOSITE_BLOCK5);
		COMPOSITE_BLOCK5.addBlock(BLOCK2);
		wall.addBlock(COMPOSITE_BLOCK10);
		wall.addBlock(COMPOSITE_BLOCK11);
		wall.addBlock(COMPOSITE_BLOCK12);
		var result = wall.findBlocksByMaterial(inputMaterial);
		//then
		assertThat(result).hasSize(3);
		assertThat(result).contains(COMPOSITE_BLOCK10);
		assertThat(result).contains(COMPOSITE_BLOCK11);
		assertThat(result).contains(COMPOSITE_BLOCK12);
	}








}
