package api;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {

    @Test
    void copy() {
        DWGraph_DS g = new DWGraph_DS();
        NodeData n = new NodeData(0);
        NodeData n1 = new NodeData(1);
        NodeData n2 = new NodeData(2);
        NodeData n3 = new NodeData(3);
        g.addNode(n);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.connect(0, 1, 1);
        g.connect(1, 2, 1);
        g.connect(2, 3, 1);
        g.connect(3, 0, 1);
        DWGraph_Algo r = new DWGraph_Algo();
        r.init(g);
        DWGraph_DS g2 = (DWGraph_DS) r.copy();
        assertEquals(g.nodeSize(),g2.nodeSize());
        assertEquals(g.edgeSize(),g2.edgeSize());

        boolean a = true;
        for (int i = 0; i <g.nodeSize();i++) {
            if (g.getNode(i).getKey() != g2.getNode(i).getKey())
                a = false;
        }
       assertEquals(a,true);
        boolean b = true;
        for (int i = 0; i <g.nodeSize();i++) {
            if (!g.getE(i).containsAll(g2.getE(i)) )
                b = false;
        }
        assertEquals(b,true);
    }
    @Test
    void isConnected() {
        DWGraph_DS g = new DWGraph_DS();
        NodeData n = new NodeData(0);
        NodeData n1 = new NodeData(1);
        NodeData n2 = new NodeData(2);
        NodeData n3 = new NodeData(3);
        g.addNode(n);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.connect(0, 1, 1);
        g.connect(1, 2, 1);
        g.connect(2, 3, 1);
        g.connect(3, 0, 1);
        DWGraph_Algo r = new DWGraph_Algo();
        r.init(g);
        assertEquals(r.isConnected(), true);
        g.removeEdge(1, 2);
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
        g.addNode(n);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);

        g.connect(0, 1, 1);
        g.connect(1, 2, 1);
        g.connect(2, 3, 1);
        g.connect(3, 0, 1);
        DWGraph_Algo r = new DWGraph_Algo();
        r.init(g);
        assertEquals(r.shortestPathDist(0, 2), 2);
        assertEquals(r.shortestPathDist(0, 0), 0);
        assertEquals(r.shortestPathDist(0, 5), -1);


    }

    @Test
    void shortestPath() {
        DWGraph_DS g = new DWGraph_DS();

        NodeData n = new NodeData(0);
        NodeData n1 = new NodeData(1);
        NodeData n2 = new NodeData(2);
        NodeData n3 = new NodeData(3);
        g.addNode(n);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);

        g.connect(0, 1, 1);
        g.connect(1, 2, 1);
        g.connect(2, 3, 1);
        g.connect(3, 0, 1);
        DWGraph_Algo r = new DWGraph_Algo();
        r.init(g);
        List<node_data> anw = new ArrayList<node_data>();
        anw.add(n);
        anw.add(n1);
        anw.add(n2);
        List<node_data> tst = r.shortestPath(0, 2);
        assertEquals(anw.containsAll(tst), true);
        List<node_data> tst2 = r.shortestPath(0, 3);
        assertEquals(anw.containsAll(tst2), false);
    }

    @Test
    void saveLoad() {
        DWGraph_DS g = new DWGraph_DS();

        NodeData n = new NodeData(0);
        NodeData n1 = new NodeData(1);
        NodeData n2 = new NodeData(2);
        NodeData n3 = new NodeData(3);
        g.addNode(n);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);

        g.connect(0, 1, 1);
        g.connect(1, 2, 1);
        g.connect(2, 3, 1);
        g.connect(3, 0, 1);
        DWGraph_Algo r = new DWGraph_Algo();
        r.init(g);
        assertTrue(r.save("graph.obj"));
        DWGraph_Algo r2 = new DWGraph_Algo();
        assertTrue(r2.save("graph.obj"));
    }


}