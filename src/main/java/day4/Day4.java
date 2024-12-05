package day4;

import day3.Day3;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

public class Day4 {

	public static int xmasCountInWordFinder(final Reader inputSource) {
		final char[][] wordFinder = constructWordFinder(inputSource);
		return findHorizontalMatches(wordFinder) + findVerticalMatches(wordFinder) + findDiagonalMatches(wordFinder);
	}

	static int findHorizontalMatches(final char[][] wordFinder) {
		int result = 0;
		for (char[] row : wordFinder) {
			result = getNumberOfXmassesInSingleRow(result, row);
		}
		return result;
	}

	static int findVerticalMatches(final char[][] wordFinder) {
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

	static int findDiagonalMatches(final char[][] wordFinder) {
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


	private static char[][] constructWordFinder(final Reader inputSource) {
		char[][] wordfinder = null;
		int i = 0;
		try (final Scanner scanner = new Scanner(inputSource)) {
			while (scanner.hasNextLine()) {
				final String line = scanner.nextLine();
				if (i == 0) {
					// only support square input
					wordfinder = new char[line.length()][line.length()];
				}
				wordfinder[i++] = line.toCharArray();
			}
		}
		return wordfinder;
	}

	public static void main(String[] args) {
		final int xmasCount = xmasCountInWordFinder(new InputStreamReader(Day3.class.getClassLoader().getResourceAsStream("day-4-input.txt")));
		System.out.println("XMAS occurs " + xmasCount + " number of times in your wordfinder");
	}
}
