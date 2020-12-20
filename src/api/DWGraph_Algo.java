package api;

import java.io.*;
import java.util.*;

import com.google.gson.Gson;

import api.BoazGraph.BoazNode;



public class DWGraph_Algo implements dw_graph_algorithms {
    directed_weighted_graph graph;
    ArrayList<Double> wieghts = new ArrayList<>();
    ArrayList<node_data> parents = new ArrayList<>();

    public DWGraph_Algo() {
        graph = new DWGraph_DS();
    }
    /**
     * the builder of the WGraph_Algo
     * gets a weighted_graph and does a shallow copy to our graph
     * @param g- a weighted_graph to init our graph with
     */
    @Override
    public void init(directed_weighted_graph g) {
        graph = g;
    }
    /**
     * this function returns the graph
     * @return weighted_graph-graph
     */
    @Override
    public directed_weighted_graph getGraph() {
        return graph;
    }

    /**
     * this function does a deep copy to our graph to a new graph
     * @return the new graph
     */
    @Override
    public directed_weighted_graph copy() {
        DWGraph_DS newGraph = new DWGraph_DS();
        for (node_data n : graph.getV()) {
            newGraph.addNode(n);
        }
        for (node_data n : graph.getV())//going through all the nodes in the graph
        {
            for (edge_data ni : graph.getE(n.getKey()))//going through all the neighbors of the node
            {
                graph.connect(ni.getSrc(), ni.getDest(), ni.getWeight());
            }
        }

        return newGraph;
    }

    /**
     * this function checks if all the nodes of the graph are connected
     * after using the DJ function this function goes over every node and
     * checks if his tag is not Integer.Max_Value because the function DJ
     * goes over all the nodes that are connected to a certion node and give them
     * tag that, if DJ dident give this node a tag that means he is not connected to
     * the node and that means that the graph is not connected
     * @return true if the graph is all connected
     */
    @Override
    public boolean isConnected() {
        if (graph.getV() == null || graph.getV().size() == 0 || graph.getV().size() == 1) {
            return true;
        }
        Iterator<node_data> nodes = graph.getV().iterator();
        if (nodes.hasNext()) {
            node_data start = nodes.next();
            DJ(graph, start);
            for (node_data n : graph.getV()) {
                if (n.getInfo() == "unvisited") {
                    return false;
                }
            }
            directed_weighted_graph RGraph = RCopy();
            DJ(RGraph, start);
            for (node_data n : RGraph.getV()) {
                if (n.getInfo() == "unvisited") {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * this function returns the shortest distance between this two nodes
     * using the DJ function that does that
     * @param src - start node
     * @param dest - end (target) node
     * @return the smallest wight of all the edges between them
     */
    @Override
    public double shortestPathDist(int src, int dest) {

        if (graph.getNode(src) == null || graph.getNode(dest) == null) {
            return -1;
        }
        if (src == dest) {
            return 0;
        }
        DJ(graph, graph.getNode(src));
        if (wieghts.get(dest) == Double.MAX_VALUE) {
            return -1;
        }
        return wieghts.get(dest);
    }

    /**
     * this function returns all the nodes that are in the smallest path
     * between this nodes (when src is the first in the list and dest is the last)
     * using the DJ function to map the graph and shortestPath that gets only the dest
     * node (node_info)
     * @param src - start node
     * @param dest - end (target) node
     * @return a LinkedList contains all the nodes in the smallest path between this two nodes
     * returns null if there is no path or if there are no nodes like that in the graph
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
//        wieghts = setWieghts();
//        parents = setParents();
        if (graph.getNode(src) == null || graph.getNode(dest) == null) {
            return null;
        }
        if (src == dest) {
            List<node_data> re = new LinkedList<>();
            re.add(graph.getNode(src));
            return re;
        }
        DJ(graph, graph.getNode(src));
        if (graph.getNode(dest).getTag() == Integer.MAX_VALUE) {
            return null;
        }
        return shortestPath(graph.getNode(dest));

    }
    /**
     * saves the graph (object) to computer in the given file name
     * @param file - the file name (may include a relative path).
     * @return true if it could save the graph
     */
    public boolean save(String file) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(file)) {

            BoazGraph b = new BoazGraph();
            for (node_data n : graph.getV()) {
                BoazNode newNode = new BoazNode();
                newNode.id = n.getKey();
                if (n.getLocation() == null) {
                    newNode.pos = "null";
                } else {
                    newNode.pos = n.getLocation().x() + "," + n.getLocation().y();
                }
                b.nodes = new ArrayList<>();
                b.edges = new ArrayList<>();
                b.nodes.add(newNode);
                for (edge_data e : graph.getE(n.getKey())) {

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
            BoazGraph g = gson.fromJson(reader, BoazGraph.class);
            // Convert JSON File to Java Object
            return createGraph(g);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * this function creats a graph from the json we get
     * @param g- the boaz jason graph
     * @return true if it created a graph
     */
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

    /**
     * loads a graph from a String json
     * @param jsonGraph string json we get
     * @return true if creats a graph
     */
    public synchronized boolean loadGraph(String jsonGraph) {

        Gson gson = new Gson();


        BoazGraph g = gson.fromJson(jsonGraph, BoazGraph.class);
        // Convert JSON File to Java Object

        return createGraph(g);


    }
    /**
     * this function starts by giving all the nodes in the graph tag= Integer.Max_Value and changing all the nodes info
     * to "unvisited"
     * after that he gives the given starting node tag value of 0 and chang his info to "visited" and the goes through all the
     * neighbors of each node starting with our starting node and giving them the min distance between them and the
     * starting node (the distance is the smallest wight of edges between them and the starting node)
     * after standing on a node we will change his info to "visited" which means we found the shortest path to him and
     * we dont need to check for a smaller path for him down the road.
     * i am using here a PriorityQueue
     * @param s- the starting node
     */
    private void DJ(directed_weighted_graph g, node_data s) {
        wieghts.clear();
        parents.clear();
        Iterator<node_data> nodes = g.getV().iterator();
        while (nodes.hasNext()) {
            node_data n = nodes.next();
            wieghts.add(n.getKey(), Double.MAX_VALUE);
            parents.add(n.getKey(), null);
            n.setInfo("unvisited");
        }
        PriorityQueue<node_data> queue = new PriorityQueue<node_data>();
        queue.add(s);
        wieghts.set(s.getKey(), 0.0);
        s.setInfo("visited");

        while (!queue.isEmpty()) {
            node_data current = queue.poll();
            current.setInfo("visited");
            if (g.getE(current.getKey()) != null) {
                for (edge_data curEdge : g.getE(current.getKey())) {
                    node_data ni = g.getNode(curEdge.getDest());
                    double dis = g.getEdge(current.getKey(), ni.getKey()).getWeight() + wieghts.get(current.getKey());
                    if (dis < wieghts.get(ni.getKey()) && ni.getInfo() == "unvisited") {
                        wieghts.set(ni.getKey(), dis);
                        ni.setWeight(dis);
                        parents.set(ni.getKey(), current);
                        queue.add(ni);
                    }
                }
            }

        }
    }

    /**
     * copying the graph but revers all the edges
     * @return the revers graph
     */
    private directed_weighted_graph RCopy() {
        DWGraph_DS newGraph = new DWGraph_DS();
        for (node_data n : graph.getV()) {
            newGraph.addNode(n);
        }
        for (node_data n : graph.getV())//going through all the nodes in the graph
        {
            for (edge_data ni : graph.getE(n.getKey()))//going through all the neighbors of the node
            {
                newGraph.connect(ni.getDest(), ni.getSrc(), ni.getWeight());
            }
        }
        return newGraph;
    }

    /**
     * creats a list of the shortest path
     * @param dest the destanition node
     * @return the list
     */
    private List<node_data> shortestPath(node_data dest) {

        List<node_data> shorti = new LinkedList<>();
        shorti.add(dest);
        node_data current = dest;

        while (parents.get(current.getKey()) != null) {

            node_data next = parents.get(current.getKey());
            current = next;
            shorti.add(0, next);
        }
        return shorti;
    }

}
