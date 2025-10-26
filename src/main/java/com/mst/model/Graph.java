package com.mst.model;

import java.util.*;

public class Graph {
    private final Map<String, List<Edge>> adjacencyList = new HashMap<>();
    private final Set<String> nodes = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    public void addNode(String node) {
        nodes.add(node);
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(String from, String to, int weight) {
        addNode(from);
        addNode(to);
        Edge edge = new Edge(from, to, weight);
        adjacencyList.get(from).add(edge);
        adjacencyList.get(to).add(new Edge(to, from, weight)); // undirected
        edges.add(edge); // store only one direction for Kruskal
    }

    public Set<String> getNodes() { return nodes; }
    public List<Edge> getEdges() { return edges; }
    public List<Edge> getNeighbors(String node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }

    public int getVertexCount() { return nodes.size(); }
    public int getEdgeCount() { return edges.size(); }

    public boolean isConnected() {
        if (nodes.isEmpty()) return true;
        Set<String> visited = new HashSet<>();
        dfs(nodes.iterator().next(), visited);
        return visited.size() == nodes.size();
    }

    private void dfs(String node, Set<String> visited) {
        visited.add(node);
        for (Edge edge : getNeighbors(node)) {
            String neighbor = edge.getTo();
            if (!visited.contains(neighbor)) {
                dfs(neighbor, visited);
            }
        }
    }
}