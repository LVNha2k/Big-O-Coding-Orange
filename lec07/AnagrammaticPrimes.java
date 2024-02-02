package lec07;

import java.io.*;
import java.util.*;

// Anagrammatic Primes UVa
public class AnagrammaticPrimes {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX_N = (int) 1e7;
	int n;
	boolean[] isPrime = new boolean[MAX_N + 1];
	List<Integer> anaPrime = new ArrayList<>();

	void sieveOfEratosthenes() {
		Arrays.fill(isPrime, true);
		isPrime[0] = isPrime[1] = false;

		for (int i = 2; i * i <= MAX_N; i++)
			if (isPrime[i])
				for (int j = i * i; j <= MAX_N; j += i)
					isPrime[j] = false;
	}

	void solve() throws IOException {
		sieveOfEratosthenes();
		anaPrime.add(2);
		for (int i = 3; i <= MAX_N; i += 2)
			if (isPrime[i] && isAnaPrime(i))
				anaPrime.add(i);

		while ((n = rd.nextInt()) != 0) {
			int ind = anaPrime.size() - 1;

			while (ind >= 0 && anaPrime.get(ind) > n)
				ind--;

			ind++;
			if (ind == anaPrime.size()) {
				pw.println(0);
				continue;
			}

			int pow10 = 1;
			while (n > 0) {
				n /= 10;
				pow10 *= 10;
			}

			if (anaPrime.get(ind) < pow10)
				pw.println(anaPrime.get(ind));
			else
				pw.println(0);
		}
		pw.close();
	}

	boolean isAnaPrime(int num) {
		String snum = num + "";
		int[] arr = new int[snum.length()];

		for (int i = 0; num > 0; num /= 10)
			arr[i++] = num % 10;
		Arrays.sort(arr);

		for (int digit : arr)
			if (digit % 2 == 0 || (digit == 5 && snum.length() > 1))
				return false;

		do {
			num = 0;
			for (int digit : arr)
				num = num * 10 + digit;
			if (!isPrime[num])
				return false;
		} while (nextPermutation(arr));

		return true;
	}

	boolean nextPermutation(int[] arr) {
		int len = arr.length;

		for (int i = len - 2; i >= 0; i--)
			if (arr[i] < arr[i + 1])
				for (int j = len - 1; j > i; j--)
					if (arr[i] < arr[j]) {
						swap(arr, i, j);
						reverse(arr, i + 1, len - 1);
						return true;
					}

		return false;
	}

	void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	void reverse(int[] arr, int left, int right) {
		for (; left < right; left++, right--)
			swap(arr, left, right);
	}

	public static void main(String[] args) throws IOException {
		new AnagrammaticPrimes().solve();
	}

	static class MyReader {
		final int BUFFER_SIZE = 1 << 16;
		BufferedInputStream bin;
		byte buffer[], c;
		int bufferPointer, bytesRead;
		boolean isWindows;
		final String FILE = "inp.txt";

		public MyReader() {
			this(false);
		}

		public MyReader(boolean file) {
			try {
				bin = new BufferedInputStream(file ? new FileInputStream(FILE) : System.in, BUFFER_SIZE);
			} catch (IOException e) {
				e.printStackTrace();
			}
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
			isWindows = System.getProperty("os.name").startsWith("Win");
		}

		public int nextInt() throws IOException {
			int ret = 0;
			c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
			if (isWindows && c == 13)
				read();
			return neg ? -ret : ret;
		}

		public boolean isEOF() {
			return buffer[0] == -1;
		}

		private byte read() throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		}

		private void fillBuffer() throws IOException {
			bytesRead = bin.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}

		public void close() throws IOException {
			if (bin == null)
				return;
			bin.close();
		}
	}
}