package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.BikeType;
import com.qualityunit.ecobike.model.Storage;
import com.qualityunit.ecobike.view.Menu;

public class AddBikeCommand extends MenuCommand {
	private final BikeType bikeType;

	public AddBikeCommand(String description, BikeType bikeType) {
		super(description);
		this.bikeType = bikeType;
	}

	@Override
	public void execute() {
		AbstractBike bike = Menu.getInstance().constructBikeFromUserInput(bikeType, false);
		Storage.getInstance().addBike(bike);
	}
}
