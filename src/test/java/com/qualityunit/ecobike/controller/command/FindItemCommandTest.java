package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.BikeType;
import com.qualityunit.ecobike.model.ElectricBike;
import com.qualityunit.ecobike.model.FoldingBike;
import com.qualityunit.ecobike.view.Menu;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class FindItemCommandTest {
	private List<AbstractBike> catalog;
	@Mock
	private Menu menu;
	@InjectMocks
	private FindItemCommand command;

	@Before
	public void setUp() throws Exception {
		buildTestCatalog();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void execute() {
	}

	private void buildTestCatalog() {
		catalog = new ArrayList<>();
		catalog.add(FoldingBike.getBuilder()
				.withBikeType(BikeType.FOLDING_BIKE)
				.withBrand("SkyBike")
				.withWeight(12000)
				.withHasLights(true)
				.withColor("pink")
				.withPrice(1029)
				.withWheelSize(24)
				.withGearsNum(7)
				.build());
		catalog.add(ElectricBike.getBuilder()
				.withBikeType(BikeType.EBIKE)
				.withBrand("Mando")
				.withWeight(15000)
				.withHasLights(false)
				.withColor("grenadine")
				.withPrice(2995)
				.withMaxSpeed(25)
				.withBatteryCapacity(22000)
				.build());
		catalog.add(ElectricBike.getBuilder()
				.withBikeType(BikeType.SPEEDELEC)
				.withBrand("Gazelle")
				.withWeight(11000)
				.withHasLights(true)
				.withColor("grenadine")
				.withPrice(2000)
				.withMaxSpeed(50)
				.withBatteryCapacity(32000)
				.build());
		catalog.add(ElectricBike.getBuilder()
				.withBikeType(BikeType.EBIKE)
				.withBrand("Mando")
				.withWeight(11000)
				.withHasLights(true)
				.withColor("emerald")
				.withPrice(3100)
				.withMaxSpeed(30)
				.withBatteryCapacity(22000)
				.build());
		catalog.add(FoldingBike.getBuilder()
				.withBikeType(BikeType.FOLDING_BIKE)
				.withBrand("SkyBike")
				.withWeight(9000)
				.withHasLights(true)
				.withColor("pink")
				.withPrice(800)
				.withWheelSize(20)
				.withGearsNum(7)
				.build());
	}
}