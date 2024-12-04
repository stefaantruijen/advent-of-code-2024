package day2;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day2 {

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
            if (isSafe(report, isIncreasing)) {
                numberOfStableReports++;
            } else {

            }
        }
        return numberOfStableReports;
    }

    public static void main(final String[] args) {
        final InputStreamReader isr = new InputStreamReader(Day2.class.getClassLoader().getResourceAsStream("day-2-input.txt"));
        final int noSafeReports = numberOfSafeReports(readReports(isr));
        System.out.println("The number of safe reports is " + noSafeReports);
        final int noSafeReportsWithDampening = numberOfSafeReportsAfterDampening(
                readReports(new InputStreamReader(Day2.class.getClassLoader().getResourceAsStream("day-2-input.txt"))));
        System.out.println("The number of safe reports after dampening is " + noSafeReportsWithDampening);
    }

    public static int numberOfSafeReportsAfterDampening(final List<List<Integer>> reports) {
        int numberOfStableReports = 0;
        for (final List<Integer> report : reports) {
            // peek to see if this report must be monotonously increasing or decreasing
            final boolean isIncreasing = (report.get(0) - report.get(1)) < 0;
            final boolean isDecreasing = (report.get(0) - report.get(1)) > 0;
            boolean skip = false;
            if (!isIncreasing && !isDecreasing) {
                // stable sequence in first two readings -> skip
                skip = true;
            }

            if (!skip && isSafe(report, isIncreasing)) {
                numberOfStableReports++;
            } else {
                final int numberOfStableReportsBefore = numberOfStableReports;
                // there is only a limited number of values -> brute force all permutations
                for (int i = 0; i < report.size(); i++) {
                    final List<Integer> reportWithValueRemoved = new ArrayList<>(report);
                    reportWithValueRemoved.remove(i);
                    assert reportWithValueRemoved.size() > 1 : "Unexpectedly small report";
                    // peek to see if this report must be monotonously increasing or decreasing
                    final boolean isIncreasing2 = reportWithValueRemoved.get(0) < reportWithValueRemoved.get(1);
                    final boolean isDecreasing2 = reportWithValueRemoved.get(0) > reportWithValueRemoved.get(1);
                    if (!isIncreasing2 && !isDecreasing2) {
                        continue;
                    }
                    if (isSafe(reportWithValueRemoved, isIncreasing2)) {
                        numberOfStableReports++;
                        break;
                    }
                }
                if (numberOfStableReportsBefore == numberOfStableReports) {
                    System.out.println("Unable to stablize " + report);
                }
            }
        }
        return numberOfStableReports;
    }

    static boolean isSafe(final List<Integer> report, final boolean isIncreasing) {
        boolean isFirstReading = true;
        int prevReading = 0;
        for (final Integer reportReading : report) {
            if (isFirstReading) {
                isFirstReading = false;
                prevReading = reportReading;
                continue;
            }
            final int lowerBound = isIncreasing ? prevReading + MIN_SAFE_DELTA : prevReading - MAX_SAFE_DELTA;
            final int upperBound = isIncreasing ? prevReading + MAX_SAFE_DELTA : prevReading - MIN_SAFE_DELTA;
            if (reportReading < lowerBound || reportReading > upperBound) {
                return false;
            }
            prevReading = reportReading;
        }
        return true;
    }
}
