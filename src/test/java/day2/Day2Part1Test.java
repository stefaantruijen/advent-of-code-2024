package day2;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.Test;

public class Day2Part1Test {

    private final String testInput = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
        """;

    @Test
    public void exampleTestCasePart1() throws Exception {
        final List<List<Integer>> reports = Day2Part1.readReports(new StringReader(testInput));
        assertThat(Day2Part1.numberOfSafeReports(reports)).isEqualTo(2);
    }

    @Test
    public void exampleTestCasePart2() throws Exception {
        // TODO: Unlock level 1
    }
}
