package com.qualityunit.ecobike.model;

import java.util.Objects;

public class FoldingBike extends AbstractBike {
	private final Integer wheelSize;
	private final Integer gearsNum;

	protected FoldingBike(FoldingBikeBuilder builder) {
		super(builder);
		this.wheelSize = builder.wheelSize;
		this.gearsNum = builder.gearsNum;
	}

	public static FoldingBikeBuilder getBuilder() {
		return new FoldingBikeBuilder();
	}

	public static class FoldingBikeBuilder extends BikeBuilder<FoldingBikeBuilder> {
		private Integer wheelSize;
		private Integer gearsNum;

		public FoldingBike build() {
			return new FoldingBike(this);
		}

		@Override
		protected FoldingBikeBuilder getThis() {
			return this;
		}

		public FoldingBikeBuilder withWheelSize(Integer wheelSize) {
			this.wheelSize = wheelSize;
			return this;
		}

		public FoldingBikeBuilder withGearsNum(Integer gearsNum) {
			this.gearsNum = gearsNum;
			return this;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		FoldingBike that = (FoldingBike) o;
		return wheelSize.equals(that.wheelSize) &&
				gearsNum.equals(that.gearsNum);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), wheelSize, gearsNum);
	}

	@Override
	public String toString() {
		return "FoldingBike{" +
				"wheelSize=" + wheelSize +
				", gearsNum=" + gearsNum +
				"} " + super.toString();
	}

	@Override
	public String toDisplayFormatString() {
		return super.toDisplayFormatString() +
				" with " + gearsNum + " gear(s) and " +
				(!getHasLights() ? "no " : "") + "head/tail light.\n" +
				"Price: " + getPrice() + " euros.";
	}
}
