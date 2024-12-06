package day5;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.StringReader;
import java.util.List;
import org.junit.jupiter.api.Test;

public class Day5Test {

	private final String exampleInput = """
			47|53
			97|13
			97|61
			97|47
			75|29
			61|13
			75|53
			29|13
			97|29
			53|29
			61|53
			97|53
			61|29
			47|13
			75|47
			97|75
			47|61
			75|61
			47|29
			75|13
			53|13
			
			75,47,61,53,29
			97,61,53,29,13
			75,29,13
			75,97,47,61,53
			61,13,29
			97,13,75,29,47
			""";

	@Test
	public void testExample() {
		final SleighInstructionSet sleighInstructionSet = Day5.parseInput(new StringReader(exampleInput));
		assertThat(Day5.checksumOfCorrectInstructions(sleighInstructionSet)).isEqualTo(143);
	}

	@Test
	public void testInstructionUpdateIsCorrect() {
		final UpdateInstructionRule rule = new UpdateInstructionRule(47, 53);
		List<Integer> updateInstruction = List.of(75, 47, 61, 53, 29);
		assertThat(rule.isSatisfiedBy(updateInstruction)).isTrue();
	}

	@Test
	public void testInstructionUpdateIsIncorrect() {
		final UpdateInstructionRule rule = new UpdateInstructionRule(47, 53);
		List<Integer> updateInstruction = List.of(75, 53, 61, 47, 29);
		assertThat(rule.isSatisfiedBy(updateInstruction)).isFalse();
	}

	@Test
	public void fixInvalidRule() {
		final UpdateInstructionRule rule = new UpdateInstructionRule(97, 75);
		List<Integer> updateInstruction = List.of(75,97,47,61,53);
		assertThat(rule.isSatisfiedBy(updateInstruction)).isFalse();
		assertThat(rule.fixBadRule(updateInstruction)).isEqualTo(List.of(97,75,47,61,53));
		assertThat(rule.isSatisfiedBy(rule.fixBadRule(updateInstruction))).isTrue();
	}

	@Test
	public void fixInvalidRule2() {
		final UpdateInstructionRule rule = new UpdateInstructionRule(29, 13);
		List<Integer> updateInstruction = List.of(61,13,29);
		assertThat(rule.isSatisfiedBy(updateInstruction)).isFalse();
		assertThat(rule.fixBadRule(updateInstruction)).isEqualTo(List.of(61,29,13));
		assertThat(rule.isSatisfiedBy(rule.fixBadRule(updateInstruction))).isTrue();
	}

	@Test
	public void testExamplePart2() {
		final SleighInstructionSet sleighInstructionSet = Day5.parseInput(new StringReader(exampleInput));
		assertThat(Day5.getChecksumOfFixedRules(sleighInstructionSet.updateInstructions(), sleighInstructionSet.rules())).isEqualTo(123);
	}

}
