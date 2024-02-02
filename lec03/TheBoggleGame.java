package lec03;

import java.io.*;
import java.util.*;

// The Boggle Game UVa
@SuppressWarnings("unchecked")
public class TheBoggleGame {
	MyReader rd = new MyReader();
	static PrintWriter pw = new PrintWriter(System.out);
	static final String VOWELS = "AEOIUY";
	int[] dr = { 1, 1, 1, 0, 0, -1, -1, -1 };
	int[] dc = { 1, 0, -1, 1, -1, 1, 0, -1 };
	boolean firstTest = true, visited[][] = new boolean[4][4];
	char[][][] boards = new char[2][4][4]; // [boardId][row][col]
	TreeSet<String>[] words = new TreeSet[2];
	List<String> commonWords = new ArrayList<>();

	void solve() throws IOException {
		words[0] = new TreeSet<>();
		words[1] = new TreeSet<>();
		while (true) {
			words[0].clear();
			words[1].clear();
			commonWords.clear();

			for (int i = 0; i < 4; i++)
				for (int j = 0; j < 8; j++) {
					char ch = rd.nextPrintableCharNotSpace();
					if (ch == '#')
						return;
					boards[j >> 2][i][j & 3] = ch;
				}

			for (int boardId : new int[] { 0, 1 })
				for (int r = 0; r < 4; r++)
					for (int c = 0; c < 4; c++) {
						String curWord = "";
						curWord += boards[boardId][r][c];
						findWords(boards[boardId], r, c, curWord, words[boardId]);
					}

			for (String word : words[0])
				if (words[1].contains(word))
					commonWords.add(word);

			if (!firstTest)
				pw.print('\n');
			firstTest = false;

			if (commonWords.size() == 0)
				pw.println("There are no common words for this pair of boggle boards.");
			else
				for (String word : commonWords)
					pw.println(word);
		}
	}

	int countVowels(String word) {
		int res = 0;
		for (int i = 0; i < word.length(); i++)
			if (VOWELS.indexOf(word.charAt(i)) > -1)
				res++;
		return res;
	}

	void findWords(char[][] board, int r, int c, String curWord, TreeSet<String> foundWords) {
		if (curWord.length() == 4) {
			if (countVowels(curWord) == 2)
				foundWords.add(curWord);
			return;
		}

		visited[r][c] = true;
		for (int i = 0; i < 8; i++) {
			int tmpr = r + dr[i], tmpc = c + dc[i];

			if (isValid(tmpr, tmpc) && !visited[tmpr][tmpc]) {
				String newCurWord = curWord + board[tmpr][tmpc];
				findWords(board, tmpr, tmpc, newCurWord, foundWords);
			}
		}
		visited[r][c] = false;
	}

	boolean isValid(int row, int col) {
		return row >= 0 && row < 4 && col >= 0 && col < 4;
	}

	public static void main(String[] args) throws IOException {
		new TheBoggleGame().solve();
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

		public char nextPrintableCharNotSpace() throws IOException {
			do {
				c = read();
			} while (c <= 32 || c > 126);
			return (char) c;
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