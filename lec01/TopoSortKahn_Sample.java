package lec01;

import java.util.*;

public class TopoSortKahn_Sample {

	static boolean topologicalSort(List<List<Integer>> graph, List<Integer> result) {
		int V = graph.size();
		int[] indegree = new int[V];
		Queue<Integer> zeroIndegree = new LinkedList<>();

		for (int u = 0; u < V; u++)
			for (int v : graph.get(u))
				indegree[v]++;

		for (int u = 0; u < V; u++)
			if (indegree[u] == 0)
				zeroIndegree.add(u);

		while (!zeroIndegree.isEmpty()) {
			int u = zeroIndegree.poll();
			result.add(u);

			for (int v : graph.get(u))
				if (--indegree[v] == 0)
					zeroIndegree.add(v);
		}

		return result.size() == V;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int V = sc.nextInt(), E = sc.nextInt();
		List<List<Integer>> graph = new ArrayList<>();

		for (int i = 0; i < V; i++)
			graph.add(new ArrayList<>());

		for (int i = 0; i < E; i++)
			graph.get(sc.nextInt()).add(sc.nextInt());
		sc.close();

		List<Integer> result = new ArrayList<>();
		if (topologicalSort(graph, result)) {
			System.out.println("Topological Sort of graph:");
			for (int x : result)
				System.out.printf("%d ", x);
		} else
			System.out.println("No result");
	}

//			8 9
//			0 1
//			1 2
//			1 5
//			1 6
//			4 1
//			4 7
//			7 6
//			3 7
//			3 5
}