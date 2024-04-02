package Models;

import static org.junit.jupiter.api.Assertions.*;
import Models.GraphNode;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class GraphNodeTest {

    GraphNode nodeA, nodeB;

    @BeforeEach
    void setUp() {

        nodeA = new GraphNode("Node A", 0, 0, null);
        nodeB = new GraphNode("Node B", 3, 4, null);
    }

    @Test
    void testNodeProperties() {
        assertEquals("Node A", nodeA.getName());
        assertEquals(0, nodeA.getX());
        assertEquals(0, nodeA.getY());
        assertNull(nodeA.getIcon());
    }

    @Test
    void testConnectToNodeUndirected() {
        nodeA.connectToNodeUndirected(nodeB);
        assertTrue(nodeA.getLinks().contains(nodeB));
        assertTrue(nodeB.getLinks().contains(nodeA));
    }


    @Test
    void testListToHashMap() {
        nodeA.connectToNodeUndirected(nodeB);
        nodeA.listToHashMap();
        assertTrue(nodeA.adjacencies.containsKey(nodeB));
        assertEquals(5.0, nodeA.adjacencies.get(nodeB), 0.01);
    }
}