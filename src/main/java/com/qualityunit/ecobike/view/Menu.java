package com.qualityunit.ecobike.view;

import com.qualityunit.ecobike.controller.command.AddBikeCommand;
import com.qualityunit.ecobike.controller.command.Executable;
import com.qualityunit.ecobike.controller.command.FindItemCommand;
import com.qualityunit.ecobike.controller.command.MenuCommand;
import com.qualityunit.ecobike.controller.command.ShowCatalogCommand;
import com.qualityunit.ecobike.controller.command.StopProgramCommand;
import com.qualityunit.ecobike.controller.command.WriteToFileCommand;
import com.qualityunit.ecobike.model.BikeType;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.System.*;

public class Menu {
	public static final String FILE_PATH_PROMPT = "Please enter the input file path:";
	public static final String OPEN_ERROR_MSG = "Can't open a file at this path: '%s'";
	public static final String MENU_CHOICE_PROMPT = "Please make your choice:";
	public static final String SEPARATOR = "- - - - - - - - - - - - - - - - - - - - - - -";

	private final Map<Integer, MenuCommand> numToCommand;

	public Menu() {
		numToCommand = new LinkedHashMap<>();
		numToCommand.put(1, new ShowCatalogCommand("Show the entire EcoBike catalog"));
		numToCommand.put(2, new AddBikeCommand("Add a new folding bike", BikeType.FOLDING_BIKE));
		numToCommand.put(3, new AddBikeCommand("Add a new speedelec", BikeType.SPEEDELEC));
		numToCommand.put(4, new AddBikeCommand("Add a new e-bike", BikeType.EBIKE));
		numToCommand.put(5, new FindItemCommand("Find the first item of a particular brand"));
		numToCommand.put(6, new WriteToFileCommand("Write to file"));
		numToCommand.put(7, new StopProgramCommand("Stop the program"));
	}

	private void displayMenu() {
		out.println(SEPARATOR);
		out.println(MENU_CHOICE_PROMPT);
		numToCommand.forEach((k, c) -> out.println(k + " - " + c.getDescription()));
		out.println(SEPARATOR);
	}

	public Executable getCommandFromUser() {
		MenuCommand command;
		do {
			displayMenu();
			int choice = UserInput.getInt("Enter a number:");
			command = numToCommand.get(choice);
			if (command == null) {
				err.println("Wrong option");
			} else {
				out.println(command.getDescription());
			}
		} while (command == null);
		return command;
	}
}
