package day2;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day2Part1 {

    public static List<List<Integer>> readReports(final Reader inputSource) {
        final List<List<Integer>> result = new ArrayList<>();
        try (final Scanner scanner = new Scanner(inputSource)) {
            while (scanner.hasNextLine()) {
                result.add(Arrays.stream(scanner.nextLine().split("\s+")).map(Integer::parseInt).toList());
            }
        }
        return result;
    }

    private static final int MIN_SAFE_DELTA = 1;
    private static final int MAX_SAFE_DELTA = 3;

    public static int numberOfSafeReports(final List<List<Integer>> reports) {
        int numberOfStableReports = 0;
        for (final List<Integer> report : reports) {
            // peek to see if this report must be monotonously increasing or decreasing
            final boolean isIncreasing = (report.get(0) - report.get(1)) < 0;
            final boolean isDecreasing = (report.get(0) - report.get(1)) > 0;
            if (!isIncreasing && !isDecreasing) {
                // stable sequence in first two readings -> skip
                continue;
            }
            int prevReading = 0;
            boolean isFirstReading = true;
            boolean isStable = true;
            for (final Integer reportReading : report) {
                if (isFirstReading) {
                    isFirstReading = false;
                    prevReading = reportReading;
                    continue;
                }
                final int lowerBound = isIncreasing ? prevReading + MIN_SAFE_DELTA : prevReading - MAX_SAFE_DELTA;
                final int upperBound = isIncreasing ? prevReading + MAX_SAFE_DELTA : prevReading - MIN_SAFE_DELTA;
                if (reportReading < lowerBound || reportReading > upperBound) {
                    isStable = false;
                    break;
                }
                prevReading = reportReading;
            }
            if (isStable) {
                numberOfStableReports++;
            }
        }
        return numberOfStableReports;
    }


    public static void main(final String[] args) {
        final InputStreamReader isr = new InputStreamReader(Day2Part1.class.getClassLoader().getResourceAsStream("day-2-input.txt"));
        final long noSafeReports = numberOfSafeReports(readReports(isr));
        System.out.println("The number of safe reports is " + noSafeReports);
    }
}
