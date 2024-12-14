package day9;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * https://www.youtube.com/watch?v=BXY6OZsA4TA
 */
public class Day9 {


	public static long fragmentedSystemChecksum(final String input) {
		final List<DiskBit> diskState = createDiskState(input);
		final List<DiskBit> fragmentedDisk = fragmentFilesForMaxContiguousFreeSpace(diskState);
		return calculateDiskChecksum(fragmentedDisk);
	}

	public static long compactFilesChecksum(final String input) {
		final List<DiskBit> diskState = createDiskState(input);
		final List<DiskBit> fragmentedDisk = compactFiles(diskState);
		return calculateDiskChecksum(fragmentedDisk);
	}

	static List<DiskBit> createDiskState(final String input) {
		final List<DiskBit> diskState = new ArrayList<>();
		final char[] inputArray = input.toCharArray();
		int nextFileId = 0;
		for (int i = 0; i < inputArray.length; i++) {
			final int numericValue = Character.getNumericValue(inputArray[i]);
			if (i % 2 == 0) {
				for (int j = 0; j < numericValue; j++) {
					diskState.add(new DiskBit(nextFileId));
				}
				nextFileId++;
			} else {
				for (int j = 0; j < numericValue; j++) {
					diskState.add(DiskBit.FREE_BIT);
				}
			}
		}
		return diskState;
	}


	static List<DiskBit> fragmentFilesForMaxContiguousFreeSpace(final List<DiskBit> diskState) {
		final List<DiskBit> result = new ArrayList<>(diskState);
		for (int i = 0; i < result.size(); i++) {
			if (result.get(i).isFree()) {
				for (int j = result.size() - 1; j > i; j--) {
					if (!result.get(j).isFree()) {
						final DiskBit bitToMove = result.remove(j);
						result.remove(DiskBit.FREE_BIT);
						result.add(DiskBit.FREE_BIT);
						result.add(i, bitToMove);
						break;
					}
				}
			}
		}
		return result;
	}

	static long calculateDiskChecksum(final List<DiskBit> diskState) {
		long result = 0L;
		for (int i = 0; i < diskState.size(); i++) {
			if (diskState.get(i).isFree()) {
				break;
			}
			result += (long) i * diskState.get(i).fileId();
		}
		return result;
	}

	static List<DiskBit> compactFiles(final List<DiskBit> diskState) {
		final List<DiskBit> result = new ArrayList<>(diskState);
		for (int i = 0; i < result.size(); i++) {
			if (result.get(i).isFree()) {
				int numberOfFreeBits = 0;
				while (i + numberOfFreeBits < result.size() && result.get(i + numberOfFreeBits).isFree()) {
					numberOfFreeBits++;
				}
				if (i + numberOfFreeBits >= result.size()) {
					break;
				}
				for (int j = result.size() - 1; j > i; j--) {
					if (!result.get(j).isFree()) {
						long fileIdToMove = result.get(j).fileId();
						int numberOfOccupiedBits = 0;
						while (result.get(j - numberOfOccupiedBits).fileId() == fileIdToMove) {
							numberOfOccupiedBits++;
						}
						if (numberOfOccupiedBits <= numberOfFreeBits) {
							for (int k = 0; k < numberOfOccupiedBits; k++) {
								assert !result.get(j - k).isFree() : "Must only move occupied bits";
								final DiskBit bitToMove = result.remove(j - k);
								assert bitToMove.fileId() == fileIdToMove : "trying to move unexpected bit";
								result.add(j - k, DiskBit.FREE_BIT);
								result.remove(i + k); // remove free bit
								result.add(i, bitToMove);
							}
							break;
						} else {
							// This comment is a tombstone for a bug that me more time than I'd care to admit "j -= numberOfOccupiedBits + 1"
							j = j - numberOfOccupiedBits + 1;
						}
					}
				}
			}
		}
		return result;
	}

	static List<DiskBit> compactFiles2(final List<DiskBit> diskState) {
		final List<DiskBit> result = new ArrayList<>(diskState);
		for (int i = result.size() - 1; i >= 0; i--) {
			if (!result.get(i).isFree()) {
				long fileIdToMove = result.get(i).fileId();
				int numberOfTakenBits = 0;
				while (i - numberOfTakenBits >= 0 && result.get(i - numberOfTakenBits).fileId() == fileIdToMove) {
					numberOfTakenBits++;
				}
				if (i - numberOfTakenBits < 0) {
					break;
				}
				for (int j = 0; j < i; j++) {
					if (result.get(j).isFree()) {
						int numberOfFreeBits = 0;
						while (result.get(j + numberOfFreeBits).isFree()) {
							numberOfFreeBits++;
						}
						if (numberOfFreeBits <= numberOfTakenBits) {
							for (int k = 0; k < numberOfTakenBits; k++) {
								final DiskBit bitToMove = result.remove(i - k);
								result.add(i - k, DiskBit.FREE_BIT);
								result.remove(j + k); // remove free bit
								result.add(j + k, bitToMove);
							}
							break;
						} else {
							j = j + numberOfTakenBits - 1;
						}
					}
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		final Reader reader = new InputStreamReader(Day9.class.getClassLoader().getResourceAsStream("day-9-input.txt"));
		String inputString = "";
		try (final Scanner scanner = new Scanner(reader)) {
			if (scanner.hasNextLine()) {
				inputString += scanner.nextLine();
			}
		}
		final long nanosBefore = System.nanoTime();
		final long result = fragmentedSystemChecksum(inputString);
		final long nanosAfter = System.nanoTime();
		final long millisElapsed = (nanosAfter - nanosBefore) / 1_000_000;
		System.out.println("Checksum of the fragged file system = " + result + ". Took " + millisElapsed + "ms");

		final long nanosBefore2 = System.nanoTime();
		final long result2 = compactFilesChecksum(inputString);
		final long nanosAfter2 = System.nanoTime();
		final long millisElapsed2 = (nanosAfter2 - nanosBefore2) / 1_000_000;
		System.out.println("Checksum of the fragged file system = " + result2 + ". Took " + millisElapsed2 + "ms");

	}
}
