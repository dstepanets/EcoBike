package com.qualityunit.ecobike.model;

import java.util.List;
import java.util.Optional;

public interface Storage {
	List<AbstractBike> getCatalog();

	void addBike(AbstractBike bike);

	boolean isUpdated();

	void setUpdated(boolean updated);

	Optional<AbstractBike> findItem(AbstractBike query);
}
