package day6;

import static common.CharMatrixParser.constructMatrixFromReader;
import static day6.LabUtil.visitAllGuardLocations;

import java.io.InputStreamReader;

public class Day6 {

	public static int numberOfVisitedPositions(final char[][] labState) {
		final char[][] allGuardPositions = visitAllGuardLocations(labState);
		return countVisitedLocations(allGuardPositions);
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

	public static void main(String[] args) {
		var reader = new InputStreamReader(Day6.class.getClassLoader().getResourceAsStream("day-6-input.txt"));
		char[][] parsedInput = constructMatrixFromReader(reader);
		long nanoTimeBefore = System.nanoTime();
		int result = numberOfVisitedPositions(parsedInput);
		long nanoTimeAfter = System.nanoTime();
		System.out.println("The guard visited " + result + " positions");
		System.out.println("Took " + (nanoTimeAfter - nanoTimeBefore) + " nanoseconds");
		// result == 4559;
	}
}

