package api;

import java.util.List;

public class DWGraph_Algo implements dw_graph_algorithms
{
    directed_weighted_graph graph;

    public DWGraph_Algo (directed_weighted_graph g)
    {
        graph = g;
    }

    @Override
    public void init(directed_weighted_graph g) {
        graph=g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return graph;
    }

    @Override
    public directed_weighted_graph copy() {
        DWGraph_DS newGraph =new DWGraph_DS();
        for (node_data n: graph.getV())
        {
            newGraph.addNode(n);
        }
        for (node_data n: graph.getV())//going through all the nodes in the graph
        {
            for (edge_data ni: graph.getE(n.getKey()))//going through all the neighbors of the node
            {
                graph.connect(ni.getSrc(), ni.getDest(), ni.getWeight());
            }
        }
        return newGraph;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
