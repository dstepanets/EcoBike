package com.qualityunit.ecobike.model;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class StorageTest {
	private List<AbstractBike> catalog;
	private Storage storage;

	@Before
	public void setUp() throws IllegalAccessException, NoSuchFieldException {
//		using reflection, reset Singleton instance to assure independence of tests
		Field i = StorageImpl.class.getDeclaredField("instance");
		i.setAccessible(true);
		i.set(storage, null);
		storage = StorageImpl.getInstance();
//		reset collection
		buildTestCatalog();
//		set catalog to the test one
		Field c = StorageImpl.class.getDeclaredField("catalog");
		c.setAccessible(true);
		c.set(storage, this.catalog);
	}

	@After
	public void tearDown() {
	}

	@Test
	public void findItem_CompleteQueryItemExists_ReturnsCorrectItem() {
		AbstractBike query = storage.getCatalog().get(2);
		Optional<AbstractBike> result = storage.findItem(query);

		assertTrue(result.isPresent());
		assertEquals(storage.getCatalog().get(2), result.get());
	}

	@Test
	public void findItem_OnlyBrandQuery_ReturnsFirstItem() {
		AbstractBike query = ElectricBike.getBuilder()
				.withBikeType(BikeType.EBIKE).withBrand("Mando").withColor("").build();
		Optional<AbstractBike> result = storage.findItem(query);

		assertTrue(result.isPresent());
		assertEquals(storage.getCatalog().get(1), result.get());
		assertNotEquals(storage.getCatalog().get(3), result.get());
	}

	@Test
	public void findItem_NoSuchItem_ReturnsEmptyOptional() {
		AbstractBike query = ElectricBike.getBuilder()
				.withBikeType(BikeType.EBIKE)
				.withBrand("Mando")
				.withColor("")
				.withMaxSpeed(777)
				.build();

		Optional<AbstractBike> result = storage.findItem(query);

		assertFalse(result.isPresent());
	}

	@Test
	public void findItem_ByPrice_ReturnsCorrectItem() {
		AbstractBike query = FoldingBike.getBuilder()
				.withBikeType(BikeType.FOLDING_BIKE)
				.withBrand("SkyBike")
				.withColor("")
				.withPrice(800)
				.build();

		Optional<AbstractBike> result = storage.findItem(query);

		assertTrue(result.isPresent());
		assertEquals(result.get(), storage.getCatalog().get(4));
		assertNotEquals(result.get(), storage.getCatalog().get(0));
	}

	@Test
	public void addBike_NewBikeAdded() {
		assertFalse(storage.isUpdated());
		int initialSize = storage.getCatalog().size();
		AbstractBike bike = ElectricBike.getBuilder()
				.withBikeType(BikeType.SPEEDELEC)
				.withBrand("NewBrand")
				.withColor("")
				.build();

		storage.addBike(bike);

		assertTrue(storage.isUpdated());
		assertEquals(initialSize + 1, storage.getCatalog().size());

	}

	@Test
	public void addBike_Null_Ignored() {
		assertFalse(storage.isUpdated());
		int initialSize = storage.getCatalog().size();

		storage.addBike(null);

		assertFalse(storage.isUpdated());
		assertEquals(initialSize, storage.getCatalog().size());
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