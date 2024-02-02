package lec03;

import java.util.*;

@SuppressWarnings("unused")
public class Permutation {

	<T extends Comparable<T>> boolean nextPermutation(T[] data) {
		if (data.length <= 1)
			return false;

		int last = data.length - 2;

		for (; last >= 0; last--)
			if (data[last].compareTo(data[last + 1]) < 0)
				break;

		if (last < 0)
			return false;

		int nextGreater = data.length - 1;

		for (int i = data.length - 1; i > last; i--)
			if (data[i].compareTo(data[last]) > 0) {
				nextGreater = i;
				break;
			}

		swap(data, nextGreater, last);

		reverse(data, last + 1, data.length - 1);

		return true;
	}

	<T> void swap(T[] data, int left, int right) {
		T tmp = data[left];
		data[left] = data[right];
		data[right] = tmp;
	}

	// both inclusive [left, right]
	<T> void reverse(T[] data, int left, int right) {
		for (; left < right; left++, right--)
			swap(data, left, right);
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

	public static void main(String[] args) {
		Permutation p = new Permutation();
//		int[] arr = { 1, 2, 3 };
//		Integer[] data = Arrays.stream(arr).boxed().toArray(Integer[]::new);

//		double[] arr = { 1, 2, 3 };
//		Double[] data = Arrays.stream(arr).boxed().toArray(Double[]::new);

//		Character[] data = "311".chars().mapToObj(c -> (char) c).toArray(Character[]::new);

		int[] data = { 1, 2, 3 };

		do {
			println(data);
		} while (p.nextPermutation(data));

	}

	static <T> void println(T[] data) {
		StringBuilder sb = new StringBuilder();
		for (T e : data)
			sb.append(e + " ");
		System.out.println(sb.substring(0, sb.length() - 1));
	}

	static void println(int[] data) {
		StringBuilder sb = new StringBuilder();
		for (int e : data)
			sb.append(e + " ");
		System.out.println(sb.substring(0, sb.length() - 1));
	}

}