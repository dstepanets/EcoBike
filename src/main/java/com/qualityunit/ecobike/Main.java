package com.qualityunit.ecobike;

import com.qualityunit.ecobike.controller.AppController;
import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.Storage;
import com.qualityunit.ecobike.model.StorageImpl;
import com.qualityunit.ecobike.service.FileParser;
import com.qualityunit.ecobike.view.UserInput;

import java.util.List;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {
		AppController appController = new AppController(new UserInput());
		Stream<String> stream = appController.getStreamFromFilePath(args);

		Storage storage = StorageImpl.getInstance();
		List<AbstractBike> bikesCatalog = storage.getCatalog();
		new FileParser().parseLinesStreamToStorage(stream, bikesCatalog);
		stream.close();

		appController.runMenu();
	}
}
