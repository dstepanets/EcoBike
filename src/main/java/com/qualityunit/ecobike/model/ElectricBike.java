package com.qualityunit.ecobike.model;

import java.util.Objects;

public class ElectricBike extends AbstractBike {
	private Integer maxSpeed;
	private Integer batteryCapacity;

	protected ElectricBike(ElectricBikeBuilder builder) {
		super(builder);
		maxSpeed = builder.maxSpeed;
		batteryCapacity = builder.batteryCapacity;
	}

	public static ElectricBikeBuilder getBuilder() {
		return new ElectricBikeBuilder();
	}

	public static class ElectricBikeBuilder extends BikeBuilder<ElectricBikeBuilder> {
		private Integer maxSpeed;
		private Integer batteryCapacity;

		public AbstractBike build() {
			return new ElectricBike(this);
		}

		@Override
		protected ElectricBikeBuilder getThis() {
			return this;
		}

		public ElectricBikeBuilder withMaxSpeed(Integer maxSpeed) {
			this.maxSpeed = maxSpeed;
			return this;
		}

		public ElectricBikeBuilder withBatteryCapacity(Integer batteryCapacity) {
			this.batteryCapacity = batteryCapacity;
			return this;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		ElectricBike that = (ElectricBike) o;
		return maxSpeed.equals(that.maxSpeed) &&
				batteryCapacity.equals(that.batteryCapacity);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), maxSpeed, batteryCapacity);
	}

	@Override
	public String toString() {
		return "ElectricBike{" +
				"maxSpeed=" + maxSpeed +
				", batteryCapacity=" + batteryCapacity +
				"} " + super.toString();
	}

	@Override
	public String toDisplayFormatString() {
		return super.toDisplayFormatString() +
				" with " + batteryCapacity + " mAh battery and " +
				(!getHasLights() ? "no " : "") + "head/tail light.\n" +
				"Price: " + getPrice() + " euros.";
	}
}
