package day5;

import day3.Day3;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Day5 {
	public static int checksumOfCorrectInstructions(final Reader inputSource) {
		final List<UpdateInstructionRule> updateRules = new ArrayList<>();
		final List<List<Integer>> updateInstructions = new ArrayList<>();
		try (final Scanner scanner = new Scanner(inputSource)) {
			while (scanner.hasNextLine()) {
				final String line = scanner.nextLine();
				if (line.contains("|")) {
					final String[] split = line.split("\\|");
					assert split.length == 2;
					updateRules.add(new UpdateInstructionRule(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
				} else if (line.contains(",")) {
					final String[] split = line.split(",");
					final List<Integer> instructionsOnLine = Arrays.stream(split)
							.map(Integer::parseInt)
							.toList();
					updateInstructions.add(instructionsOnLine);
				}
			}
		}
		final List<List<Integer>> validInstructionLists = new ArrayList<>();
		for (List<Integer> instructionList : updateInstructions) {
			final Optional<UpdateInstructionRule> first = updateRules.stream().filter(rule -> !rule.isSatisfiedBy(instructionList)).findFirst();
			if (first.isEmpty()) {
				validInstructionLists.add(instructionList);
			}
		}
		return validInstructionLists.stream()
				.map(instructionList -> instructionList.get(instructionList.size() / 2))
				.reduce(0, Integer::sum);
	}

	public static void main(String[] args) {
		final int result = checksumOfCorrectInstructions(new InputStreamReader(Day5.class.getClassLoader().getResourceAsStream("day-5-input.txt")));
		System.out.println("What do you get if you add up the middle page number from those correctly-ordered updates? " + result);
	}

}
