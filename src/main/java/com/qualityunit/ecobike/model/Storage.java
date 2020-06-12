package com.qualityunit.ecobike.model;

import java.util.ArrayList;
import java.util.List;

public class Storage {
	private List<AbstractBike> catalog;
	private static Storage storageInstance;

	private Storage() {
		catalog = new ArrayList<>();
	}

	public static Storage getInstance() {
		if (storageInstance == null) {
			storageInstance = new Storage();
		}
		return storageInstance;
	}

	public List<AbstractBike> getCatalog() {
		return catalog;
	}
}
