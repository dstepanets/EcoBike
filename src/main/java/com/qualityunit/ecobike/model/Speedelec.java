package com.qualityunit.ecobike.model;

public class Speedelec extends AbstractElectricBike {
	public static final String IDENTIFIER = "SPEEDELEC";

	protected Speedelec(SpeedelecBuilder builder) {
		super(builder);
	}

	public static SpeedelecBuilder getBuilder() {
		return new SpeedelecBuilder();
	}

	public static class SpeedelecBuilder extends ElectricBikeBuilder {
		public Speedelec build() {
			return new Speedelec(this);
		}

		@Override
		protected ElectricBikeBuilder getThis() {
			return this;
		}
	}

	@Override
	public String toString() {
		return "Speedelec{} " + super.toString();
	}
}
