package lec06;

@SuppressWarnings("unused")
public class NumberTheory1_Sample {

	int modularExponentiation(int a, int b, int m) {
		int res = 1;
		a %= m;
		while (b > 0) {
			if ((b & 1) == 1)
				res = (res * a) % m;
			b >>= 1;
			a = (a * a) % m;
		}
		return res;
	}

	int fastPow(int a, int b, int m) {
		if (b == 0)
			return 1;
		int res = fastPow(a, b >> 1, m);
		res = (res * res) % m;
		if ((b & 1) == 1)
			res = (res * a) % m;
		return res;
	}

	int modInverse(int b, int m) {
		int res = fastPow(b, m - 2, m);
		if ((res * b) % m == 1)
			return res;
		return -1;
	}

	int lcm(int a, int b) {
		return (a / gcd(a, b)) * b;
	}

	int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	int[] extendedEuclid(int b, int m) {
		int x1 = 1, y1 = 0, x2 = 0, y2 = 1, x3, y3;
		for (int q, r; m != 0;) {
			q = b / m;
			r = b % m;
			x3 = x1 - q * x2;
			y3 = y1 - q * y2;
			x1 = x2;
			y1 = y2;
			x2 = x3;
			y2 = y3;
			b = m;
			m = r;
		}
		return new int[] { b, x1, y1 };
	}

	void modInverse2(int b, int m) {
		int[] res = extendedEuclid(b, m);
		int gcd = res[0], x = res[1], y = res[2];
		if (gcd != 1)
			System.out.println("Inverse doesn't exist");
		else
			System.out.println((x + m) % m);
	}

	public static void main(String[] args) {
		NumberTheory1_Sample ins = new NumberTheory1_Sample();
		int a = 50, b = 100, m = 13;
		System.out.println(ins.modularExponentiation(a, b, m));
		System.out.println(ins.fastPow(a, b, m));

		b = 19;
		m = 141;
		System.out.println(ins.modInverse(b, m));
		System.out.println(ins.modInverse(22, 17));

		ins.modInverse2(b, m);
		ins.modInverse2(4, 14);

	}

}