package day4;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.StringReader;
import org.junit.jupiter.api.Test;

public class Day4Test {

	final String exampleInput = """
			MMMSXXMASM
			MSAMXMSMSA
			AMXSXMAAMM
			MSAMASMSMX
			XMASAMXAMM
			XXAMMXXAMA
			SMSMSASXSS
			SAXAMASAAA
			MAMMMXMMMM
			MXMXAXMASX
			""";

	@Test
	public void testExample() {
		assertThat(Day4.xmasCountInWordFinder(new StringReader(exampleInput))).isEqualTo(18);
	}
}
