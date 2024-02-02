package lec02;

import java.io.*;

// Samu and her Birthday Party HackerEarth
public class SamuAndHerBirthdayParty {
	MyReader rd = new MyReader();
	int T, N, K, arr[], ans;
	boolean choice;

	void solve() throws IOException {
		T = rd.nextInt();
		while (T-- > 0) {
			arr = new int[N = rd.nextInt()];
			K = rd.nextInt();

			for (int i = 0, decimal; i < N; i++) {
				decimal = Integer.parseInt(rd.next(), 2);
				arr[i] = decimal;
			}

			ans = K;
			for (int menu = 1, max = 1 << K; menu < max; menu++) {
				choice = true;

				for (int friend : arr)
					if ((friend & menu) == 0) {
						choice = false;
						break;
					}
				if (choice)
					ans = Math.min(ans, countBit1(menu));
			}

			System.out.println(ans);
		}
	}

	int countBit1(int num) {
		int cnt = 0;
		while (num != 0) {
			if ((num & 1) == 1)
				cnt++;
			num >>= 1;
		}
		return cnt;
	}

	public static void main(String[] args) throws IOException {
		new SamuAndHerBirthdayParty().solve();
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

		public String next() throws IOException {
			StringBuilder sb = new StringBuilder();
			while ((c = read()) <= ' ')
				;
			do {
				sb.append((char) c);
			} while ((c = read()) > ' ');
			if (isWindows && c == 13)
				read();
			return sb.toString();
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