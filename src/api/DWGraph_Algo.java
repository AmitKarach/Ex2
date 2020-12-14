package api;

import java.io.*;
import java.util.*;

import com.google.gson.Gson;

import api.BoazGraph.BoazNode;
//import api.BoazGraph.BoazEdge;


public class DWGraph_Algo implements dw_graph_algorithms {
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

    public boolean save(String file) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(file)) {

            BoazGraph b =new BoazGraph();
            for (node_data n:graph.getV())
            {
                BoazNode newNode = new BoazNode();
                newNode.id=n.getKey();
                newNode.pos=n.getLocation().x()+","+ n.getLocation().y();
                b.nodes =new ArrayList<>();
                b.edges =new ArrayList<>();
                b.nodes.add(newNode);
                for (edge_data e:graph.getE(n.getKey()))
                {
//                    EdgeData newEdge =new EdgeData(e.getSrc(), e.getDest(), e.getWeight());
//                    newEdge.src =e.getSrc();
//                    newEdge.dest =e.getDest();
//                    newEdge.w =e.getWeight();
                    b.edges.add((EdgeData) e);
                }
            }
            gson.toJson(b, writer);
            writer.flush();
            writer.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {

        Gson gson = new Gson();


        try (Reader reader = new FileReader(file)) {
            BoazGraph g  = gson.fromJson(reader, BoazGraph.class);
                // Convert JSON File to Java Object
            return createGraph(g);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean createGraph(BoazGraph g) {
        for (BoazNode d : g.nodes) {
            NodeData nodeData = new NodeData(d.id);
            String pos = d.pos;
            String[] posSplit = pos.split(",");
            GLocation loc = new GLocation(Double.parseDouble(posSplit[0]), Double.parseDouble(posSplit[1]));
            nodeData.setLocation(loc);
            graph.addNode(nodeData);
        }
        for (EdgeData e : g.edges) {
            graph.connect(e.getSrc(), e.getDest(), e.getWeight());
        }
        return true;
    }

    public boolean loadGraph(String jsonGraph) {

        Gson gson = new Gson();



        BoazGraph g  = gson.fromJson(jsonGraph, BoazGraph.class);
            // Convert JSON File to Java Object

        return createGraph(g);


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

    public ArrayList<node_data> getParents() {
        return parents;
    }


}
