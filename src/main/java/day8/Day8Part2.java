package day8;

import static common.CharMatrixParser.constructMatrixFromReader;

import common.Position;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Day8Part2 {

	static int numberOfUniqueAntinodesWithResonance(final Reader reader) {
		final Set<Position> antinodePositions = new HashSet<>();
		final char[][] map = constructMatrixFromReader(reader);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if(map[i][j] != '.') {
					for (int k = 0; k < map.length; k++) {
						for (int l = 0; l < map[0].length; l++) {
							if (k != i && l != j && map[k][l] == map[i][j]) {
								int hammingDistanceRow = k - i;
								int hammingDistanceCol = l - j;
								final ArrayList<Position> potentialPositions = new ArrayList<>();
								boolean addedPosition = true;
								for(int counter = 1; counter < 10000; counter++) {
									// add a bunch of positions that are definitely out of bounds
									potentialPositions.add(new Position(i + hammingDistanceRow * counter, j + hammingDistanceCol * counter));
									potentialPositions.add(new Position(i - hammingDistanceRow * counter, j - hammingDistanceCol * counter));
									potentialPositions.add(new Position(k - hammingDistanceRow * counter, l - hammingDistanceCol * counter));
									potentialPositions.add(new Position(k + hammingDistanceRow * counter, l + hammingDistanceCol * counter));
								}
								for (Position potentialPosition : potentialPositions) {
									if (
											potentialPosition.row() >= 0
											&& potentialPosition.col() >= 0
											&& potentialPosition.row() < map.length
											&& potentialPosition.col() < map[0].length
											/*&& (potentialPosition.row() != i && potentialPosition.col() != j)
											&& (potentialPosition.row() != k && potentialPosition.col() != l)*/) {
										boolean added = antinodePositions.add(potentialPosition);
										if(added) {
											System.out.println("added " + potentialPosition);
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
		final var reader = new InputStreamReader(Day8Part2.class.getClassLoader().getResourceAsStream("day-8-input.txt"));
		final long nanosBefore = System.nanoTime();
		final int result = numberOfUniqueAntinodesWithResonance(reader);
		final long nanosAfter = System.nanoTime();
		final long millisElapsed = (nanosAfter - nanosBefore) / 1_000_000;
		System.out.println("# unique locations within the bounds of the map contain an antinode with resonance = " + result + ". Took " + millisElapsed + "ms");
	}
}
