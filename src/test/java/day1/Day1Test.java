package day1;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

public class Day1Test {


    private final String testInput = """
3   4
4   3
2   5
1   3
3   9
3   3
""";

    @Test
    public void inputReader() throws Exception {
        final List<List<Long>> locationLists = LocationListsReader.readLocationsFromString(testInput);
        assertThat(locationLists).hasSize(2);
        final List<Long> leftList = locationLists.get(0);
        assertThat(leftList).hasSize(6);
        final List<Long> leftLocations = List.of(3L, 4L, 2L, 1L, 3L, 3L);
        assertThat(leftList).isEqualTo(leftLocations);

        final List<Long> rightList = locationLists.get(1);
        final List<Long> rightLocations = List.of(4L, 3L, 5L, 3L, 9L, 3L);
        assertThat(rightList).hasSize(6);
        assertThat(rightList).isEqualTo(rightLocations);
    }

    @Test
    public void exampleTestCasePart1() throws Exception {
        final List<List<Long>> locationLists = LocationListsReader.readLocationsFromString(testInput);
        assertThat(ListDiffer.listDifference(locationLists.get(0), locationLists.get(1))).isEqualTo(11L);
    }

    @Test
    public void exampleTestCasePart2() throws Exception {
        final List<List<Long>> locationLists = LocationListsReader.readLocationsFromString(testInput);
        assertThat(ListDiffer.listSimilarity(locationLists.get(0), locationLists.get(1))).isEqualTo(31L);
    }
}
