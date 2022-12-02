package com.pilar.composition;

import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompositionApplicationTests {


	private static final Block BLOCK1 = new BlockClass("color1", "material1");
	private static final Block BLOCK2 = new BlockClass("color2", "material2");
	private static final Block BLOCK3 = new BlockClass("color3", "material3");

	private static final Composition COMPOSITE_BLOCK1 = new Composition("color4", "material4");
	private static final CompositeBlock COMPOSITE_BLOCK2 = new Composition("color5", "material5");
	private static final CompositeBlock COMPOSITE_BLOCK3 = new Composition("color1", "material1");

	Wall wall;
	LogCaptor logCaptor;
	@BeforeEach
	void setUp() {
		wall = new Wall();
		COMPOSITE_BLOCK1.addBlock(BLOCK1);
		wall.addBlock(COMPOSITE_BLOCK1);
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
	void findByMaterialInputOKShouldReturnListOfOneBlock() {
		//TODO
//		//given
//		String correctInput = "color1";
//		//when
//		var result = wall.findBlocksByMaterial(correctInput);
//		//then
//		assertThat(result).hasSize(1).containsExactly(BLOCK1);
	}

	@Test
	void findByMaterialInputOKShouldReturnListOfTwoBlocks() {
//TODO
	}




}
