package day7;

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

        long intermediateResult = 0L;
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < operators.length; i++) {
            for (int j = 0; j < operands.length; j++) {
                if (j == 0) {
                    intermediateResult = operands[0];
                    sb.append(operands[j]);
                } else {
                    if (operators[i][j - 1] == '+') {
                        sb.append(" + ");
                        sb.append(operands[j]);
                        intermediateResult += operands[j];
                    } else if (operators[i][j - 1] == '*') {
                        sb.append(" * ");
                        sb.append(operands[j]);
                        intermediateResult *= operands[j];
                    } else {
                        throw new IllegalStateException("unexpected operator");
                    }
                }
            }
            sb.append(" = ");
            sb.append(intermediateResult);
            if (intermediateResult == equationResult) {
                sb.append(" SUCCESS\n");
                System.out.println(sb.toString());
                return true;
            }
            sb.append(" FAIL\n");
        }
        return false;
    }

    public static void main(final String[] args) {
        final var reader = new InputStreamReader(Day7.class.getClassLoader().getResourceAsStream("day-7-input.txt"));
        System.out.println("Sum of left members of equations that add up = " + sumOfLeftMemberOfEquationsThatAddUp(reader));
    }
}
