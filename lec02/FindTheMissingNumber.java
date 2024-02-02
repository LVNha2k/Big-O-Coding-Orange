package lec02;

public class FindTheMissingNumber {

	static int computeXOR(int n) {
		// If n is a multiple of 4
		if ((n & 3) == 0)
			return n;
		// If n % 4 gives remainder 1
		if ((n & 3) == 1)
			return 1;
		// If n % 4 gives remainder 2
		if ((n & 3) == 2)
			return n + 1;
		// If n % 4 gives remainder 3
		return 0;
	}

	static int getMissingNum(int... arr) {
		int xor = computeXOR(arr.length + 1);
		int xorArr = 0;
		for (int num : arr)
			xorArr ^= num;
		return xor ^ xorArr;
	}

	public static void main(String[] args) {
		System.out.print(getMissingNum(1, 3, 2, 6, 8, 4, 7, 9));
	}
}