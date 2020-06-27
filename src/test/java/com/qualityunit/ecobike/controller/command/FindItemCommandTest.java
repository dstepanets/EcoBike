package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.BikeType;
import com.qualityunit.ecobike.model.ElectricBike;
import com.qualityunit.ecobike.model.Storage;
import com.qualityunit.ecobike.view.Menu;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FindItemCommandTest {
	@Mock
	private Storage storage;
	@Mock
	private Menu menu;
	@InjectMocks
	private FindItemCommand command;
	private AbstractBike bike;

	@Before
	public void setUp() {
		bike = ElectricBike.getBuilder()
				.withBikeType(BikeType.EBIKE)
				.withBrand("Mando")
				.withWeight(15000)
				.withHasLights(false)
				.withColor("grenadine")
				.withPrice(2995)
				.withMaxSpeed(25)
				.withBatteryCapacity(22000)
				.build();
	}

	@Test
	public void execute() {
		when(menu.chooseBikeType()).thenReturn(bike.getBikeType());
		when(menu.constructBikeFromUserInput(bike.getBikeType(), true)).thenReturn(bike);
		when(storage.findItem(bike)).thenReturn(Optional.ofNullable(bike));

		command.execute();

		verify(menu).chooseBikeType();
		verify(menu).constructBikeFromUserInput(bike.getBikeType(), true);
		verify(storage, timeout(100)).findItem(bike);
		verify(menu).displaySearchResult(Optional.ofNullable(bike));
		verifyNoMoreInteractions(menu, storage);
	}
}