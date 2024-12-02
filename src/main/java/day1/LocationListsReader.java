package day1;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LocationListsReader {

    public static List<List<Long>> readLocationsFromString(final String locationLists) {
        return readLocations(new StringReader(locationLists));
    }

    public static List<List<Long>> readLocations(final Reader inputSource) {
        final List<Long> leftLocationList = new ArrayList<>();
        final List<Long> rightLocationList = new ArrayList<>();
        final List<List<Long>> result = List.of(leftLocationList, rightLocationList);
        try (final Scanner scanner = new Scanner(inputSource)) {
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                final String[] splitLine = line.split("\s+");
                leftLocationList.add(Long.parseLong(splitLine[0]));
                rightLocationList.add(Long.parseLong(splitLine[1]));
            }
        }
        return result;
    }
}
