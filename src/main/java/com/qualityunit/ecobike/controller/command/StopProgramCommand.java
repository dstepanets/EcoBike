package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.controller.AppController;
import com.qualityunit.ecobike.model.StorageImpl;
import com.qualityunit.ecobike.view.Menu;
import com.qualityunit.ecobike.view.UserInput;

import static java.lang.System.*;

public class StopProgramCommand extends MenuCommand {
	private final AppController controller;
	public StopProgramCommand(String description, Menu menu, AppController controller) {
		super(description, menu);
		this.controller = controller;
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
		controller.stopProgram();
	}
}
