package day3;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

public class Day3Test {

	private final String testInput = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";

	@Test
	public void inputParserTest() throws Exception {
		final List<Multiplication> memory = Day3.readMemory(new StringReader(testInput));
		assertThat(memory).hasSize(4);
		assertThat(memory).isEqualTo(List.of(new Multiplication(2,4), new Multiplication(5,5), new Multiplication(11,8), new Multiplication(8,5)));
	}

	@Test
	public void part1ExampleCase() throws Exception {
		final List<Multiplication> memory = Day3.readMemory(new StringReader(testInput));
		assertThat(Day3.sumOfProducts(memory)).isEqualTo(161);
	}

}
