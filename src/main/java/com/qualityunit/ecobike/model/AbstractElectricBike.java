package com.qualityunit.ecobike.model;

public abstract class AbstractElectricBike extends AbstractBike {
	private int maxSpeed;
	private int batteryCapacity;

	protected AbstractElectricBike(ElectricBikeBuilder builder) {
		super(builder);
		maxSpeed = builder.maxSpeed;
		batteryCapacity = builder.batteryCapacity;
	}

	public abstract static class ElectricBikeBuilder extends BikeBuilder<ElectricBikeBuilder> {
		private int maxSpeed;
		private int batteryCapacity;

		public abstract AbstractBike build();

		public ElectricBikeBuilder withMaxSpeed(int maxSpeed) {
			this.maxSpeed = maxSpeed;
			return getThis();
		}

		public ElectricBikeBuilder withBatteryCapacity(int batteryCapacity) {
			this.batteryCapacity = batteryCapacity;
			return getThis();
		}
	}

	@Override
	public String toString() {
		return "AbstractElectricBike{" +
				"maxSpeed=" + maxSpeed +
				", batteryCapacity=" + batteryCapacity +
				"} " + super.toString();
	}
}
