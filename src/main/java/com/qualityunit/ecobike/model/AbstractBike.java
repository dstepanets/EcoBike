package com.qualityunit.ecobike.model;

import java.util.Objects;

public abstract class AbstractBike {
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
		AbstractBike bike = (AbstractBike) o;
		return weight == bike.weight &&
				price == bike.price &&
				bikeType == bike.bikeType &&
				brand.equals(bike.brand) &&
				Objects.equals(hasLights, bike.hasLights) &&
				Objects.equals(color, bike.color);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bikeType, brand, weight, hasLights, color, price);
	}

	@Override
	public String toString() {
		return "{bikeType=" + bikeType +
				", brand='" + brand + '\'' +
				", weight=" + weight +
				", hasLights=" + hasLights +
				", color='" + color + '\'' +
				", price=" + price +
				'}';
	}

	public String toDisplayFormatString() {
		return bikeType.toString() + " " + this.brand;
	}
}
