package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.BikeType;
import com.qualityunit.ecobike.model.Storage;
import com.qualityunit.ecobike.view.Menu;

import java.util.List;
import java.util.Optional;

import static java.lang.System.out;

public class FindItemCommand extends MenuCommand {
	public FindItemCommand(String description, Menu menu) {
		super(description, menu);
	}

	@Override
	public void execute() {
		BikeType bikeType = getMenu().chooseBikeType();
		AbstractBike query = getMenu().constructBikeFromUserInput(bikeType, true);

		out.println("Search may take time. Meanwhile, you can continue using the app.");
		Thread searchThread = new Thread(() -> {
			long startTime = System.nanoTime();
			Optional<AbstractBike> result = findItem(query);
			long endTime = System.nanoTime();
			out.println("Search time: " + (endTime - startTime));

			getMenu().displaySearchResult(result);
		});
		searchThread.start();
	}

	private Optional<AbstractBike> findItem(AbstractBike query) {
		synchronized (Storage.getInstance()) {
			final List<AbstractBike> catalog = Storage.getInstance().getCatalog();
			try {
				Thread.sleep(20_000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return catalog.stream()
					.sorted()
					.filter(b -> b.equals(query))
					.findFirst();
		}
	}
}
