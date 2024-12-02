package day1;

import java.io.InputStreamReader;
import java.util.List;

public class MainDay1Part1 {

    public static void main(final String[] args) {
        final InputStreamReader isr = new InputStreamReader(MainDay1Part1.class.getClassLoader().getResourceAsStream("day-1-input.txt"));
        final List<List<Long>> locations = LocationListsReader.readLocations(isr);
        final long listDifference = ListDiffer.listDifference(locations.get(0), locations.get(1));
        System.out.println("The difference between both lists is " + listDifference);
    }
}
