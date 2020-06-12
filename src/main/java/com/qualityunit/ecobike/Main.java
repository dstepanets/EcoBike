package com.qualityunit.ecobike;

import com.qualityunit.ecobike.controller.AppController;

public class Main {

	public static void main(String[] args) {
		AppController appController = new AppController();
		appController.processInputFile(args);
		appController.runMenu();
	}

}
