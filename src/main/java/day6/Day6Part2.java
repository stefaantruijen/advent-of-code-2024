package day6;

import static common.CharMatrixParser.constructMatrixFromReader;
import static day6.LabUtil.GUARD_REPRESENTATIONS;
import static day6.LabUtil.deepCopyLabState;
import static day6.LabUtil.labHasGuard;
import static day6.LabUtil.tick;
import static day6.LabUtil.visitAllGuardLocations;

import common.Position;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day6Part2 {

	public static int numberOfWaysToMakeTheGuardLoop(final char[][] labState) {
		final Guard initialGuardState = findGuard(labState);
		final Position forbiddenObstacleLocation = initialGuardState.position();
		char[][] labWithAllGuardPositions = visitAllGuardLocations(labState);
		final List<Position> obstaclePositionsToCheck = findAllObstaclePositionCandidateLocationsToCheck(labWithAllGuardPositions, forbiddenObstacleLocation);

		int result = 0;
		StringBuilder resultLog = new StringBuilder();
		for (Position position : obstaclePositionsToCheck) {
			System.out.println("Checking " + position);
			final char[][] labWithNewObstacle = deepCopyLabState(labState);
			labWithNewObstacle[position.row()][position.col()] = '#';
			if (doesGuardLoop(labWithNewObstacle)) {
				resultLog.append("Adding an obstacle at ");
				resultLog.append(position.toString());
				resultLog.append(" makes the guard loop\n");
				result++;
			}
		}
		System.out.println(resultLog);
		return result;
	}

	private static List<Position> findAllObstaclePositionCandidateLocationsToCheck(final char[][] labWithAllGuardPositions, final Position forbiddenObstacleLocation) {
		final List<Position> positionsToCheck = new ArrayList<>();
		for (int i = 0; i < labWithAllGuardPositions.length; i++) {
			for (int j = 0; j < labWithAllGuardPositions[0].length; j++) {
				if (labWithAllGuardPositions[i][j] == 'X'
						&& !(i == forbiddenObstacleLocation.row() && j == forbiddenObstacleLocation.col())) {
					positionsToCheck.add(new Position(i, j));
				}
			}
		}
		return positionsToCheck;
	}

	private record Guard(Position position, char representation) {
	}

	private static Guard findGuard(final char[][] labState) {
		for (int i = 0; i < labState.length; i++) {
			for (int j = 0; j < labState[0].length; j++) {
				if (GUARD_REPRESENTATIONS.contains(labState[i][j])) {
					return new Guard(new Position(i, j), labState[i][j]);
				}
			}
		}
		throw new IllegalStateException("There is no guard in the lab!");
	}

	static boolean doesGuardLoop(final char[][] labState) {
		// project what position the guard visited and in what direction she was walking
		final List<Guard> visitedGuardProjections = new ArrayList<>();
		char[][] nextLabState = deepCopyLabState(labState);
		while (labHasGuard(nextLabState)) {
			final Guard guard = findGuard(nextLabState);
			if (visitedGuardProjections.contains(guard)) {
				return true;
			}
			visitedGuardProjections.add(guard);
			final TickResult tickResult = tick(nextLabState);
			nextLabState = tickResult.labStateAfterTick();
			if (!tickResult.guardMoved()) {
				// we are looping on the same position
				return true;
			}

		}
		return false;
	}

	public static void main(String[] args) {
		var reader = new InputStreamReader(Day6Part2.class.getClassLoader().getResourceAsStream("day-6-input.txt"));
		char[][] parsedInput = constructMatrixFromReader(reader);
		long millisBefore = System.currentTimeMillis();
		int result = numberOfWaysToMakeTheGuardLoop(parsedInput);
		long millisAfter = System.currentTimeMillis();
		System.out.println("The guard can be looped in " + result + " ways");
		System.out.println("Took " + (millisAfter - millisBefore) + " ms");
	}
}

