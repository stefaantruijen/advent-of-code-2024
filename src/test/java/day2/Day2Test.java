package day2;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class Day2Test {

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
        final List<List<Integer>> reports = Day2.readReports(new StringReader(testInput));
        assertThat(Day2.numberOfSafeReports(reports)).isEqualTo(2);
    }

    @Test
    public void exampleTestCasePart2_problemDampener() throws Exception {
        final List<List<Integer>> reports = Day2.readReports(new StringReader(testInput));
        assertThat(Day2.numberOfSafeReportsAfterDampening(reports)).isEqualTo(4);
    }

    @Test
    public void isSafeIncreasingTrue() {
        assertThat(Day2.isSafe(List.of(1, 2, 3, 4, 5, 6), true)).isTrue();
    }

    @Test
    public void isSafeIncreaseFalse() {
        assertThat(Day2.isSafe(List.of(2, 8, 3, 4, 5, 6), true)).isFalse();
    }

    @Test
    public void isSafeDecreasingTrue() {
        assertThat(Day2.isSafe(List.of(6, 5, 4, 3, 2, 1), false)).isTrue();
    }

    @Test
    public void isSafeDecreaseFalse() {
        assertThat(Day2.isSafe(List.of(6, 5, 3, 8, 5, 6), false)).isFalse();
    }

    @Test
    public void testTolerance1() {
        assertThat(Day2.numberOfSafeReportsAfterDampening(List.of(List.of(1, 4, 3, 5, 7, 8)))).isEqualTo(1);
    }

    @Test
    public void testTolerance2() {
        assertThat(Day2.numberOfSafeReportsAfterDampening(List.of(List.of(1, 4, 3, 5, 7, 8), List.of(1, 1, 3, 5, 7, 8)))).isEqualTo(2);
    }

    @Test
    public void testTolerance0() {
        assertThat(Day2.numberOfSafeReportsAfterDampening(List.of(List.of(1, 4, 3, 5, 5, 8)))).isEqualTo(0);
    }

    @Test
    public void testTolerance0_2() {
        assertThat(Day2.numberOfSafeReportsAfterDampening(List.of(List.of(1, -4, 3, 5, 5, 8)))).isEqualTo(0);
    }

    @Test
    public void doesJavaWork() {
        final List<Integer> theList = List.of(1, 2, 3, 4);
        System.out.println(theList.subList(0, 0));
        System.out.println(theList.subList(1, theList.size()));
        System.out.println(theList.subList(2 + 1, 4));

        final List<Integer> arrayList = new ArrayList<>(List.of(1, 2, 3, 4));
        arrayList.remove(2);
        System.out.println(arrayList);
    }

}
