package com.qualityunit.ecobike.model;

import java.util.Objects;

public abstract class AbstractBike implements Comparable<AbstractBike> {
	private final BikeType bikeType;
	private final String brand;
	private final int weight;
	private final Boolean hasLights;
	private final String color;
	private final int price;

	protected <T extends BikeBuilder<T>> AbstractBike(BikeBuilder<T> builder) {
		this.bikeType = builder.bikeType;
		this.brand = builder.brand;
		this.weight = builder.weight;
		this.hasLights = builder.hasLights;
		this.color = builder.color;
		this.price = builder.price;
	}

	protected abstract static class BikeBuilder<T extends BikeBuilder<T>> {
		private BikeType bikeType;
		private String brand;
		private int weight;
		private Boolean hasLights;
		private String color;
		private int price;

		protected abstract T getThis();

		public T withBikeType(BikeType bikeType) {
			this.bikeType = bikeType;
			return getThis();
		}

		public T withBrand(String brand) {
			this.brand = brand;
			return getThis();
		}

		public T withWeight(int weight) {
			this.weight = weight;
			return getThis();
		}

		public T withHasLights(Boolean hasLights) {
			this.hasLights = hasLights;
			return getThis();
		}

		public T withColor(String color) {
			this.color = color;
			return getThis();
		}

		public T withPrice(int price) {
			this.price = price;
			return getThis();
		}

		protected void validateFieldsBeforeBuild() {
			if (this.bikeType == null) {
				throw new BikeBuildingException("Bike type is not set");
			}
			if (this.brand == null || brand.trim().isEmpty()) {
				throw new BikeBuildingException("No brand name");
			}
			if (this.color == null) {
				throw new BikeBuildingException("No color is set");
			}
			if (this.weight < 0) {
				throw new BikeBuildingException("Weight can't be negative");
			}
			if (this.price < 0) {
				throw new BikeBuildingException("Price can't be negative");
			}
		}
	}

	public BikeType getBikeType() {
		return bikeType;
	}

	public String getBrand() {
		return brand;
	}

	public int getWeight() {
		return weight;
	}

	public String getColor() {
		return color;
	}

	public Boolean getHasLights() {
		return hasLights;
	}

	public int getPrice() {
		return price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AbstractBike other = (AbstractBike) o;
		return bikeType == other.bikeType &&
				brand.equals(other.brand) &&
				(other.weight == 0 || weight == other.weight) &&
				(other.hasLights == null || hasLights == other.hasLights) &&
				(other.color.isEmpty() || color.equals(other.color)) &&
				(other.price == 0 || price == other.price);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bikeType, brand, weight, hasLights, color, price);
	}

	@Override
	public int compareTo(AbstractBike that) {
		if (this.bikeType.compareTo(that.bikeType) < 0) {
			return -1;
		} else if (this.bikeType.compareTo(that.bikeType) > 0) {
			return 1;
		}
		if (this.brand.compareTo(that.brand) < 0) {
			return -1;
		} else if (this.brand.compareTo(that.brand) > 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return bikeType.toString() + " " + brand + "; ";
	}

	public String toDisplayFormatString() {
		return bikeType.toString() + " " + this.brand;
	}
}
