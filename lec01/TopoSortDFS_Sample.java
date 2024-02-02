package lec01;

import java.util.*;

public class TopoSortDFS_Sample {

	static void dfs(int u, List<List<Integer>> graph, boolean[] visited, List<Integer> result) {
		visited[u] = true;

		for (int v : graph.get(u))
			if (!visited[v])
				dfs(v, graph, visited, result);

		result.add(u);
	}

	static void topologicalSort(List<List<Integer>> graph, List<Integer> result) {
		int V = graph.size();
		boolean[] visited = new boolean[V];

		for (int u = 0; u < V; u++)
			if (!visited[u])
				dfs(u, graph, visited, result);

		Collections.reverse(result);
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
		topologicalSort(graph, result);

		System.out.println("Topological Sort of graph:");
		for (int x : result)
			System.out.printf("%d ", x);
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