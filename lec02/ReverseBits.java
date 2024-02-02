package lec02;

public class ReverseBits {

	static int bitReversal(int n) {
		if (n < 0 || n > 255)
			throw new IllegalArgumentException("n in [0, 255]");

		int s = 8, rev = 0;
		while (n != 0) {
			// Shift left
			rev <<= 1;
			// XOR
			rev ^= (n & 1);
			// Shift right
			n >>= 1;
			s--;
		}
		if (s > 0)
			rev <<= s;
		return rev;
	}

	public static void main(String[] args) {
		int n = 38;
		int rev = bitReversal(n);

		StringBuilder sb = new StringBuilder(Integer.toBinaryString(n));
		for (int times = 8 - sb.length(); times-- > 0;)
			sb.insert(0, '0');

		System.out.printf("%d\t%s\n", n, sb.toString());

		sb = new StringBuilder(Integer.toBinaryString(rev));
		for (int times = 8 - sb.length(); times-- > 0;)
			sb.insert(0, '0');

		System.out.printf("%d\t%s\n", rev, sb.toString());
	}
}