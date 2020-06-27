package com.qualityunit.ecobike.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/*
* All commands except FindItemCommand access storage on the main thread. So they are safe to have
* direct access to the catalog list, they don't use it concurrently. AddItemCommand is not injected
* with the catalog but obtains it on execution from Storage instance through the synchronized method. So, if
* the search is running at the time on the other thread, adding is blocked.
*/
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

	/*
	* I decided not to implement sorting and binary search, as suggested in the task description.
	* I could write the complex and error-prone comparator that would have to ignore all fields absent
	* in a query, then do a binary search which could return ANY matching item and not the first in order,
	* as needed. And then sort everything back for keeping initial order.
	*
	* But this simple linear search with overridden equals() does the job much faster
	* and more reliable. If I'm required to do binary search I'm ready to rewrite this solution.
	*/
	@Override
	public synchronized Optional<AbstractBike> findItem(AbstractBike query) {
		return catalog.stream().filter(b -> b.equals(query)).findFirst();
	}
}
