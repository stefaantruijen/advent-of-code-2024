package day7;

import static day7.Day7.sumOfLeftMemberOfEquationsThatAddUp;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class Day7Test {

    private final String example1Input = """
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20
        """;

    @Test
    public void testSampleCasePart1() {
        assertThat(sumOfLeftMemberOfEquationsThatAddUp(new StringReader(example1Input))).isEqualTo(3749);
    }

}

