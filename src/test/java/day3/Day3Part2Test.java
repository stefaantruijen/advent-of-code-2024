package day3;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringReader;
import java.util.List;
import org.junit.jupiter.api.Test;

public class Day3Part2Test {

	private final String exampleInput = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";

	@Test
	public void part2ExampleCase() throws Exception {
		final List<Multiplication> memory = Day3Part2.readMemory(new StringReader(exampleInput));
		assertThat(Day3.sumOfProducts(memory)).isEqualTo(48);
	}

	@Test
	public void multipleDosBeforeDont() throws Exception {
		final String testInput = "do()blabla&mul(2,4)do()don't()mul(1,5)mul(2,3)do()mul(1,4)";
		final List<Multiplication> memory = Day3Part2.readMemory(new StringReader(testInput));
		assertThat(Day3.sumOfProducts(memory)).isEqualTo(12);
	}

	@Test
	public void multipleDontsBeforeDo() throws Exception {
		final String testInput = "don't()blabla&mul(2,4)don't()don't()mul(1,5)mul(2,3)do()mul(1,4)";
		final List<Multiplication> memory = Day3Part2.readMemory(new StringReader(testInput));
		assertThat(Day3.sumOfProducts(memory)).isEqualTo(4);
	}

	@Test
	public void noDos() throws Exception {
		final String testInput = "don't()blabla&mul(2,4)don't()don't()mul(1,5)mul(2,3)don't()mul(1,4)";
		final List<Multiplication> memory = Day3Part2.readMemory(new StringReader(testInput));
		assertThat(Day3.sumOfProducts(memory)).isEqualTo(0);
	}

	@Test
	public void noDonts() throws Exception {
		final String testInput = "do()blabla&mul(2,4)do()do()mul(1,5)mul(2,3)do()mul(1,4)";
		final List<Multiplication> memory = Day3Part2.readMemory(new StringReader(testInput));
		assertThat(Day3.sumOfProducts(memory)).isEqualTo(23);
	}

}
