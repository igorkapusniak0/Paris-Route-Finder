package Utilites;

import Models.GraphNode;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import static org.junit.jupiter.api.Assertions.*;

class APITest {

    private ArrayList<GraphNode> POIs;

    @BeforeEach
    void setUp() {
        // Setup test data - creating some POIs with varying distances
        POIs = new ArrayList<>();
        POIs.add(new GraphNode("POI1", 0, 0, null,0));
        POIs.add(new GraphNode("POI2", 30, 40, null,0));
        POIs.add(new GraphNode("POI3", 200, 0, null,0));
        POIs.add(new GraphNode("POI4", 300, 0, null,0));
        POIs.add(new GraphNode("POI5", 500, 500, null,0));
    }
    @Test
    void testMatchesColour() {
        assertTrue(API.matchesColour(Color.WHITE, Color.rgb(255, 255, 255), 0.01));
        assertTrue(API.matchesColour(Color.rgb(250, 250, 250), Color.WHITE, 0.02));
        assertFalse(API.matchesColour(Color.BLACK, Color.WHITE, 0.1));
    }

    @Test
    void testReadInDatabase() {
        ArrayList<GraphNode> result = API.readInDatabase();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testPoiLinks() {
        ArrayList<GraphNode> linkedPOIs = API.poiLinks(POIs);
        assertNotNull(linkedPOIs);
        assertEquals(5, linkedPOIs.size());

        assertTrue(linkedPOIs.get(0).getLinks().contains(linkedPOIs.get(1)));
        assertTrue(linkedPOIs.get(0).getLinks().contains(linkedPOIs.get(2)));
        assertTrue(linkedPOIs.get(2).getLinks().contains(linkedPOIs.get(3)));
        assertEquals(0, linkedPOIs.get(4).getLinks().size());
    }


}
