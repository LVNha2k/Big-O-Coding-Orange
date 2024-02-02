package util;

import java.io.*;
import java.util.*;

public class MyScanner {
	final int BUFFER_SIZE = 1 << 16;
	final String FILE = "inp.txt";
	BufferedReader br;
	StringTokenizer st;

	public MyScanner() {
		this(false);
	}

	public MyScanner(boolean file) {
		if (file)
			try {
				br = new BufferedReader(new FileReader(FILE), BUFFER_SIZE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		else
			br = new BufferedReader(new InputStreamReader(System.in), BUFFER_SIZE);
	}

	String nextLine() throws IOException {
		return br.readLine();
	}

	String next() throws IOException {
		while (st == null || !st.hasMoreElements()) {
			String line = br.readLine();
			if (line == null)
				return null;
			st = new StringTokenizer(line);
		}
		return st.nextToken();
	}

	boolean hasNext() throws IOException {
		while (st == null || !st.hasMoreElements()) {
			String line = br.readLine();
			if (line == null)
				return false;
			st = new StringTokenizer(line);
		}
		return st.hasMoreTokens();
	}

	int nextInt() throws IOException {
		return Integer.parseInt(next());
	}

	long nextLong() throws IOException {
		return Long.parseLong(next());
	}

	double nextDouble() throws IOException {
		return Double.parseDouble(next());
	}
}