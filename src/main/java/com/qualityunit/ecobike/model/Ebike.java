package com.qualityunit.ecobike.model;

public class Ebike extends AbstractElectricBike {
	public static final String IDENTIFIER = "E-BIKE";

	protected Ebike(EbikeBuilder builder) {
		super(builder);
	}

	public static EbikeBuilder getBuilder() {
		return new EbikeBuilder();
	}

	public static class EbikeBuilder extends ElectricBikeBuilder {
		public Ebike build() {
			return new Ebike(this);
		}

		@Override
		protected ElectricBikeBuilder getThis() {
			return this;
		}
	}

	@Override
	public String toString() {
		return "Ebike{} " + super.toString();
	}
}
