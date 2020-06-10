package com.qualityunit.ecobike.model;

public abstract class AbstractBike {
	protected String brand;
	protected int weight;
	protected boolean hasLights;
	protected String color;
	protected int price;

	protected <T extends BikeBuilder<T>> AbstractBike(BikeBuilder<T> builder) {
		this.brand = builder.brand;
		this.weight = builder.weight;
		this.hasLights = builder.hasLights;
		this.color = builder.color;
		this.price = builder.price;
	}

	protected abstract static class BikeBuilder<T extends BikeBuilder<T>> {
		private String brand;
		private int weight;
		private boolean hasLights;
		private String color;
		private int price;

		protected abstract T getThis();

		public T withBrand(String brand) {
			this.brand = brand;
			return getThis();
		}

		public T withWeight(int weight) {
			this.weight = weight;
			return getThis();
		}

		public T withHasLights(boolean hasLights) {
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

	@Override
	public String toString() {
		return "{brand='" + brand + '\'' +
				", weight=" + weight +
				", hasLights=" + hasLights +
				", color='" + color + '\'' +
				", price=" + price +
				'}';
	}
}
