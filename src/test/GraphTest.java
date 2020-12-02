

package ex2.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ex2.src.DS_DWGraph;
import ex2.src.NodeData;
import ex2.src.edge_data;
import ex2.src.node_data;

class GraphTest {


        @Test
        final void testGetNode() {
                //Create Graph...
                DS_DWGraph g = new DS_DWGraph();
                //Create Nodes...
                node_data n = new NodeData(2);
                node_data n1 = new NodeData(4);
                node_data n2 = new NodeData(20);
                // Add Nodes to G
                g.addNode(n);
                g.addNode(n1);
                g.addNode(n2);
                // Check
                assertEquals(g.getNode(n.getKey()), n);
                assertEquals(g.getNode(n1.getKey()), n1);

        }

        @Test
        final void testGetEdge() {
                DS_DWGraph g = new DS_DWGraph();

                node_data n = new NodeData(2);
                node_data n1 = new NodeData(4);
                node_data n2 = new NodeData(20);

                g.addNode(n);
                g.addNode(n1);
                g.addNode(n2);

                g.connect(n.getKey(), n1.getKey(), 0);
                //g.connect(n.getKey(), n2.getKey(), 0);
                g.connect(n2.getKey(), n1.getKey(), 0);

                assertEquals(g.getEdge(n.getKey(), n1.getKey()).getSrc(), n.getKey());
                assertEquals(g.getEdge(n.getKey(), n1.getKey()).getDest(), n1.getKey());
        }
//        //assertEquals(g.getEdge(n.getKey(), n2.getKey()).getSrc(),n.getKey()); //if n, n2 are not connected
//        //assertEquals(g.getEdge(n.getKey(), n2.getKey()).getDest(),n2.getKey());
//
//    }
//
    @Test
    final void testAddNode() {
            DS_DWGraph g = new DS_DWGraph();
        node_data n = new NodeData(2);
        g.addNode(n);
        assertEquals(g.getNode(n.getKey()), n);
    }
//
    @Test
    final void testConnect() {
            DS_DWGraph g = new DS_DWGraph();
        node_data n = new NodeData(2);
        node_data n1 = new NodeData(4);
        node_data n2 = new NodeData(21);

        g.addNode(n);
        g.addNode(n1);
        g.addNode(n2);

        g.connect(n1.getKey(), n2.getKey(), 0);

        assertEquals(g.getEdge(n1.getKey(), n2.getKey()).getSrc(), n1.getKey());

        edge_data e1 = g.getEdge(n.getKey(), n2.getKey()); //n, n2 are not connected

        assertEquals(null, e1);

    }
//
    @Test
    final void testGetV() {
            DS_DWGraph g = new DS_DWGraph();

        assertTrue(g.getV().size() == g.nodeSize());
    }
//
//
    @Test
    final void testRemoveNode() {
            DS_DWGraph g = new DS_DWGraph();
        node_data n = new NodeData(2);
        g.addNode(n);

        g.removeNode(n.getKey());

        assertTrue(g.getNode(n.getKey()) == null);
    }

    @Test
    final void testRemoveEdge() {
            DS_DWGraph g = new DS_DWGraph();
        node_data n = new NodeData(2);
        node_data n1 = new NodeData(4);

        g.addNode(n);
        g.addNode(n1);

        g.connect(n.getKey(), n1.getKey(), 0);

        g.removeEdge(n.getKey(), n1.getKey());

        assertTrue(g.getEdge(n.getKey(), n1.getKey()) == null);
    }

    @Test
    final void testNodeSize() {
            DS_DWGraph g = new DS_DWGraph();
        node_data n = new NodeData(2);
        node_data n1 = new NodeData(4);

        g.addNode(n);
        g.addNode(n1);

        assertEquals(g.nodeSize(), 2);

        g.removeNode(n.getKey()); //remove one vertex

        assertEquals(g.nodeSize(), 1);
    }

    @Test
    final void testEdgeSize() {
            DS_DWGraph g = new DS_DWGraph();
        node_data n = new NodeData(2);
        node_data n1 = new NodeData(4);
        node_data n2 = new NodeData(21);

        g.addNode(n);
        g.addNode(n1);
        g.addNode(n2);

        g.connect(n.getKey(), n1.getKey(), 0);
        g.connect(n1.getKey(), n2.getKey(), 0);

        assertEquals(g.edgeSize(), 2);

        g.removeEdge(n.getKey(), n1.getKey()); //remove one edge

        assertEquals(g.edgeSize(), 1);
    }

    @Test
    final void testGetMC() {
            DS_DWGraph g = new DS_DWGraph();
        node_data n = new NodeData(2);
        g.addNode(n);

        assertEquals(g.getMC(), 1);

        g.removeNode(n.getKey());

        assertEquals(g.getMC(), 2);


    }

        }


