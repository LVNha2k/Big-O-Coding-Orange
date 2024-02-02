package lec14;

public class KMP_Sample {

	static void KMPpreprocess(String P, int[] prefix) {
		prefix[0] = 0;
		int j = 0;

		for (int i = 1; i < P.length(); i++) {
			while (j > 0 && P.charAt(i) != P.charAt(j))
				j = prefix[j - 1];

			if (P.charAt(i) == P.charAt(j))
				j++;
			prefix[i] = j;
		}
	}

	static void KMPsearch(String T, String P, int[] prefix) {
		int n = T.length(), m = P.length();
		int j = 0;

		for (int i = 0; i < n; i++) {
			while (j > 0 && T.charAt(i) != P.charAt(j))
				j = prefix[j - 1];

			if (T.charAt(i) == P.charAt(j))
				j++;
			if (j == m) {
				System.out.printf("Found pattern at index: %d\n", i - j + 1);
				j = prefix[j - 1];
			}
		}
	}

	public static void main(String[] args) {
		String T = "ACMIACMIAACMIACMIBC";
		String P = "ACMIACMIB";
		int[] prefix = new int[P.length()];

		KMPpreprocess(P, prefix);

		for (int num : prefix)
			System.out.printf("%d ", num);
		System.out.println();

		KMPsearch(T, P, prefix);
	}

}