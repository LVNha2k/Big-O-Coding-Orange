package lec14;

import java.io.*;

// Password Codeforces
public class Password {
	MyReader rd = new MyReader();
	static PrintWriter pw = new PrintWriter(System.out);
	String s;
	int m, prefix[];

	void solve() throws IOException {
		m = (s = rd.next()).length();
		prefix = new int[m];

		KMPpreprocess(s);

		int len_t = prefix[m - 1];
		String ans = "";

		if (len_t == 0)
			ans = "Just a legend";
		else {
			boolean found = false;
			for (int i = 1; i < m - 1 && !found; i++)
				if (prefix[i] == len_t)
					found = true;
			if (found)
				ans = s.substring(0, len_t);
		}
		if (ans.length() > 0) {
			pw.print(ans);
			return;
		}

		len_t = prefix[len_t - 1];

		if (len_t == 0)
			ans = "Just a legend";
		else
			ans = s.substring(0, len_t);

		pw.print(ans);
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

	public static void main(String[] args) throws IOException {
		new Password().solve();
		pw.close();
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