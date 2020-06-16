package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.BikeType;
import com.qualityunit.ecobike.model.ElectricBike;
import com.qualityunit.ecobike.model.FoldingBike;
import com.qualityunit.ecobike.model.Storage;
import com.qualityunit.ecobike.view.Menu;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.lang.System.*;

public class FindItemCommand extends MenuCommand {
	public FindItemCommand(String description) {
		super(description);
	}

	@Override
	public void execute() {
		BikeType bikeType = Menu.getInstance().chooseBikeType();
		AbstractBike query = Menu.getInstance().constructBikeFromUserInput(bikeType, true);

			long startTime = System.nanoTime();
		Optional<AbstractBike> result = findItem(query);
			long endTime = System.nanoTime();
			out.println("Search time: " + (endTime - startTime));

		if (result.isPresent()) {
			out.println("ITEM IS FOUND!\n--> " + result.get().toDisplayFormatString());
		} else {
			out.println("Item is not found :(");
		}
	}

	private Optional<AbstractBike> findItem(AbstractBike query) {
		List<AbstractBike> catalog = Storage.getInstance().getCatalog();
		return catalog.stream()
				.sorted()
				.filter(b -> b.equals(query))
				.findFirst();
	}

}
