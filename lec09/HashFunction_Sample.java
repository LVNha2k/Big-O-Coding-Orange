package lec09;

public class HashFunction_Sample {

	// Robert Sedgewick
	int RSHash(String keys) {
		int a = 63689, b = 378551;
		int hashValue = 0;

		for (int i = 0; i < keys.length(); i++) {
			hashValue = (hashValue * a + keys.charAt(i)) & 0x7FFFFFFF;
			a = (a * b) & 0x7FFFFFFF;
		}

		return hashValue & 0x7FFFFFFF;
	}

	// Polynominal
	int polyHash(String keys) {
		int a = 131, hashValue = 0;

		for (int i = 0; i < keys.length(); i++)
			hashValue = (keys.charAt(i) + a * hashValue) & 0x7FFFFFFF;

		return hashValue & 0x7FFFFFFF;
	}

	// Cyclic shift
	// Justin Sobel
	int JSHash(String keys) {
		int hashValue = 1315423911;
		int a = 5, b = 2;

		for (int i = 0, x, y; i < keys.length(); i++) {
			x = hashValue << a & 0x7FFFFFFF;
			y = hashValue >> b & 0x7FFFFFFF;
			hashValue ^= (x + keys.charAt(i) + y) & 0x7FFFFFFF;
		}

		return hashValue & 0x7FFFFFFF;
	}

	public static void main(String[] args) {
		HashFunction_Sample ins = new HashFunction_Sample();
		String keys = "Anne";

		System.out.println(ins.RSHash(keys));

		System.out.println(ins.polyHash(keys));

		System.out.println(ins.JSHash(keys));
	}

}