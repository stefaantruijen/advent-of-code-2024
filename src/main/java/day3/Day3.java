package day3;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    public static List<Multiplication> readMemory(final Reader inputSource) {
        final List<Multiplication> result = new ArrayList<>();
        try (final Scanner scanner = new Scanner(inputSource)) {
            while (scanner.hasNextLine()) {
                result.addAll(parseMemoryLine(scanner.nextLine()));
            }
        }
        return result;
    }

    private static List<Multiplication> parseMemoryLine(final String line) {
        final List<Multiplication> result = new ArrayList<>();
        final String regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(line);
        while(matcher.find()) {
            final int leftFactor = Integer.parseInt(matcher.group(1));
            final int rightFactor = Integer.parseInt(matcher.group(2));
            result.add(new Multiplication(leftFactor, rightFactor));
        }
        return result;
    }


    static int sumOfProducts(final List<Multiplication> multiplications) {
        return multiplications.stream()
                .map(Multiplication::getProduct)
                .reduce(0, Integer::sum);
    }

    public static void main(final String[] args) {
        final InputStreamReader isr = new InputStreamReader(Day3.class.getClassLoader().getResourceAsStream("day-3-input.txt"));
        final List<Multiplication> multiplications = readMemory(isr);
        System.out.println("The sum of multiplications is " + sumOfProducts(multiplications));
    }

}
