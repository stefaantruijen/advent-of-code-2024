package day3;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day3Part2 {

	public static List<Multiplication> readMemory(final Reader inputSource) {
		StringBuilder sb = new StringBuilder();
		final List<Multiplication> result = new ArrayList<>();
		try (final Scanner scanner = new Scanner(inputSource)) {
			while (scanner.hasNextLine()) {
				sb.append(scanner.nextLine());
			}
		}
		String currentLine = sb.toString();
		while (currentLine.contains("don't()")) {
			final int firstDont = currentLine.indexOf("don't()");
			final int startIndexSearchForDo = firstDont + "don't()".length();
			if(!currentLine.substring(startIndexSearchForDo).contains("do()")) {
				// no more dos -> throw everything away
				currentLine = currentLine.substring(0, firstDont);
				continue;
			}
			final int firstDo = currentLine.substring(startIndexSearchForDo).indexOf("do()") + startIndexSearchForDo;
			currentLine = currentLine.substring(0, firstDont) + currentLine.substring(firstDo + "do()".length());
		}
		result.addAll(Day3.readMemory(new StringReader(currentLine)));
		return result;
	}

	public static void main(final String[] args) {
		final InputStreamReader isr = new InputStreamReader(Day3Part2.class.getClassLoader().getResourceAsStream("day-3-input.txt"));
		final List<Multiplication> multiplications = readMemory(isr);
		System.out.println("The sum of ENABLED multiplications is " + Day3.sumOfProducts(multiplications));
	}

}
