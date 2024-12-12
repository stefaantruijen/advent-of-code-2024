package day8;

import static common.CharMatrixParser.constructMatrixFromReader;

import common.Position;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Day8 {

	public static final String VALID_INPUTS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	static int numberOfUniqueAntinodes(final Reader reader) {
		final Set<Position> antinodePositions = new HashSet<>();
		final char[][] map = constructMatrixFromReader(reader);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if(/*VALID_INPUTS.contains("" + map[i][j])*/ map[i][j] != '.') {
					for (int k = 0; k < map.length; k++) {
						for (int l = 0; l < map[0].length; l++) {
							if (k != i && l != j && map[k][l] == map[i][j]) {
								int hammingDistanceRow = k - i;
								int hammingDistanceCol = l - j;
								final ArrayList<Position> potentialPositions = new ArrayList<>();
								potentialPositions.add(new Position(i + hammingDistanceRow, j + hammingDistanceCol));
								potentialPositions.add(new Position(i - hammingDistanceRow, j - hammingDistanceCol));
								potentialPositions.add(new Position(k - hammingDistanceRow, l - hammingDistanceCol));
								potentialPositions.add(new Position(k + hammingDistanceRow, l + hammingDistanceCol));
								for (Position potentialPosition : potentialPositions) {
									if (
											potentialPosition.row() >= 0
											&& potentialPosition.col() >= 0
											&& potentialPosition.row() < map.length
											&& potentialPosition.col() < map[0].length
											&& potentialPosition.row() != i && potentialPosition.col() != j
											&& potentialPosition.row() != k && potentialPosition.col() != l) {
										boolean added = antinodePositions.add(potentialPosition);
										if(added) {
											System.out.println("added " + potentialPosition + " char there = " + map[potentialPosition.row()][potentialPosition.col()]);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return antinodePositions.size();
	}

	public static void main(final String[] args) {
		final var reader = new InputStreamReader(Day8.class.getClassLoader().getResourceAsStream("day-8-input.txt"));
		final long nanosBefore = System.nanoTime();
		final int result = numberOfUniqueAntinodes(reader);
		final long nanosAfter = System.nanoTime();
		final long millisElapsed = (nanosAfter - nanosBefore) / 1_000_000;
		System.out.println("# unique locations within the bounds of the map contain an antinode = " + result + ". Took " + millisElapsed + "ms");
	}
}
