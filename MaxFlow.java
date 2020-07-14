/*
    Ford Fulkerson's algorithm to solve the maximum flow problem in a flow network
 */

import javax.crypto.spec.PSource;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MaxFlow {
    public static void main(String [] args) {
        int[][] graph = new int[][]{    {0, 16, 13, 0, 0, 0},
                                        {0, 0, 10, 12, 0, 0},
                                        {0, 4, 0, 0, 14, 0},
                                        {0, 0, 9, 0, 0, 20},
                                        {0, 0, 0, 7, 0, 4},
                                        {0, 0, 0, 0, 0, 0}  };
        int V = graph.length;
        System.out.println(findMaxFlow(graph, 0, V-1, V));
    }

    public static int findMaxFlow(int[][] graph, int source, int sink, int V) {
        int maxFlow = 0;

        int[][] residualGraph = new int[V][V];
        for (int u = 0; u < V; u++)
            for (int v = 0; v < V; v++)
                residualGraph[u][v] = graph[u][v];

        int[] parent = new int[V];

        while(BFS(residualGraph, source, sink, V, parent)) {
            int path_flow = Integer.MAX_VALUE;
            for (int v = sink;v != source; v = parent[v]) {
                int u = parent[v];
                path_flow = Math.min(path_flow, residualGraph[u][v]);
            }
            for (int v = sink;v != source; v = parent[v]) {
                int u = parent[v];
                residualGraph[u][v] -= path_flow;
            }
            maxFlow += path_flow;
        }

        return maxFlow;
    }

    public static boolean BFS(int[][] graph, int source, int sink, int V, int[] parent) {
        boolean[] visited = new boolean[V];
        Arrays.fill(visited, false);
        Queue<Integer> q = new LinkedList<>();
        q.add(source);
        visited[source] = true;
        parent[source] = -1;
        while(!q.isEmpty()) {
            int u = q.poll();
            for(int v=0;v<V;v++) {
                if(!visited[v] && graph[u][v] > 0) {
                    q.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return visited[sink];
    }
}
