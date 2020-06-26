package com.qualityunit.ecobike.model;

import java.util.Objects;

public class ElectricBike extends AbstractBike {
	private final int maxSpeed;
	private final int batteryCapacity;

	protected ElectricBike(ElectricBikeBuilder builder) {
		super(builder);
		maxSpeed = builder.maxSpeed;
		batteryCapacity = builder.batteryCapacity;
	}

	public static ElectricBikeBuilder getBuilder() {
		return new ElectricBikeBuilder();
	}

	public static class ElectricBikeBuilder extends BikeBuilder<ElectricBikeBuilder> {
		private int maxSpeed;
		private int batteryCapacity;

		public AbstractBike build() {
			validateFieldsBeforeBuild();
			return new ElectricBike(this);
		}

		@Override
		protected ElectricBikeBuilder getThis() {
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

		@Override
		protected void validateFieldsBeforeBuild() {
			super.validateFieldsBeforeBuild();
			if (maxSpeed < 0) {
				throw new BikeBuildingException("Maximum speed can't be negative");
			}
			if (batteryCapacity < 0) {
				throw new BikeBuildingException("Battery capacity speed can't be negative");
			}
		}
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public int getBatteryCapacity() {
		return batteryCapacity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		ElectricBike other = (ElectricBike) o;
		return (other.maxSpeed == 0 || maxSpeed == other.maxSpeed) &&
				(other.batteryCapacity == 0 || batteryCapacity == other.batteryCapacity);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), maxSpeed, batteryCapacity);
	}

	@Override
	public String toString() {
		return super.toString() + maxSpeed + "; "
								+ getWeight() + "; "
								+ getHasLights() + "; "
								+ batteryCapacity + "; "
								+ getColor() + "; "
								+ getPrice();
	}

	@Override
	public String toDisplayFormatString() {
		return super.toDisplayFormatString() +
				" with " + batteryCapacity + " mAh battery and " +
				(!getHasLights() ? "no " : "") + "head/tail light.\n" +
				"Price: " + getPrice() + " euros.";
	}
}
