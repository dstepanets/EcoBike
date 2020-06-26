package com.qualityunit.ecobike.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StorageImpl implements Storage {
	private static StorageImpl instance;
	private final List<AbstractBike> catalog;
	private boolean isUpdated = false;

	private StorageImpl() {
		catalog = Collections.synchronizedList(new ArrayList<>());
	}

	public static synchronized StorageImpl getInstance() {
		if (instance == null) {
			instance = new StorageImpl();
		}
		return instance;
	}

	@Override
	public List<AbstractBike> getCatalog() {
		return catalog;
	}

	@Override
	public synchronized void addBike(AbstractBike bike) {
		if (bike != null) {
			catalog.add(bike);
			isUpdated = true;
		}
	}

	@Override
	public boolean isUpdated() {
		return isUpdated;
	}

	@Override
	public void setUpdated(boolean updated) {
		isUpdated = updated;
	}

	@Override
	public synchronized Optional<AbstractBike> findItem(AbstractBike query) {
//		try {
//			Thread.sleep(20_000L);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		return catalog.stream()
				.sorted()
				.filter(b -> b.equals(query))
				.findFirst();
	}
}
