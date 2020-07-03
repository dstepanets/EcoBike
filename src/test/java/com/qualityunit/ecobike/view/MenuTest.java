package com.qualityunit.ecobike.view;

import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.BikeType;
import com.qualityunit.ecobike.model.FoldingBike;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MenuTest {
	@Mock
	private UserInput userInput;
	@InjectMocks
	private Menu menu;
	private FoldingBike bike;

	private final PrintStream backupOut = System.out;
	private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();

	@Before
	public void setUp() {
		bike = FoldingBike.getBuilder()
				.withBikeType(BikeType.FOLDING_BIKE)
				.withBrand("SkyBike")
				.withWeight(9000)
				.withHasLights(true)
				.withColor("pink")
				.withPrice(800)
				.withWheelSize(20)
				.withGearsNum(7)
				.build();
		System.setOut(new PrintStream(testOut));
	}

	@After
	public void tearDown() {
		System.setOut(new PrintStream(backupOut));
	}

	@Test
	public void constructBikeFromUserInput_ValidInput_ReturnsCorrectObject() {
		when(userInput.getLine(anyString())).thenReturn(bike.getBrand(), bike.getColor());
		when(userInput.getIntInRange(anyString(), anyInt(), anyInt(), anyBoolean()))
				.thenReturn(bike.getWeight(), bike.getPrice(), bike.getWheelSize(), bike.getGearsNum());
		when(userInput.getBoolean(anyString(), anyBoolean())).thenReturn(bike.getHasLights());

		AbstractBike result = menu.constructBikeFromUserInput(BikeType.FOLDING_BIKE, false);

		assertEquals(bike, result);
	}

	@Test
	public void constructBikeFromUserInput_SearchQuery_AllowsBlankFields() {
		when(userInput.getLine(anyString())).thenReturn(bike.getBrand(), "");
		when(userInput.getIntInRange(anyString(), anyInt(), anyInt(), anyBoolean()))
				.thenReturn(0, 0, 0, 0);
		when(userInput.getBoolean(anyString(), anyBoolean())).thenReturn(null);

		AbstractBike result = menu.constructBikeFromUserInput(BikeType.EBIKE, false);

		assertEquals(bike.getBrand(), result.getBrand());
		assertNull(result.getHasLights());
		assertTrue(result.getColor().isEmpty());
		assertEquals(0, result.getPrice());
	}

	@Test
	public void chooseBikeType_ReturnsCorrectType() {
		when(userInput.getIntInRange(anyString(), anyInt(), anyInt(), anyBoolean())).thenReturn(1, 2, 3);

		BikeType bikeType = menu.chooseBikeType();
		assertEquals(BikeType.FOLDING_BIKE, bikeType);
		bikeType = menu.chooseBikeType();
		assertEquals(BikeType.EBIKE, bikeType);
		bikeType = menu.chooseBikeType();
		assertEquals(BikeType.SPEEDELEC, bikeType);
	}
}