package day6;

import static common.CharMatrixParser.constructMatrixFromReader;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.StringReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day6Test {
	private final String exampleInput = """
			....#.....
			.........#
			..........
			..#.......
			.......#..
			..........
			.#..^.....
			........#.
			#.........
			......#...
			""";

	private char[][] initialLabState;

	@BeforeEach
	public void setUp() {
		// set up the initial lab state of the example
		initialLabState = new char[10][10];
		for (int i = 0; i < initialLabState.length; i++) {
			for (int j = 0; j < initialLabState[0].length; j++) {
				initialLabState[i][j] = '.';
			}
		}
		initialLabState[0][4] = '#';
		initialLabState[1][9] = '#';
		initialLabState[3][2] = '#';
		initialLabState[4][7] = '#';
		initialLabState[6][1] = '#';
		initialLabState[6][4] = '^';
		initialLabState[7][8] = '#';
		initialLabState[8][0] = '#';
		initialLabState[9][6] = '#';
	}

	@Test
	public void testExamplePart1() {
		final var reader = new StringReader(exampleInput);
		final char[][] parsedInput = constructMatrixFromReader(reader);
		assertThat(Day6.numberOfVisitedPositions(parsedInput)).isEqualTo(41);
	}

	// sanity check of expectedLabState matrix
	@Test
	public void sanityCheckLabStateRepresentation() {
		assertThat(labStateToString(initialLabState)).isEqualTo(exampleInput);
	}

	@Test
	public void tickOnceFromStart() {
		final var reader = new StringReader(exampleInput);
		final char[][] parsedInput = constructMatrixFromReader(reader);
		final char[][] labStateAfterTick = Day6.tick(parsedInput);
		assertThat(labStateToString(labStateAfterTick)).isEqualTo("""
			....#.....
			.........#
			..........
			..#.......
			.......#..
			....^.....
			.#..X.....
			........#.
			#.........
			......#...
			""");
	}


	@Test
	public void tickWithRotate() {
		final String input = """
				.#.
				.^.
				...
				""";
		final var reader = new StringReader(input);
		final char[][] parsedInput = constructMatrixFromReader(reader);
		final char[][] labStateAfterTick = Day6.tick(parsedInput);
		assertThat(labStateToString(labStateAfterTick)).isEqualTo("""
				.#.
				.X>
				...
				""");
	}

	@Test
	public void tickWithMultipleRotate() {
		final String input = """
				.#.
				X^#
				...
				""";
		final var reader = new StringReader(input);
		final char[][] parsedInput = constructMatrixFromReader(reader);
		final char[][] labStateAfterTick = Day6.tick(parsedInput);
		assertThat(labStateToString(labStateAfterTick)).isEqualTo("""
				.#.
				XX#
				.v.
				""");
	}

	private String labStateToString(char[][] labState) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < labState.length; i++) {
			for (int j = 0; j < labState[0].length; j++) {
				sb.append(labState[i][j]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}