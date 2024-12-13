package day9;

public record DiskBit(long fileId) {
	public static DiskBit FREE_BIT = new DiskBit(-1);
	public boolean isFree() {
		return fileId() == -1;
	}
}
