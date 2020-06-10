package com.qualityunit.ecobike.model;

public class FoldingBike extends AbstractBike {
	private int wheelSize;
	private int gearsNum;

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
	}

	@Override
	public String toString() {
		return "FoldingBike{" +
				"wheelSize=" + wheelSize +
				", gearsNum=" + gearsNum +
				"} " + super.toString();
	}
}
