package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.BikeType;
import com.qualityunit.ecobike.model.Storage;
import com.qualityunit.ecobike.view.Menu;

import java.util.Optional;

import static java.lang.System.out;

public class FindItemCommand extends MenuCommand {
	private final Storage storage;

	public FindItemCommand(String description, Menu menu, Storage storage) {
		super(description, menu);
		this.storage = storage;
	}

	@Override
	public void execute() {
		BikeType bikeType = getMenu().chooseBikeType();
		AbstractBike query = getMenu().constructBikeFromUserInput(bikeType, true);

		out.println("Search may take time. Meanwhile, you can continue using the app.");
		Thread searchThread = new Thread(() -> {

//			long startTime = System.nanoTime();
			Optional<AbstractBike> result = storage.findItem(query);
//			long endTime = System.nanoTime();
//			out.println("Search time: " + (endTime - startTime));

			getMenu().displaySearchResult(result);
		});
		searchThread.start();
	}
}
