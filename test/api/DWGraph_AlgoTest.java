package api;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {



    @Test
    void isConnected() {
        DWGraph_DS g = new DWGraph_DS();
        NodeData n = new NodeData(0);
        NodeData n1 = new NodeData(1);
        NodeData n2 = new NodeData(2);
        NodeData n3 = new NodeData(3);
        g.connect(0,1,1);
        g.connect(1,2,1);
        g.connect(2,3,1);
        g.connect(3,0,1);
        DWGraph_Algo r = new DWGraph_Algo();
        r.init(g);
        assertEquals(r.isConnected(), true);
        g.removeEdge(1,2);
        DWGraph_Algo w = new DWGraph_Algo();
        w.init(g);
        assertEquals(w.isConnected(), false);


    }

    @Test
    void shortestPathDist() {
        DWGraph_DS g = new DWGraph_DS();
        NodeData n = new NodeData(0);
        NodeData n1 = new NodeData(1);
        NodeData n2 = new NodeData(2);
        NodeData n3 = new NodeData(3);
        g.connect(0,1,1);
        g.connect(1,2,1);
        g.connect(2,3,1);
        g.connect(3,0,1);
        DWGraph_Algo r = new DWGraph_Algo();
        r.init(g);
        assertEquals(r.shortestPathDist(0,2), 2);

    }

    @Test
    void shortestPath() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }

    @Test
    void loadGraph() {
    }
}