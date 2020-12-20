package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeDataTest {



    @Test
    void compareTo() {
        GLocation g = new GLocation(2,3,4);
        NodeData n = new NodeData(0,4,g,1);
        NodeData n1 = new NodeData(0,5,g,1);
        assertEquals(n.compareTo(n1), -1);
        n.setWeight(5);
        n1.setWeight(4);
        assertEquals(n.compareTo(n1), 1);
        n.setWeight(5);
        n1.setWeight(5);
        assertEquals(n.compareTo(n1), 0);
    }

    @Test
    void testToString() {
        GLocation g = new GLocation(2,3,4);
        NodeData n = new NodeData(0,4,g,1);
        System.out.println(n.toString());
    }
}