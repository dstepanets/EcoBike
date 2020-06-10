package com.qualityunit.ecobike.model;

public class ElectricBike extends AbstractBike {
	private BikeType bikeType;
	private int maxSpeed;
	private int batteryCapacity;

	protected ElectricBike(ElectricBikeBuilder builder) {
		super(builder);
		bikeType = builder.bikeType;
		maxSpeed = builder.maxSpeed;
		batteryCapacity = builder.batteryCapacity;
	}

	public static ElectricBikeBuilder getBuilder() {
		return new ElectricBikeBuilder();
	}

	public static class ElectricBikeBuilder extends BikeBuilder<ElectricBikeBuilder> {
		private BikeType bikeType;
		private int maxSpeed;
		private int batteryCapacity;

		public AbstractBike build() {
			return new ElectricBike(this);
		}

		@Override
		protected ElectricBikeBuilder getThis() {
			return this;
		}

		public ElectricBikeBuilder withBikeType(BikeType bikeType) {
			this.bikeType = bikeType;
			return this;
		}

		public ElectricBikeBuilder withMaxSpeed(int maxSpeed) {
			this.maxSpeed = maxSpeed;
			return this;
		}

		public ElectricBikeBuilder withBatteryCapacity(int batteryCapacity) {
			this.batteryCapacity = batteryCapacity;
			return this;
		}
	}

	@Override
	public String toString() {
		return "ElectricBike{" +
				"bikeType=" + bikeType +
				", maxSpeed=" + maxSpeed +
				", batteryCapacity=" + batteryCapacity +
				"} " + super.toString();
	}
}
