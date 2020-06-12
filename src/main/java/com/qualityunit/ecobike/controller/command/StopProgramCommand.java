package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.view.UserInput;

public class StopProgramCommand extends MenuCommand {
	public StopProgramCommand(String description) {
		super(description);
	}

	@Override
	public void execute() {
		UserInput.closeScanner();
		System.exit(0);
	}
}
