package day1;

import java.io.InputStreamReader;
import java.util.List;

public class MainDay1Part2 {

    public static void main(final String[] args) {
        final InputStreamReader isr = new InputStreamReader(MainDay1Part2.class.getClassLoader().getResourceAsStream("day-1-input.txt"));
        final List<List<Long>> locations = LocationListsReader.readLocations(isr);
        final long listSimilarity = ListDiffer.listSimilarity(locations.get(0), locations.get(1));
        System.out.println("The similarity between both lists is " + listSimilarity);
    }
}
