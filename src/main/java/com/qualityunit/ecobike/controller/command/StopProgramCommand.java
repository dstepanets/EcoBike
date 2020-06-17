package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.model.Storage;
import com.qualityunit.ecobike.view.UserInput;

import static java.lang.System.*;

public class StopProgramCommand extends MenuCommand {
	public StopProgramCommand(String description) {
		super(description);
	}

	@Override
	public void execute() {
		if (Storage.getInstance().isUpdated()) {
			out.println("WARNING!\nThere are unsaved changes. You may want to write them to file first.");
			boolean answer = UserInput.getBoolean("Quit without saving?", false);
			if (!answer) {
				return;
			}
		}
		UserInput.closeScanner();
		exit(0);
	}
}
