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

	}
}
