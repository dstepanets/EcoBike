package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.model.Storage;
import com.qualityunit.ecobike.view.Menu;
import com.qualityunit.ecobike.view.UserInput;

import static java.lang.System.*;

public class StopProgramCommand extends MenuCommand {
	public StopProgramCommand(String description, Menu menu) {
		super(description, menu);
	}

	@Override
	public void execute() {
		final UserInput userInput = getMenu().getUserInput();
		if (Storage.getInstance().isUpdated()) {
			out.println("WARNING!\nThere are unsaved changes. You may want to write them to file first.");
			boolean answer = userInput.getBoolean("Quit without saving?", false);
			if (!answer) {
				return;
			}
		}
		userInput.closeScanner();
		exit(0);
	}
}
