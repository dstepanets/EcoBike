package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.BikeType;
import com.qualityunit.ecobike.model.ElectricBike;
import com.qualityunit.ecobike.model.FoldingBike;
import com.qualityunit.ecobike.model.Storage;
import com.qualityunit.ecobike.view.Menu;
import com.qualityunit.ecobike.view.UserInput;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WriteToFileCommandTest {
	private List<AbstractBike> catalog;
	@Mock
	private Menu menu;
	@Mock
	private UserInput userInput;
	@Mock
	private Storage storage;
	private File testFile;
	private WriteToFileCommand command;

	private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
	private final PrintStream backupOut = System.out;
	private final ByteArrayOutputStream testErr = new ByteArrayOutputStream();
	private final PrintStream backupErr = System.err;

	@Rule
	public final TemporaryFolder folder = TemporaryFolder.builder().build();

	@Before
	public void setUp() throws Exception {
		catalog = buildTestCatalog();
		testFile = folder.newFile("testFile.txt");
		command = new WriteToFileCommand("", testFile.toPath(), menu, storage);
		System.setOut(new PrintStream(testOut));
		System.setErr(new PrintStream(testErr));
	}

	@After
	public void tearDown() {
		System.setOut(new PrintStream(backupOut));
		System.setErr(new PrintStream(backupErr));
	}

	@Test
	public void execute_WritesCatalogToFileCorrectly() throws IOException {
		when(storage.isUpdated()).thenReturn(true);
		when(menu.writeToFileOptions()).thenReturn(2);
		when(menu.getUserInput()).thenReturn(userInput);
		when(userInput.getBoolean(anyString(), anyBoolean())).thenReturn(true);
		when(storage.getCatalog()).thenReturn(catalog);

		command.execute();

		assertTrue(testFile.length() > 0);
		List<String> lines = Files.readAllLines(testFile.toPath());
		assertEquals(catalog.size(), lines.size());
		for (int i = 0; i < catalog.size(); i++) {
			assertEquals(catalog.get(i).toString(), lines.get(i));
		}
	}

	@Test
	public void execute_WrongPathFromUser_LoopsAgainAndSucceeds() throws IOException {
		when(storage.isUpdated()).thenReturn(true);
		when(menu.writeToFileOptions()).thenReturn(1);
		when(menu.getUserInput()).thenReturn(userInput, userInput);
		when(userInput.getLine(anyString())).thenReturn("", testFile.getPath());
		when(userInput.getBoolean(anyString(), anyBoolean())).thenReturn(true);
		when(storage.getCatalog()).thenReturn(catalog);

		command.execute();

		assertTrue(testFile.length() > 0);
		assertEquals(catalog.size(), Files.lines(testFile.toPath()).count());
		assertThat(testErr.toString(), containsString("Error on writing to file"));
	}

	private List<AbstractBike> buildTestCatalog() {
		List<AbstractBike> list = new ArrayList<>();
		list.add(FoldingBike.getBuilder()
				.withBikeType(BikeType.FOLDING_BIKE)
				.withBrand("SkyBike")
				.withWeight(12000)
				.withHasLights(true)
				.withColor("pink")
				.withPrice(1029)
				.withWheelSize(24)
				.withGearsNum(7)
				.build());
		list.add(ElectricBike.getBuilder()
				.withBikeType(BikeType.EBIKE)
				.withBrand("Mando")
				.withWeight(15000)
				.withHasLights(false)
				.withColor("grenadine")
				.withPrice(2995)
				.withMaxSpeed(25)
				.withBatteryCapacity(22000)
				.build());
		list.add(ElectricBike.getBuilder()
				.withBikeType(BikeType.SPEEDELEC)
				.withBrand("Gazelle")
				.withWeight(11000)
				.withHasLights(true)
				.withColor("grenadine")
				.withPrice(2000)
				.withMaxSpeed(50)
				.withBatteryCapacity(32000)
				.build());
		list.add(ElectricBike.getBuilder()
				.withBikeType(BikeType.EBIKE)
				.withBrand("Mando")
				.withWeight(11000)
				.withHasLights(true)
				.withColor("emerald")
				.withPrice(3100)
				.withMaxSpeed(30)
				.withBatteryCapacity(22000)
				.build());
		list.add(FoldingBike.getBuilder()
				.withBikeType(BikeType.FOLDING_BIKE)
				.withBrand("SkyBike")
				.withWeight(9000)
				.withHasLights(true)
				.withColor("pink")
				.withPrice(800)
				.withWheelSize(20)
				.withGearsNum(7)
				.build());
		return list;
	}
}