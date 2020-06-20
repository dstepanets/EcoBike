package com.qualityunit.ecobike;

import com.qualityunit.ecobike.controller.AppController;
import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.Storage;
import com.qualityunit.ecobike.service.FileParser;

import java.util.List;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {
		AppController appController = new AppController();
		Stream<String> stream = appController.getStreamFromFilePath(args);

		List<AbstractBike> bikesCatalog = Storage.getInstance().getCatalog();
		new FileParser().parseLinesStreamToStorage(stream, bikesCatalog);
		stream.close();

		appController.runMenu();
	}
}
