package com.mst.algorithm;

import com.mst.model.Edge;
import com.mst.model.Graph;

import java.util.*;

public class PrimAlgorithm {

    public static class Result {
        public final List<Edge> mstEdges;
        public final int totalCost;
        public final long operationsCount;
        public final double executionTimeMs;

        public Result(List<Edge> mstEdges, int totalCost, long operationsCount, double executionTimeMs) {
            this.mstEdges = mstEdges;
            this.totalCost = totalCost;
            this.operationsCount = operationsCount;
            this.executionTimeMs = executionTimeMs;
        }
    }

    public static Result findMST(Graph graph) {
        long startTime = System.nanoTime();
        long operations = 0;

        if (!graph.isConnected()) {
            throw new IllegalArgumentException("Graph is disconnected. MST does not exist.");
        }

        Set<String> nodes = graph.getNodes();
        if (nodes.isEmpty()) {
            return new Result(new ArrayList<>(), 0, 0, 0);
        }

        String start = nodes.iterator().next();
        Map<String, Integer> key = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        Set<String> inMST = new HashSet<>();

        for (String node : nodes) {
            key.put(node, Integer.MAX_VALUE);
            operations++;
        }
        key.put(start, 0);

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(null, start, 0));
        operations++;

        while (!pq.isEmpty()) {
            operations++;
            Edge minEdge = pq.poll();
            String u = minEdge.getTo();

            if (inMST.contains(u)) continue;
            inMST.add(u);
            operations++;

            for (Edge edge : graph.getNeighbors(u)) {
                operations++;
                String v = edge.getTo();
                int weight = edge.getWeight();
                if (!inMST.contains(v) && weight < key.getOrDefault(v, Integer.MAX_VALUE)) {
                    operations += 2;
                    key.put(v, weight);
                    parent.put(v, u);
                    pq.add(new Edge(u, v, weight));
                    operations += 3;
                }
            }
        }

        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;
        for (Map.Entry<String, String> entry : parent.entrySet()) {
            operations++;
            String v = entry.getKey();
            String u = entry.getValue();
            int weight = key.get(v);
            mstEdges.add(new Edge(u, v, weight));
            totalCost += weight;
            operations += 2;
        }

        double executionTimeMs = (System.nanoTime() - startTime) / 1_000_000.0;
        return new Result(mstEdges, totalCost, operations, executionTimeMs);
    }
}