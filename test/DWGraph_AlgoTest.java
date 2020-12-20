package test;

import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.NodeData;
import api.node_data;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {

    @Test
    void isConnected()
    {
        DWGraph_Algo g =new DWGraph_Algo();
        g.init(accurateGraph());
        assertTrue(g.isConnected());
    }

    @Test
    void shortestPathD ()
    {

        DWGraph_Algo g =new DWGraph_Algo();
        g.init(accurateGraph());

        double d =g.shortestPathDist(0,3);
        assertEquals(8,d);

    }
    @Test
    void shortestPath ()
    {
        DWGraph_Algo g =new DWGraph_Algo();
        g.init(accurateGraph());

        List<node_data> get= g.shortestPath(0,3);
        int [] check ={0,1,4,3};
        int i=0;
        for (node_data n:get)
        {
            assertEquals(n.getKey(),check[i]);
            i++;
        }
    }



    private DWGraph_DS accurateGraph () {
        DWGraph_DS graph = new DWGraph_DS();
        for (int i = 0; i < 5; i++) {
            node_data n = new NodeData(i);
            graph.addNode(n);
        }
        graph.connect(0, 1, 1);
        graph.connect(0, 2, 2);
        graph.connect(1, 4, 1);
        graph.connect(2, 3, 30);
        graph.connect(3, 0, 5);
        graph.connect(4, 3, 6);


        return graph;
    }
}