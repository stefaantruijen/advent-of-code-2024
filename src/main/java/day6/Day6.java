package day6;

import static common.CharMatrixParser.constructMatrixFromReader;

import day5.Day5;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Day6 {

	private static final char INVALID_GUARD_REPRESENTATION = ' ';
	private static final List<Character> GUARD_REPRESENTATIONS = List.of('^', 'v', '>', '<');

	public static int numberOfVisitedPositions(final char[][] labState) {
		char[][] nextLabState = deepCopyLabState(labState);
		while (labHasGuard(nextLabState)) {
			nextLabState = tick(nextLabState);
		}
		return countVisitedLocations(nextLabState);
	}

	private static boolean labHasGuard(final char[][] labState) {
		for (int i = 0; i < labState.length; i++) {
			for (int j = 0; j < labState[0].length; j++) {
				if (GUARD_REPRESENTATIONS.contains(labState[i][j])) {
					return true;
				}
			}
		}
		return false;
	}

	private static int countVisitedLocations(final char[][] labState) {
		int result = 0;
		for (int i = 0; i < labState.length; i++) {
			for (int j = 0; j < labState[0].length; j++) {
				if (labState[i][j] == 'X') {
					result++;
				}
			}
		}
		return result;
	}

	static char[][] tick(final char[][] labState) {
		for (int i = 0; i < labState.length; i++) {
			for (int j = 0; j < labState[0].length; j++) {
				final Position previousPosition = new Position(i, j);
				if (GUARD_REPRESENTATIONS.contains(labState[i][j])) {
					char guardRepresentation = labState[i][j];
					Position nextPosition = determineNextPosition(i, j, guardRepresentation);
					while (isWithinBoundsOfLab(nextPosition.row(), nextPosition.col(), labState)
							&& labState[nextPosition.row()][nextPosition.col()] == '#') {
						guardRepresentation = rotateGuard(guardRepresentation);
						nextPosition = determineNextPosition(i, j, guardRepresentation);
					}
					// assuming there is only one guard
					return doMove(labState, nextPosition, previousPosition, guardRepresentation);
				}
			}
		}
		throw new IllegalStateException("Could not find any guards!");
	}

	private record Position(int row, int col) {
	}

	private static Position determineNextPosition(final int nextRow, final int nextCol, final char guardRepresentation) {
		return switch (guardRepresentation) {
			case '^' -> new Position(nextRow - 1, nextCol);
			case 'v' -> new Position(nextRow + 1, nextCol);
			case '>' -> new Position(nextRow, nextCol + 1);
			case '<' -> new Position(nextRow, nextCol - 1);
			default -> throw new IllegalStateException("Cannot rotate unexpected guard state " + guardRepresentation);
		};
	}

	static char[][] doMove(final char[][] labState, final Position nextPosition, final Position previousPosition, final char guardRepresentation) {
		final char[][] result = deepCopyLabState(labState);

		result[previousPosition.row()][previousPosition.col()] = 'X';
		if (isWithinBoundsOfLab(nextPosition.row(), nextPosition.col(), labState)) {
			result[nextPosition.row()][nextPosition.col()] = guardRepresentation;
		}
		return result;
	}

	private static boolean isWithinBoundsOfLab(final int row, final int col, final char[][] labState) {
		return row >= 0 && col >= 0 && row < labState.length && col < labState[0].length;
	}

	private static char[][] deepCopyLabState(final char[][] labState) {
		char[][] result = new char[labState.length][labState[0].length];
		for (int i = 0; i < labState.length; i++) {
			result[i] = Arrays.copyOf(labState[i], labState[i].length);
		}
		return result;
	}

	private static char rotateGuard(final char guardRepresentation) {
		return switch (guardRepresentation) {
			case '^' -> '>';
			case '>' -> 'v';
			case 'v' -> '<';
			case '<' -> '^';
			default -> throw new IllegalStateException("Cannot rotate unexpected guard state " + guardRepresentation);
		};
	}

	public static void main(String[] args) {
		var reader = new InputStreamReader(Day6.class.getClassLoader().getResourceAsStream("day-6-input.txt"));
		char[][] parsedInput = constructMatrixFromReader(reader);
		int result = numberOfVisitedPositions(parsedInput);
		System.out.println("The guard visited " + result + " positions");
	}
}

