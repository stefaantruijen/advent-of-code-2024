package day3;

import java.util.Objects;

public class Multiplication {
	private final int leftFactor;
	private final int rightFactor;

	public Multiplication(final int leftFactor, final int rightFactor) {
		this.leftFactor = leftFactor;
		this.rightFactor = rightFactor;
	}

	public int getProduct() {
		return leftFactor * rightFactor;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof final Multiplication that)) return false;
		return leftFactor == that.leftFactor && rightFactor == that.rightFactor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(leftFactor, rightFactor);
	}

	@Override
	public String toString() {
		return "mul(" + leftFactor + "," + rightFactor + ")";
	}
}
