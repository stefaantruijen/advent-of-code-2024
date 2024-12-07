package common;

import java.io.Reader;
import java.util.Scanner;

public class CharMatrixParser {

	public static char[][] constructMatrixFromReader(final Reader inputSource) {
		char[][] result = null;
		int i = 0;
		try (final Scanner scanner = new Scanner(inputSource)) {
			while (scanner.hasNextLine()) {
				final String line = scanner.nextLine();
				if (i == 0) {
					// only support square input
					result = new char[line.length()][line.length()];
				}
				result[i++] = line.toCharArray();
			}
		}
		return result;
	}

}
