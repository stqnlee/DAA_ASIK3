package com.mst.algorithm;

import com.mst.model.Edge;
import com.mst.model.Graph;

import java.util.*;

public class KruskalAlgorithm {

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

    static class UnionFind {
        Map<String, String> parent;
        Map<String, Integer> rank;

        UnionFind(Set<String> nodes) {
            parent = new HashMap<>();
            rank = new HashMap<>();
            for (String node : nodes) {
                parent.put(node, node);
                rank.put(node, 0);
            }
        }

        String find(String x) {
            if (!parent.get(x).equals(x)) {
                parent.put(x, find(parent.get(x)));
            }
            return parent.get(x);
        }

        void union(String x, String y) {
            String px = find(x), py = find(y);
            if (px.equals(py)) return;
            if (rank.get(px) < rank.get(py)) {
                parent.put(px, py);
            } else {
                parent.put(py, px);
                if (rank.get(px).equals(rank.get(py))) {
                    rank.put(px, rank.get(px) + 1);
                }
            }
        }
    }

    public static Result findMST(Graph graph) {
        long startTime = System.nanoTime();
        long operations = 0;

        if (!graph.isConnected()) {
            throw new IllegalArgumentException("Graph is disconnected. MST does not exist.");
        }

        List<Edge> allEdges = new ArrayList<>(graph.getEdges());
        operations += allEdges.size();

        Collections.sort(allEdges);
        operations += (long) allEdges.size() * Math.log(allEdges.size()); // approx

        UnionFind uf = new UnionFind(graph.getNodes());
        operations += graph.getNodes().size() * 2;

        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;

        for (Edge edge : allEdges) {
            operations++;
            if (!uf.find(edge.getFrom()).equals(uf.find(edge.getTo()))) {
                operations += 2;
                uf.union(edge.getFrom(), edge.getTo());
                operations++;
                mstEdges.add(edge);
                totalCost += edge.getWeight();
                operations++;
            }
        }

        double executionTimeMs = (System.nanoTime() - startTime) / 1_000_000.0;
        return new Result(mstEdges, totalCost, operations, executionTimeMs);
    }
}