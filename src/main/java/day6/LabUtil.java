package day6;

import common.Position;
import java.util.Arrays;
import java.util.List;

public class LabUtil {

	public static final List<Character> GUARD_REPRESENTATIONS = List.of('^', 'v', '>', '<');

	static char[][] deepCopyLabState(final char[][] labState) {
		char[][] result = new char[labState.length][labState[0].length];
		for (int i = 0; i < labState.length; i++) {
			result[i] = Arrays.copyOf(labState[i], labState[i].length);
		}
		return result;
	}

	static char[][] visitAllGuardLocations(final char[][] labState) {
		char[][] allGuardPositions = deepCopyLabState(labState);
		while (labHasGuard(allGuardPositions)) {
			allGuardPositions = tick(allGuardPositions).labStateAfterTick();
		}
		return allGuardPositions;
	}

	static TickResult tick(final char[][] labState) {
		boolean shouldContinue = true;
		for (int i = 0; i < labState.length && shouldContinue; i++) {
			for (int j = 0; j < labState[0].length && shouldContinue; j++) {
				final Position previousPosition = new Position(i, j);
				if (GUARD_REPRESENTATIONS.contains(labState[i][j])) {
					char guardRepresentation = labState[i][j];
					Position nextPosition = determineNextPosition(i, j, guardRepresentation);
					int guardLoopingCounter = 0;
					while (isWithinBoundsOfLab(nextPosition.row(), nextPosition.col(), labState)
							&& labState[nextPosition.row()][nextPosition.col()] == '#') {
						// if guard is surrounded by obstacles, stop trying
						if (guardLoopingCounter++ >= 4) {
							shouldContinue = false;
							break;
						}
						guardRepresentation = rotateGuard(guardRepresentation);
						nextPosition = determineNextPosition(i, j, guardRepresentation);
					}
					// assuming there is only one guard
					if (shouldContinue) {
						final char[][] nextLabState = doMove(labState, nextPosition, previousPosition, guardRepresentation);
						return new TickResult(nextLabState, true);
					}
				}
			}
		}
		// no guard exists, or the guard is looping on the same position, return what we have
		return new TickResult(labState, false);
	}

	static Position determineNextPosition(final int nextRow, final int nextCol, final char guardRepresentation) {
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

	static boolean isWithinBoundsOfLab(final int row, final int col, final char[][] labState) {
		return row >= 0 && col >= 0 && row < labState.length && col < labState[0].length;
	}

	static char rotateGuard(final char guardRepresentation) {
		return switch (guardRepresentation) {
			case '^' -> '>';
			case '>' -> 'v';
			case 'v' -> '<';
			case '<' -> '^';
			default -> throw new IllegalStateException("Cannot rotate unexpected guard state " + guardRepresentation);
		};
	}

	public static boolean labHasGuard(final char[][] labState) {
		for (int i = 0; i < labState.length; i++) {
			for (int j = 0; j < labState[0].length; j++) {
				if (GUARD_REPRESENTATIONS.contains(labState[i][j])) {
					return true;
				}
			}
		}
		return false;
	}

}
