package day4;

import static common.CharMatrixParser.constructMatrixFromReader;

import day3.Day3;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

public class Day4 {

	public static int xmasCountInWordFinder(final Reader inputSource) {
		final char[][] wordFinder = constructMatrixFromReader(inputSource);
		return findHorizontalMatches(wordFinder) + findVerticalMatches(wordFinder) + findDiagonalMatches(wordFinder);
	}

	private static int findHorizontalMatches(final char[][] wordFinder) {
		int result = 0;
		for (char[] row : wordFinder) {
			result = getNumberOfXmassesInSingleRow(result, row);
		}
		return result;
	}

	private static int findVerticalMatches(final char[][] wordFinder) {
		int result = 0;
		for (int j = 0; j < wordFinder.length; j++) {
			char[] col = new char[wordFinder.length];
			for (int i = 0; i < wordFinder[0].length; i++) {
				col[i] = wordFinder[i][j];
			}
			result = getNumberOfXmassesInSingleRow(result, col);
		}
		return result;
	}


	private static int getNumberOfXmassesInSingleRow(int result, final char[] rowOrCol) {
		for (int i = 0; i < rowOrCol.length; i++) {
			char colChar = rowOrCol[i];
			if (colChar == 'X') {
				// check LTR/TTB
				if (i + "MAS".length() < rowOrCol.length
						&& rowOrCol[i + 1] == 'M'
						&& rowOrCol[i + 2] == 'A'
						&& rowOrCol[i + 3] == 'S') {
					result++;
				}
				// check RTL/BTT
				if (i - "MAS".length() >= 0
						&& rowOrCol[i - 1] == 'M'
						&& rowOrCol[i - 2] == 'A'
						&& rowOrCol[i - 3] == 'S') {
					result++;
				}
			}
		}
		return result;
	}

	private static int findDiagonalMatches(final char[][] wordFinder) {
		int result = 0;
		final int colLength = wordFinder[0].length;
		final int rowLength = wordFinder.length;
		for (int rowIndex = 0; rowIndex < wordFinder.length; rowIndex++) {
			for (int columnIndex = 0; columnIndex < rowLength; columnIndex++) {
				char rowChar = wordFinder[rowIndex][columnIndex];
				if (rowChar == 'X') {
					// check from topleft to bottomright
					if (rowIndex + "MAS".length() < rowLength
							&& columnIndex + "MAS".length() < colLength
							&& wordFinder[rowIndex + 1][columnIndex + 1] == 'M'
							&& wordFinder[rowIndex + 2][columnIndex + 2] == 'A'
							&& wordFinder[rowIndex + 3][columnIndex + 3] == 'S') {
						result++;
					}
					// check from bottomright to topleft
					if (rowIndex - "MAS".length() >= 0
							&& columnIndex - "MAS".length() >= 0
							&& wordFinder[rowIndex - 1][columnIndex - 1] == 'M'
							&& wordFinder[rowIndex - 2][columnIndex - 2] == 'A'
							&& wordFinder[rowIndex - 3][columnIndex - 3] == 'S') {
						result++;
					}
					// check from topright to bottomleft
					if (rowIndex + "MAS".length() < rowLength
							&& columnIndex - "MAS".length() >= 0
							&& wordFinder[rowIndex + 1][columnIndex - 1] == 'M'
							&& wordFinder[rowIndex + 2][columnIndex - 2] == 'A'
							&& wordFinder[rowIndex + 3][columnIndex - 3] == 'S') {
						result++;
					}
					// check from bottomleft to topright
					if (rowIndex - "MAS".length() >= 0
							&& columnIndex + "MAS".length() < colLength
							&& wordFinder[rowIndex - 1][columnIndex + 1] == 'M'
							&& wordFinder[rowIndex - 2][columnIndex + 2] == 'A'
							&& wordFinder[rowIndex - 3][columnIndex + 3] == 'S') {
						result++;
					}
				}
			}
		}
		return result;
	}


	private static int findCrossMasses(final char[][] wordFinder) {
		int result = 0;
		final int colLength = wordFinder[0].length;
		final int rowLength = wordFinder.length;
		for (int rowIndex = 0; rowIndex < wordFinder.length; rowIndex++) {
			for (int columnIndex = 0; columnIndex < rowLength; columnIndex++) {
				char rowChar = wordFinder[rowIndex][columnIndex];
				if (rowChar == 'A') {
					// see if we have an A dead-center and enough space for a potential cross
					if (rowIndex - 1 >= 0 && columnIndex - 1 >= 0 && rowIndex + 1 < rowLength && columnIndex + 1 < colLength) {
						// check for M top left and S bottom right OR M bottom right and S top left
						if ((wordFinder[rowIndex - 1][columnIndex - 1] == 'M'
								&& wordFinder[rowIndex + 1][columnIndex + 1] == 'S')
								|| (wordFinder[rowIndex - 1][columnIndex - 1] == 'S'
								&& wordFinder[rowIndex + 1][columnIndex + 1] == 'M')) {
							// check for M bottom left and S top right + M top right and S bottom left
							if ((wordFinder[rowIndex + 1][columnIndex - 1] == 'M'
									&& wordFinder[rowIndex - 1][columnIndex + 1] == 'S')
									|| (wordFinder[rowIndex + 1][columnIndex - 1] == 'S'
									&& wordFinder[rowIndex - 1][columnIndex + 1] == 'M')) {
								result++;
							}
						}
					}
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		final int xmasCount = xmasCountInWordFinder(new InputStreamReader(Day3.class.getClassLoader().getResourceAsStream("day-4-input.txt")));
		final int x_masCount = findCrossMasses(constructMatrixFromReader(new InputStreamReader(Day3.class.getClassLoader().getResourceAsStream("day-4-input.txt"))));
		System.out.println("XMAS occurs " + xmasCount + " number of times in your wordfinder");
		System.out.println("X-MAS occurs " + x_masCount + " number of times in your wordfinder");
	}
}
