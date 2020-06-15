package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.BikeType;
import com.qualityunit.ecobike.view.Menu;

public class FindItemCommand extends MenuCommand {
	public FindItemCommand(String description) {
		super(description);
	}

	@Override
	public void execute() {
		BikeType bikeType = Menu.getInstance().chooseBikeType();
		AbstractBike bike = Menu.getInstance().constructBikeFromUserInput(bikeType, true);

	}
}
