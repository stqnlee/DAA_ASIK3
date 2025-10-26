package com.mst;

import com.google.gson.*;
import com.mst.algorithm.KruskalAlgorithm;
import com.mst.algorithm.PrimAlgorithm;
import com.mst.model.Edge;
import com.mst.model.Graph;

import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Gson gson = new Gson();
            JsonObject inputJson = gson.fromJson(
                new FileReader("src/main/resources/assign_3_input.json"), JsonObject.class);
            JsonArray graphsArray = inputJson.getAsJsonArray("graphs");

            JsonArray results = new JsonArray();

            for (JsonElement graphElem : graphsArray) {
                JsonObject graphObj = graphElem.getAsJsonObject();
                int graphId = graphObj.get("id").getAsInt();
                JsonArray nodesArray = graphObj.getAsJsonArray("nodes");
                JsonArray edgesArray = graphObj.getAsJsonArray("edges");

                Graph graph = new Graph();
                for (JsonElement node : nodesArray) {
                    graph.addNode(node.getAsString());
                }
                for (JsonElement edgeElem : edgesArray) {
                    JsonObject edgeObj = edgeElem.getAsJsonObject();
                    graph.addEdge(
                        edgeObj.get("from").getAsString(),
                        edgeObj.get("to").getAsString(),
                        edgeObj.get("weight").getAsInt()
                    );
                }

                JsonObject resultObj = new JsonObject();
                resultObj.addProperty("graph_id", graphId);

                JsonObject inputStats = new JsonObject();
                inputStats.addProperty("vertices", graph.getVertexCount());
                inputStats.addProperty("edges", graph.getEdgeCount());
                resultObj.add("input_stats", inputStats);

                // Prim
                PrimAlgorithm.Result primResult = PrimAlgorithm.findMST(graph);
                resultObj.add("prim", toJson(primResult));

                // Kruskal
                KruskalAlgorithm.Result kruskalResult = KruskalAlgorithm.findMST(graph);
                resultObj.add("kruskal", toJson(kruskalResult));

                results.add(resultObj);
            }

            JsonObject output = new JsonObject();
            output.add("results", results);

            try (FileWriter writer = new FileWriter("output.json")) {
                gson.toJson(output, writer);
            }

            System.out.println("MST computation completed. Output saved to output.json");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JsonObject toJson(PrimAlgorithm.Result r) {
        return toJson(r.mstEdges, r.totalCost, r.operationsCount, r.executionTimeMs);
    }

    private static JsonObject toJson(KruskalAlgorithm.Result r) {
        return toJson(r.mstEdges, r.totalCost, r.operationsCount, r.executionTimeMs);
    }

    private static JsonObject toJson(List<Edge> edges, int cost, long ops, double time) {
        JsonObject obj = new JsonObject();
        JsonArray arr = new JsonArray();
        for (Edge e : edges) {
            JsonObject edgeJson = new JsonObject();
            edgeJson.addProperty("from", e.getFrom());
            edgeJson.addProperty("to", e.getTo());
            edgeJson.addProperty("weight", e.getWeight());
            arr.add(edgeJson);
        }
        obj.add("mst_edges", arr);
        obj.addProperty("total_cost", cost);
        obj.addProperty("operations_count", ops);
        obj.addProperty("execution_time_ms", String.format("%.2f", time));
        return obj;
    }
}