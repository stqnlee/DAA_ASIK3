package com.mst;

import com.mst.model.Graph;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void testConnectedGraph() {
        Graph g = new Graph();
        g.addEdge("A", "B", 1);
        g.addEdge("B", "C", 2);
        assertTrue(g.isConnected());
    }

    @Test
    void testDisconnectedGraph() {
        Graph g = new Graph();
        g.addNode("A");
        g.addNode("B");
        assertFalse(g.isConnected());
    }
}