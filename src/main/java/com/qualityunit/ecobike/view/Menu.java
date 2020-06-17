package com.qualityunit.ecobike.view;

import com.qualityunit.ecobike.controller.command.AddItemCommand;
import com.qualityunit.ecobike.controller.command.Executable;
import com.qualityunit.ecobike.controller.command.FindItemCommand;
import com.qualityunit.ecobike.controller.command.MenuCommand;
import com.qualityunit.ecobike.controller.command.ShowCatalogCommand;
import com.qualityunit.ecobike.controller.command.StopProgramCommand;
import com.qualityunit.ecobike.controller.command.WriteToFileCommand;
import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.BikeType;
import com.qualityunit.ecobike.model.CatalogPage;
import com.qualityunit.ecobike.model.ElectricBike;
import com.qualityunit.ecobike.model.FoldingBike;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static com.qualityunit.ecobike.view.UserInput.getBoolean;
import static com.qualityunit.ecobike.view.UserInput.getIntInRange;
import static com.qualityunit.ecobike.view.UserInput.getLine;
import static com.qualityunit.ecobike.view.UserInput.getLineAllowEmpty;
import static java.lang.System.out;

public class Menu {
	private static final String SEPARATOR = "- - - - - - - - - - - - - - - - - - - - - - -";
	private static final String ENTER_A_NUMBER = "Enter a number:";

	private static Menu menuInstance;
	private final Map<Integer, MenuCommand> numToCommand;
	private final String menuText;

	private Menu() {
		numToCommand = new LinkedHashMap<>();
		numToCommand.put(1, new ShowCatalogCommand("Show the entire EcoBike catalog"));
		numToCommand.put(2, new AddItemCommand("Add a new folding bike", BikeType.FOLDING_BIKE));
		numToCommand.put(3, new AddItemCommand("Add a new speedelec", BikeType.SPEEDELEC));
		numToCommand.put(4, new AddItemCommand("Add a new e-bike", BikeType.EBIKE));
		numToCommand.put(5, new FindItemCommand("Find the first item of a particular brand"));
		numToCommand.put(6, new WriteToFileCommand("Write to file"));
		numToCommand.put(7, new StopProgramCommand("Stop the program"));

		menuText = buildMenuText();
	}

	private String buildMenuText() {
		StringBuilder sb = new StringBuilder();
		sb.append(SEPARATOR).append("\n").append("Please make your choice:\n");
		numToCommand.forEach((k, c) -> sb.append(k).append(" - ").append(c.getDescription()).append("\n"));
		sb.append(SEPARATOR);
		return sb.toString();
	}

	public static synchronized Menu getInstance() {
		if (menuInstance == null) {
			menuInstance = new Menu();
		}
		return menuInstance;
	}

	public Executable getCommandFromUser() {
		out.println(menuText);
		int choice = getIntInRange(ENTER_A_NUMBER, 1, numToCommand.size(), false);
		MenuCommand command = numToCommand.get(choice);
		out.println("--> " + command.getDescription());
		return command;
	}

	public String displayCatalogPage(CatalogPage page) {
		String pageStats = String.format("---[Page: %d/%d | Items: %d-%d/%d]---%n",
				page.getCurrentPage(), page.getTotalPages(),
				page.getFirstItemNum(), page.getLastItemNum(),
				page.getTotalItems());

		StringBuilder sb = new StringBuilder();
		sb.append("\n").append(pageStats);
		page.getItems().forEach(i -> sb.append(i.toDisplayFormatString()).append("\n"));
		sb.append(pageStats);
		out.println(sb.toString());

		String prompt =
				"('N' - next page | 'P' - previous page | 'M' - back to menu | <number> - go to this page)\n" +
						"Your command: ";
		return getLine(prompt);
	}

	public AbstractBike constructBikeFromUserInput(BikeType bikeType, boolean isSearchQuery) {
		if (isSearchQuery) {
			out.println("NEW SEARCH QUERY");
		} else {
			out.println("NEW " + bikeType.toString());
		}

		String brandName = getLine("Enter its brand name:");
		int weight = getIntInRange("Enter the weight of the bike (in grams):", 0, Integer.MAX_VALUE, isSearchQuery);
		Boolean hasLights = getBoolean("Does it have front and back lights?", isSearchQuery);
		String color = isSearchQuery ? getLineAllowEmpty("Enter the color:") : getLine("Enter the color:");
		int price = getIntInRange("Enter the price:", 0, Integer.MAX_VALUE, isSearchQuery);

		AbstractBike bike = null;
		switch (bikeType) {
			case FOLDING_BIKE:
				bike = FoldingBike.getBuilder()
						.withBikeType(bikeType)
						.withBrand(brandName)
						.withWeight(weight)
						.withHasLights(hasLights)
						.withColor(color)
						.withPrice(price)
						.withWheelSize(getIntInRange("Enter the size of the wheels (in inches):",
								0, Integer.MAX_VALUE, isSearchQuery))
						.withGearsNum(getIntInRange("Enter the number of gears:",
								0, Integer.MAX_VALUE, isSearchQuery))
						.build();
				break;
			case EBIKE:
			case SPEEDELEC:
				bike = ElectricBike.getBuilder()
						.withBikeType(bikeType)
						.withBrand(brandName)
						.withWeight(weight)
						.withHasLights(hasLights)
						.withColor(color)
						.withPrice(price)
						.withMaxSpeed(getIntInRange("Enter the maximum speed (in km/h):",
								0, Integer.MAX_VALUE, isSearchQuery))
						.withBatteryCapacity(getIntInRange("Enter the battery capacity (in mAh):",
								0, Integer.MAX_VALUE, isSearchQuery))
						.build();
				break;
		}
		return bike;
	}

	public BikeType chooseBikeType() {
		BikeType[] bikeTypes = BikeType.values();
		StringBuilder sb = new StringBuilder();
		sb.append("Choose the bike type:\n");
		Arrays.stream(bikeTypes).forEach(t -> sb.append(t.ordinal() + 1).append(" - ").append(t.toString()).append("\n"));
		out.println(sb.toString());
		int i = getIntInRange(ENTER_A_NUMBER, 1, BikeType.values().length, false);
		return bikeTypes[i - 1];
	}

	public void displaySearchResult(Optional<AbstractBike> result) {
		String s = "= = = = = SEARCH RESULT = = = = =\n" +
				result.map(bike -> ("ITEM IS FOUND!\n" + bike.toDisplayFormatString()) + "\n")
						.orElse("Item is not found :(\n") +
				"= = = = = = = = = = = = = = = = =";
		out.println(s);
	}

	public int writeToFileOptions() {
		String s = "1 - Create new file\n" +
				"2 - Overwrite input file\n" +
				"3 - Cancel";
		out.println(s);
		return UserInput.getIntInRange(ENTER_A_NUMBER, 1, 3, false);
	}
}
