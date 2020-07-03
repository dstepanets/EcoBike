package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.model.StorageImpl;
import com.qualityunit.ecobike.view.Menu;
import com.qualityunit.ecobike.view.UserInput;

import static java.lang.System.out;

public class StopProgramCommand extends MenuCommand {
	public StopProgramCommand(String description, Menu menu) {
		super(description, menu);
	}

	@Override
	public void execute() {
		final UserInput userInput = getMenu().getUserInput();
		if (StorageImpl.getInstance().isUpdated()) {
			out.println("WARNING!\nThere are unsaved changes. You may want to write them to file first.");
			boolean answer = userInput.getBoolean("Quit without saving?", false);
			if (!answer) {
				return;
			}
		}
		userInput.closeScanner();
		System.exit(0);
	}
}
