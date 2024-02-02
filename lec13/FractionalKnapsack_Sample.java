package lec13;

import java.util.*;

public class FractionalKnapsack_Sample {

	static class Item implements Comparable<Item> {
		int profit, weight;

		public Item(int p, int w) {
			profit = p;
			weight = w;
		}

		@Override
		public int compareTo(Item that) {
			return Double.compare(1.0 * that.profit / that.weight, 1.0 * this.profit / this.weight);
		}
	}

	static double fractionalKnapsack(Item[] items, int W) {
		Arrays.sort(items);
		double res = 0;

		for (Item item : items)
			if (W >= item.weight) {
				res += item.profit;
				W -= item.weight;
			} else {
				res += (1.0 * W / item.weight) * item.profit;
				break;
			}

		return res;
	}

	public static void main(String[] args) {
		Item[] items = new Item[5];
		items[0] = new Item(1, 1);
		items[1] = new Item(2, 1);
		items[2] = new Item(2, 2);
		items[3] = new Item(4, 6);
		items[4] = new Item(10, 4);

		int W = 10;
		double res = fractionalKnapsack(items, W);
		System.out.println("Maximum value: " + res);
		printItems(items, W);
	}

	static void printItems(Item[] items, int W) {
		// TODO
	}
}