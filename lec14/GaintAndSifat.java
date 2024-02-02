package lec14;

import java.io.*;
import java.util.*;

// Gaint and Sifat CodeChef
public class GaintAndSifat {
	MyReader rd = new MyReader();
	static final int MAX = (int) 1e5;
	int T, prefix[] = new int[MAX];
	String S, s;

	void solve() throws IOException {
		StringTokenizer st;
		StringBuilder sb;
		T = rd.nextInt();

		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(rd.readLine());
			s = rd.readLine();

			sb = new StringBuilder();
			while (st.hasMoreTokens())
				sb.append(st.nextToken());
			S = sb.toString();

			KMPpreprocess(s);

			System.out.printf("Case %d: %d\n", tc, KMPsearch(S, s));
		}
	}

	void KMPpreprocess(String P) {
		int j = 0;
		for (int i = 1; i < P.length(); i++) {
			while (j > 0 && P.charAt(i) != P.charAt(j))
				j = prefix[j - 1];

			if (P.charAt(i) == P.charAt(j))
				j++;
			prefix[i] = j;
		}
	}

	int KMPsearch(String S, String P) {
		int res = 0;
		int n = S.length(), m = P.length();
		int j = 0;

		for (int i = 0; i < n; i++) {
			while (j > 0 && S.charAt(i) != P.charAt(j))
				j = prefix[j - 1];

			if (S.charAt(i) == P.charAt(j))
				j++;
			if (j == m) {
				res++;
				j = prefix[j - 1];
			}
		}

		return res;
	}

	public static void main(String[] args) throws IOException {
		new GaintAndSifat().solve();
	}

	static class MyReader {
		final int BUFFER_SIZE = 1 << 16;
		BufferedInputStream bin;
		byte buffer[], c;
		int bufferPointer, bytesRead, lineLength = (int) 1e5 + 5;
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

		public String readLine() throws IOException {
			byte[] buf = new byte[lineLength];
			int cnt = 0;
			while ((c = read()) != -1) {
				if (c == '\n')
					break;
				buf[cnt++] = c;
			}
			if (isWindows && buf[cnt - 1] == 13)
				return new String(buf, 0, cnt - 1);
			return new String(buf, 0, cnt);
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