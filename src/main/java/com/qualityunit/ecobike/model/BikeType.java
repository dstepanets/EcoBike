package com.qualityunit.ecobike.model;

public enum BikeType {
	FOLDING_BIKE("FOLDING BIKE"),
	EBIKE("E-BIKE"),
	SPEEDELEC("SPEEDELEC");

	private final String typeID;

	BikeType(String typeID) {
		this.typeID = typeID;
	}

	@Override
	public String toString() {
		return typeID;
	}

	public static BikeType fromString(String s) {
		for (BikeType b : BikeType.values()) {
			if (b.typeID.equalsIgnoreCase(s)) {
				return b;
			}
		}
		throw new IllegalArgumentException("No bike type with identifier '" + s + "'");
	}
}
