package day9;

import static day9.Day9.calculateDiskChecksum;
import static day9.Day9.createDiskState;
import static day9.Day9.fragmentFilesForMaxContiguousFreeSpace;
import static day9.Day9.fragmentedSystemChecksum;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class Day9Test {

	private final String exampleInput = """
			2333133121414131402
			""";

	@Test
	public void example() {
		assertThat(fragmentedSystemChecksum(exampleInput)).isEqualTo(1928L);
	}

	@Test
	public void fragTest() {
		final List<DiskBit> diskBits = createDiskState(exampleInput);
		final List<DiskBit> expected = new ArrayList<>();
		for (final char c : "0099811188827773336446555566..............".toCharArray()) {
			if (c == '.') {
				expected.add(DiskBit.FREE_BIT);
			} else {
				expected.add(new DiskBit(Character.getNumericValue(c)));
			}
		}

		assertThat(fragmentFilesForMaxContiguousFreeSpace(diskBits)).isEqualTo(expected);
	}

	@Test
	public void checksumExample() {
		final char[] charArray = "0099811188827773336446555566..............".toCharArray();
		final List<DiskBit> diskState = new ArrayList<>();
		for (final char c : charArray) {
			if (c == '.') {
				break;
			}
			diskState.add(new DiskBit(Character.getNumericValue(c)));
		}
		assertThat(calculateDiskChecksum(diskState)).isEqualTo(1928L);
	}

	@Test
	public void diskBitState() {
		final List<DiskBit> result = createDiskState("23331");
		assertThat(result).isEqualTo(List.of(
				new DiskBit(0),
				new DiskBit(0),
				DiskBit.FREE_BIT,
				DiskBit.FREE_BIT,
				DiskBit.FREE_BIT,
				new DiskBit(1),
				new DiskBit(1),
				new DiskBit(1),
				DiskBit.FREE_BIT,
				DiskBit.FREE_BIT,
				DiskBit.FREE_BIT,
				new DiskBit(2)
		));
	}

	@Test
	public void doesJavaWork() {
		final List<String> list = new ArrayList<>(3);
		list.add("a");
		list.add("b");
		list.add("c");
		list.remove("c");
		list.add(2, "5");
		assertThat(list).isEqualTo(List.of("a", "b", "5"));
	}
}
