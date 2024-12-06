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
		assertThat(Day5.checksumOfCorrectInstructions(new StringReader(exampleInput))).isEqualTo(143);
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
}
