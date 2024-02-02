package lec14;

import java.io.*;

// Find String Roots SPOJ
public class FindStringRoots {
	MyReader rd = new MyReader();
	static final int MAX = (int) 1e5;
	String S;
	int[] prefix = new int[MAX];

	void solve() throws IOException {
		while (!(S = rd.next()).equals("*")) {
			KMPpreprocess(S);

			int ans = 1, m = S.length();

			if (m % (m - prefix[m - 1]) == 0)
				ans = m / (m - prefix[m - 1]);

			System.out.println(ans);
		}
	}

	void KMPpreprocess(String s) {
		int j = 0;
		for (int i = 1; i < s.length(); i++) {
			while (j > 0 && s.charAt(i) != s.charAt(j))
				j = prefix[j - 1];

			if (s.charAt(i) == s.charAt(j))
				j++;
			prefix[i] = j;
		}
	}

	public static void main(String[] args) throws IOException {
		new FindStringRoots().solve();
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