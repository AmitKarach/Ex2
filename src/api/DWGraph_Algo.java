package api;

import java.io.*;
import java.util.List;

import api.*;
import com.google.gson.Gson;


public class DWGraph_Algo implements dw_graph_algorithms {
    directed_weighted_graph graph;

    @Override
    public void init(directed_weighted_graph g) {
        graph = g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return graph;
    }

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

    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(graph, writer);
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

            // Convert JSON File to Java Object
            this.graph = gson.fromJson(file,  DWGraph_DS.class);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
