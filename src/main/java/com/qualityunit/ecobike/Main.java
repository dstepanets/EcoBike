package com.qualityunit.ecobike;

import com.qualityunit.ecobike.controller.AppController;
import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.Storage;
import com.qualityunit.ecobike.model.StorageImpl;
import com.qualityunit.ecobike.service.FileParser;
import com.qualityunit.ecobike.view.UserInput;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/*
 * Instantiates controller and storage objects, invokes file parsing, then
 * passes control to AppController which runs app in a loop.
 */
public class Main {

	public static void main(String[] args) {
		AppController appController = new AppController(new UserInput());
		try {
			Stream<String> stream = appController.getStreamFromFilePath(args);

			Storage storage = StorageImpl.getInstance();
			List<AbstractBike> bikesCatalog = storage.getCatalog();
			new FileParser().parseLinesStreamToStorage(stream, bikesCatalog);
			stream.close();

			appController.runMenu();
			/* This can be thrown by the Scanner if the input stream is closed
			 * (e.g. with Ctrl-C or Ctrl-Z) while expecting user's input */
		} catch (NoSuchElementException | IllegalStateException e) {
			System.err.println("User input was closed. Program terminates.");
		}
	}
}
