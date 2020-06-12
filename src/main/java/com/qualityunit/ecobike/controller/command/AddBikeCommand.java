package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.model.BikeType;

public class AddBikeCommand extends MenuCommand {
	public AddBikeCommand(String description, BikeType bikeType) {
		super(description);
	}

	@Override
	public void execute() {

	}
}
