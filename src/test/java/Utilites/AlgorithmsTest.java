package Utilites;

import Models.GraphNode;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class AlgorithmsTest {

    private List<GraphNode> nodes;
    private Set<GraphNode> avoidSet;

    @BeforeEach
    public void setUp() {
        nodes = new ArrayList<>();
        avoidSet = new HashSet<>();

        GraphNode nodeA = new GraphNode("A", 0, 0, null,5);
        GraphNode nodeB = new GraphNode("B", 10, 10, null,5);
        GraphNode nodeC = new GraphNode("C", 20, 20, null,5);
        GraphNode nodeD = new GraphNode("D", 30, 30, null,5);

        nodeA.connectToNodeUndirected(nodeB);
        nodeB.connectToNodeUndirected(nodeC);
        nodeC.connectToNodeUndirected(nodeD);

        nodes.addAll(Arrays.asList(nodeA, nodeB, nodeC, nodeD));
    }

    @Test
    public void testBFSAlgorithm() {
        List<GraphNode> path = Algorithms.BFSAlgorithm(nodes.get(0), nodes.get(3), avoidSet);
        assertEquals(4, path.size());
        assertTrue(path.containsAll(nodes));
    }

    @Test
    public void testDFSAlgorithm() {
        List<GraphNode> path = Algorithms.DFSAlgorithm(nodes.get(0), nodes.get(3), avoidSet);
        assertNotNull(path);
    }

    @Test
    public void testDijkstraAlgorithm() {
        LinkedList<GraphNode> path = Algorithms.dijkstraAlgorithm(nodes.get(0), nodes.get(3), avoidSet,true);
        assertNotNull(path);
        assertEquals( 4, path.size());
    }

    @Test
    public void testBFSWithPOIs() {
        List<GraphNode> POIs = new ArrayList<>(Arrays.asList(nodes.get(1), nodes.get(2)));
        List<GraphNode> path = Algorithms.BFSWithPOIs(nodes.get(0), nodes.get(3), POIs, avoidSet);
        assertTrue(path.containsAll(POIs));
        assertEquals(Arrays.asList(nodes.get(0), nodes.get(3)), Arrays.asList(path.get(0), path.get(path.size() - 1)));
    }
    @Test
    public void testDFSWithPOIs() {
        List<GraphNode> POIs = new ArrayList<>(Arrays.asList(nodes.get(1), nodes.get(2)));
        List<GraphNode> path = Algorithms.DFSPOIs(nodes.get(0), nodes.get(3), POIs, avoidSet);
        assertTrue(path.containsAll(POIs));
        assertEquals(Arrays.asList(nodes.get(0), nodes.get(3)), Arrays.asList(path.get(0), path.get(path.size() - 1)));
    }
    @Test
    public void testDijWithPOIs() {
        List<GraphNode> POIs = new ArrayList<>(Arrays.asList(nodes.get(1), nodes.get(2)));
        List<GraphNode> path = Algorithms.dijkstraWithPOIs(nodes.get(0), nodes.get(3), POIs, avoidSet,true);
        assertTrue(path.containsAll(POIs));
        assertEquals(Arrays.asList(nodes.get(0), nodes.get(3)), Arrays.asList(path.get(0), path.get(path.size() - 1)));
    }


    @Test
    public void testNoPathScenario() {
        avoidSet.add(nodes.get(1));
        List<GraphNode> pathBFS = Algorithms.BFSAlgorithm(nodes.get(0), nodes.get(3), avoidSet);
        List<GraphNode> pathDFS = Algorithms.DFSAlgorithm(nodes.get(0), nodes.get(3), avoidSet);
        LinkedList<GraphNode> pathDijkstra = Algorithms.dijkstraAlgorithm(nodes.get(0), nodes.get(3), avoidSet,true);

        assertTrue(pathBFS.isEmpty());
        assertNull(pathDFS);
        assertNull( pathDijkstra);
    }


}
