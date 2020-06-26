package com.qualityunit.ecobike.model;

import java.util.Objects;

public class FoldingBike extends AbstractBike {
	private final int wheelSize;
	private final int gearsNum;

	protected FoldingBike(FoldingBikeBuilder builder) {
		super(builder);
		this.wheelSize = builder.wheelSize;
		this.gearsNum = builder.gearsNum;
	}

	public static FoldingBikeBuilder getBuilder() {
		return new FoldingBikeBuilder();
	}

	public static class FoldingBikeBuilder extends BikeBuilder<FoldingBikeBuilder> {
		private int wheelSize;
		private int gearsNum;

		public FoldingBike build() {
			validateFieldsBeforeBuild();
			return new FoldingBike(this);
		}

		@Override
		protected FoldingBikeBuilder getThis() {
			return this;
		}

		public FoldingBikeBuilder withWheelSize(int wheelSize) {
			this.wheelSize = wheelSize;
			return this;
		}

		public FoldingBikeBuilder withGearsNum(int gearsNum) {
			this.gearsNum = gearsNum;
			return this;
		}

		@Override
		protected void validateFieldsBeforeBuild() {
			super.validateFieldsBeforeBuild();
			if (this.wheelSize < 0) {
				throw new BikeBuildingException("Wheel size can't be negative");
			}
			if (this.gearsNum < 0) {
				throw new BikeBuildingException("Number of gears can't be negative");
			}
		}
	}

	public int getWheelSize() {
		return wheelSize;
	}

	public int getGearsNum() {
		return gearsNum;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		FoldingBike other = (FoldingBike) o;
		return (other.wheelSize == 0 || wheelSize == other.wheelSize) &&
				(other.gearsNum == 0 || gearsNum == other.gearsNum);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), wheelSize, gearsNum);
	}

	@Override
	public String toString() {
		return super.toString() + wheelSize + "; "
								+ gearsNum + "; "
								+ getWeight() + "; "
								+ getHasLights() + "; "
								+ getColor() + "; "
								+ getPrice();
	}

	@Override
	public String toDisplayFormatString() {
		return super.toDisplayFormatString() +
				" with " + gearsNum + " gear(s) and " +
				(!getHasLights() ? "no " : "") + "head/tail light.\n" +
				"Price: " + getPrice() + " euros.";
	}
}
