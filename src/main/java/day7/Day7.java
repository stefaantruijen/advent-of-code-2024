package day7;

import static day7.Day7Shared.linearCombinationMakesEquationMatchUp;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

public class Day7 {

    static long sumOfLeftMemberOfEquationsThatAddUp(final Reader inputSource) {
        long result = 0;
        try (final Scanner scanner = new Scanner(inputSource)) {
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                if (equationAddsUp(line)) {
                    result += Long.parseLong(line.split(":")[0]);
                }
            }
        }
        return result;
    }

    public static boolean equationAddsUp(final String inputLine) {
        final String[] split = inputLine.split(":");
        final long equationResult = Long.parseLong(split[0]);
        final String[] operandStrings = split[1].trim().split("\s+");
        final long[] operands = new long[operandStrings.length];
        for (int i = 0; i < operands.length; i++) {
            operands[i] = Long.parseLong(operandStrings[i]);
        }

        final int nBitNumber = (int) Math.round(Math.pow(2, operandStrings.length - 1));
        final char[][] operators = new char[nBitNumber + 1][operands.length - 1];
        for (int i = 0; i <= nBitNumber; i++) {
            for (int j = 0; j < operands.length - 1; j++) {
                final long bitshiftResult = (i >> j) & 1;
                operators[i][j] = bitshiftResult == 0 ? '+' : '*';
            }
        }

        return linearCombinationMakesEquationMatchUp(equationResult, operands, operators);
    }

    public static void main(final String[] args) {
        final var reader = new InputStreamReader(Day7.class.getClassLoader().getResourceAsStream("day-7-input.txt"));
        final long nanosBefore = System.nanoTime();
        final long result = sumOfLeftMemberOfEquationsThatAddUp(reader);
        final long nanosAfter = System.nanoTime();
        final long millisElapsed = (nanosAfter - nanosBefore) / 1_000_000;
        System.out.println("Sum of left members of equations that add up = " + result + ". Took " + millisElapsed + "ms");
    }
}
