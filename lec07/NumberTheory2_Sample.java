package lec07;

import java.util.*;

public class NumberTheory2_Sample {

	boolean isPrime(int n) {
		for (int i = 2; i * i <= n; i++)
			if (n % i == 0)
				return false;
		return n > 1;
	}

	List<Integer> sieveOfEratosthenes(int n) {
		boolean isPrime[] = new boolean[n + 1];
		Arrays.fill(isPrime, true);
		isPrime[0] = isPrime[1] = false;

		for (int i = 2; i * i <= n; i++)
			if (isPrime[i])
				for (int j = i * i; j <= n; j += i)
					isPrime[j] = false;

		List<Integer> primes = new ArrayList<>();

		for (int i = 2; i <= n; i++)
			if (isPrime[i])
				primes.add(i);

		return primes;
	}

	List<Integer> segmentedSieve(int left, int right, List<Integer> primes) {
		if (left == 1)
			left++;
		boolean[] isPrime = new boolean[right - left + 1];
		Arrays.fill(isPrime, true);

		for (int p : primes) {
			if (p > Math.sqrt(right))
				break;

			int base = left / p * p;
			if (base < left)
				base += p;

			for (int i = base; i <= right; i += p)
				if (i != p)
					isPrime[i - left] = false;
		}

		List<Integer> res = new ArrayList<>();
		for (int i = left; i <= right; i++)
			if (isPrime[i - left])
				res.add(i);

		return res;
	}

	int phi(int n) {
		int res = n;
		for (int i = 2; i * i <= n; i++)
			if (n % i == 0) {
				while (n % i == 0)
					n /= i;
				res = res / i * (i - 1);
			}

		if (n > 1)
			res = res / n * (n - 1);
		return res;
	}

	public static void main(String[] args) {
		NumberTheory2_Sample ins = new NumberTheory2_Sample();
		int n = 25;
		for (int p : ins.sieveOfEratosthenes(n))
			System.out.printf("%d ", p);
		System.out.println();

		int left = 11, right = 34;
		List<Integer> primes = ins.sieveOfEratosthenes((int) Math.sqrt(right + 1));

		for (int p : ins.segmentedSieve(left, right, primes))
			System.out.printf("%d ", p);
		System.out.println();

		n = 60;
		System.out.printf("phi(%d) = %d", n, ins.phi(n));

	}
}