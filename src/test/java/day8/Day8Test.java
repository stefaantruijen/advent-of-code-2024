package day8;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.StringReader;
import org.junit.jupiter.api.Test;

public class Day8Test {

	private final String exampleInput = """
			............
			........0...
			.....0......
			.......0....
			....0.......
			......A.....
			............
			............
			........A...
			.........A..
			............
			............
			""";

	@Test
	public void testExamplePart1() {
		final long nanosBefore = System.nanoTime();
		final int result = Day8.numberOfUniqueAntinodes(new StringReader(exampleInput));
		final long nanosAfter = System.nanoTime();
		final long millisElapsed = (nanosAfter - nanosBefore) / 1_000_000;
		System.out.println(millisElapsed + " ms");
		assertThat(result).isEqualTo(14);
	}

	@Test
	public void testExamplePart2() {
		final long nanosBefore = System.nanoTime();
		final int result = Day8.numberOfUniqueAntinodes(new StringReader(exampleInput));
		final long nanosAfter = System.nanoTime();
		final long millisElapsed = (nanosAfter - nanosBefore) / 1_000_000;
		System.out.println(millisElapsed + " ms");
		assertThat(result).isEqualTo(34);
	}
}
