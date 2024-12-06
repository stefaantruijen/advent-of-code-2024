package day5;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Day5 {

	public static int checksumOfCorrectInstructions(final SleighInstructionSet sleighInstructionSet) {
		return getChecksumOfValidRules(sleighInstructionSet.rules(), sleighInstructionSet.updateInstructions());
	}

	static SleighInstructionSet parseInput(final Reader inputSource) {
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
		return new SleighInstructionSet(updateRules, updateInstructions);
	}

	private static Integer getChecksumOfValidRules(final List<UpdateInstructionRule> updateRules, final List<List<Integer>> updateInstructions) {
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


	static Integer getChecksumOfFixedRules(final List<List<Integer>> updateInstructions, final List<UpdateInstructionRule> updateRules) {
		final List<List<Integer>> validInstructionLists = new ArrayList<>();
		for (List<Integer> instructionList : updateInstructions) {
			boolean ruleWasFixed = false;
			List<Integer> fixedInstructionList = new ArrayList<>(instructionList);
			for (int i = 0; i < updateRules.size(); i++) {
				if(!updateRules.get(i).isSatisfiedBy(fixedInstructionList)) {
					fixedInstructionList = updateRules.get(i).fixBadRule(fixedInstructionList);
					for (int j = 0; j < i; j++) {
						// every fix might break a rule that passed on the first time -> reapply all rules
						if(!updateRules.get(j).isSatisfiedBy(fixedInstructionList)) {
							fixedInstructionList = updateRules.get(j).fixBadRule(fixedInstructionList);
						}
					}
					ruleWasFixed = true;
				}
			}
			// one final pass, just to make sure COPIUM
			for (UpdateInstructionRule rule : updateRules) {
				if(!rule.isSatisfiedBy(fixedInstructionList)) {
					fixedInstructionList = rule.fixBadRule(fixedInstructionList);
				}
			}
			if (ruleWasFixed) {
				validInstructionLists.add(fixedInstructionList);
			}
		}
		return validInstructionLists.stream()
				.map(instructionList -> instructionList.get(instructionList.size() / 2))
				.reduce(0, Integer::sum);
	}

	public static void main(String[] args) {
		final SleighInstructionSet sleighInstructionSet = parseInput(new InputStreamReader(Day5.class.getClassLoader().getResourceAsStream("day-5-input.txt")));
		final int result = checksumOfCorrectInstructions(sleighInstructionSet);
		System.out.println("What do you get if you add up the middle page number from those correctly-ordered updates? " + result);

		final int result2 = getChecksumOfFixedRules(sleighInstructionSet.updateInstructions(), sleighInstructionSet.rules());
		System.out.println("For fixed instructions? " + result2);
	}

}
