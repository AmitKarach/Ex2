package api;

import java.util.*;

public class DWGraph_Algo implements dw_graph_algorithms
{
    directed_weighted_graph graph;
    ArrayList<Double> wieghts =new ArrayList<Double>();
    ArrayList<node_data> parents =new ArrayList<node_data>();
    public DWGraph_Algo ()
    {
        graph = new DWGraph_DS();
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
    public boolean isConnected()
    {
//        if (graph.getV()==null ||graph.getV().size()==0 || graph.getV().size()==1)
//        {
//            return true;
//        }
//        Iterator<node_data> nodes = graph.getV().iterator();
//        while (nodes.hasNext())
//        {
//            node_data start= nodes.next();;
//            if (start !=null)
//            {
//                DJ(start);
//            }
//            for (node_data n: graph.getV())
//            {
//                if (wieghts.get(n.getKey())==Double.MAX_VALUE)
//                {
//                    return false;
//                }
//            }
//            return true;
//        }
//        return false;
        if (graph.getV()==null ||graph.getV().size()==0 || graph.getV().size()==1)
        {
            return true;
        }
        Iterator<node_data> nodes = graph.getV().iterator();
        if (nodes.hasNext())
        {
            node_data start= nodes.next();
            DJ(graph,start);
            for (node_data n: graph.getV())
            {
                if (n.getInfo()=="unvisited")
                {
                    return false;
                }
            }
            directed_weighted_graph RGraph = RCopy();
            DJ(RGraph,start);
            for (node_data n: RGraph.getV())
            {
                if (n.getInfo()=="unvisited")
                {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (src==dest)
        {
            return 0;
        }
        DJ (graph, graph.getNode(src));
        if (wieghts.get(dest)==Double.MAX_VALUE){return -1;}
        return wieghts.get(dest);
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        if (graph.getNode(src)==null ||graph.getNode(dest)==null)
        {
            return null;
        }
        if (src ==dest)
        {
            List<node_data> re= new LinkedList<>();
            re.add(graph.getNode(src));
            return re;
        }
        DJ (graph, graph.getNode(src));
        if (graph.getNode(dest).getTag() ==Integer.MAX_VALUE)
        {
            return null;
        }
        return shortestPath(graph.getNode(dest));

    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }

    private void DJ(directed_weighted_graph g,node_data s)
    {
        wieghts.clear();
        Iterator<node_data> nodes = g.getV().iterator();
        while (nodes.hasNext())
        {
            node_data n = nodes.next();
            wieghts.add(n.getKey(),Double.MAX_VALUE);
            parents.add(n.getKey(), null);
            n.setInfo("unvisited");
        }
        PriorityQueue<node_data> queue = new PriorityQueue<node_data>();
        queue.add(s);
        wieghts.set(s.getKey(),0.0);
        s.setInfo("visited");

        while (!queue.isEmpty())
        {
            node_data current= queue.poll();
            current.setInfo("visited");
            if (g.getE(current.getKey()) != null)
            {
                for (edge_data curEdge : g.getE(current.getKey()))
                {
                    node_data ni =g.getNode(curEdge.getDest());
                    double dis = g.getEdge(current.getKey(), ni.getKey()).getWeight() + wieghts.get(current.getKey());
                    if (dis < wieghts.get(ni.getKey()) && ni.getInfo() == "unvisited")
                    {
                        wieghts.set(ni.getKey(), dis);
                        parents.set(ni.getKey(),current);
                        queue.add(ni);
                    }
                }
            }

        }
    }

    private directed_weighted_graph RCopy() {
        DWGraph_DS newGraph =new DWGraph_DS();
        for (node_data n: graph.getV())
        {
            newGraph.addNode(n);
        }
        for (node_data n: graph.getV())//going through all the nodes in the graph
        {
            for (edge_data ni: graph.getE(n.getKey()))//going through all the neighbors of the node
            {
                newGraph.connect(ni.getDest(), ni.getSrc(), ni.getWeight());
            }
        }
        return newGraph;
    }

    public List<node_data> shortestPath(node_data dest)
    {

        List <node_data> shorti = new LinkedList<>();
        shorti.add(dest);
        node_data current=dest;

        while (parents.get(current.getKey())!=null)
        {

            node_data next = parents.get(current.getKey());
            current=next;
            shorti.add(0,next);
        }
        return shorti;
    }














}
