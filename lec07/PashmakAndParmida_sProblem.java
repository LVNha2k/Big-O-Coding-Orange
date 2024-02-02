package lec07;

import java.io.*;
import java.util.*;

// Pashmak and Parmida's problem Codeforces
public class PashmakAndParmida_sProblem {
	MyReader rd = new MyReader();
	int n, a[], fL[], fR[], tmp[];
	Map<Integer, Integer> map = new HashMap<>();

	void solve() throws IOException {
		n = rd.nextInt();
		a = new int[n + 1];
		fL = new int[n + 1];
		fR = new int[n + 1];
		tmp = new int[n + 1];

		for (int i = 1; i <= n; i++) {
			a[i] = rd.nextInt();
			map.put(a[i], map.getOrDefault(a[i], 0) + 1);
			fL[i] = map.get(a[i]);
		}

		map.clear();

		for (int i = n; i >= 1; i--) {
			map.put(a[i], map.getOrDefault(a[i], 0) + 1);
			fR[i] = map.get(a[i]);
		}

		System.out.print(calc(1, n));
	}

	long calc(int L, int R) {
		if (L == R)
			return 0;

		int mid = (L + R) / 2;

		long res = calc(L, mid) + calc(mid + 1, R);

		int i = L, j = mid + 1;

		while (j <= R) {
			while (i <= mid && fL[i] <= fR[j])
				i++;
			res += (mid - i + 1);
			j++;
		}

		merge(fL, L, R);
		merge(fR, L, R);

		return res;
	}

	void merge(int[] arr, int L, int R) {
		int mid = (L + R) / 2;
		int i = L, j = mid + 1, k = 0;

		while (i <= mid && j <= R)
			if (arr[i] < arr[j])
				tmp[k++] = arr[i++];
			else
				tmp[k++] = arr[j++];

		while (i <= mid)
			tmp[k++] = arr[i++];
		while (j <= R)
			tmp[k++] = arr[j++];

		for (i = L; i <= R; i++)
			arr[i] = tmp[i - L];
	}

	public static void main(String[] args) throws IOException {
		new PashmakAndParmida_sProblem().solve();
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