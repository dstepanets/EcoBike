package com.qualityunit.ecobike.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Storage {
	private static Storage storageInstance;
	private final List<AbstractBike> catalog;
	private boolean isUpdated = false;

	private Storage() {
		catalog = Collections.synchronizedList(new ArrayList<>());
	}

	public static synchronized Storage getInstance() {
		if (storageInstance == null) {
			storageInstance = new Storage();
		}
		return storageInstance;
	}

	public List<AbstractBike> getCatalog() {
		return catalog;
	}

	public synchronized void addBike(AbstractBike bike) {
		catalog.add(bike);
		isUpdated = true;
	}

	public boolean isUpdated() {
		return isUpdated;
	}
}
