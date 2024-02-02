package lec02;

import java.io.*;

public class SubsetPowerOfTwo {
	MyReader rd = new MyReader();
	int T, N, arr[], sumAND;
	boolean flag;

	void solve() throws IOException {
		T = rd.nextInt();
		while (T-- > 0) {
			arr = new int[N = rd.nextInt()];
			for (int i = 0; i < N; i++)
				arr[i] = rd.nextInt();

			flag = false;
			for (int i = 0, x; i < 31 && !flag; i++) {
				x = 1 << i;
				sumAND = (1 << 31) - 1; // 1 bit '0, 31 bit '1

				for (int num : arr)
					if ((num & x) != 0)
						sumAND &= num;

				if (sumAND == x)
					flag = true;
			}

			System.out.println(flag ? "YES" : "NO");
		}
	}

	public static void main(String[] args) throws IOException {
		new SubsetPowerOfTwo().solve();
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