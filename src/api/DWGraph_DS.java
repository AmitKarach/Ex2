package api;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import api.EdgeData;

public class DWGraph_DS implements directed_weighted_graph, Serializable {
    private final HashMap<Integer, node_data> Vertices;
    private final HashMap<Integer, HashMap<Integer, edge_data>> Edges;
    private int edgesCounter;
    private int mc;

    public DWGraph_DS() {
        Vertices = new HashMap<Integer, node_data>();
        Edges = new HashMap<Integer, HashMap<Integer, edge_data>>();
        edgesCounter = 0;
        mc = 0;

    }

    /**
     * this function returns the node with this given key
     * @param key - the node_id
     * @return the node, if this node is not in the graph return null
     */
    @Override
    public node_data getNode(int key) {
        if (Vertices.get(key) != null) {
            return Vertices.get(key);
        }
        return null;
    }

    /**
     * this function check to see if there is an edge between two given keys (nodes)
     * @param src- the first node
     * @param dest-the second node
     * @return the edge
     */
    // More Elegant approach with try & Catch
    @Override
    public edge_data getEdge(int src, int dest) {
        if (Edges.containsKey(src) && Edges.get(src).containsKey(dest)) {
            return Edges.get(src).get(dest);
        }
        return null;

    }

    /**
     * adds a node to the graph
     * @param n-the node
     */
    @Override
    public void addNode(node_data n) throws RuntimeException {
        if (Vertices.get(n.getKey()) == null) {
            Vertices.put(n.getKey(), n);
            Edges.put(n.getKey(), new HashMap<Integer, edge_data>());

        }
        mc++;
    }

    /**
     * this function connects two nodes with a given wight, if there is already an edge
     * between this two nodes its checks if the w is different from the wight there is
     * now and if so it changes the wight to the given w
     * if one of the nodes is not in the graph this function does nothing
     * @param src the first node
     * @param dest-the second node
     * @param w- the weight of the edge
     */
    @Override
    public void connect(int src, int dest, double w) {
        if (src == dest||Vertices.get(src)==null||Vertices.get(dest)==null) {
            return;
        }
        if (Edges.get(src) != null) {
            if (Edges.get(src).containsKey(dest) != true) {
                Edges.get(src).put(dest, new EdgeData(src, dest, w));
                edgesCounter++;
            } else {
                if (Edges.get(src).get(dest).getWeight() != w) {
                    Edges.get(src).remove(dest);
                    Edges.get(src).put(dest, new EdgeData(src, dest, w));
                    edgesCounter++;
                }
            }
        } else {
            Edges.put(src, new HashMap<Integer, edge_data>());
            Edges.get(src).put(dest, new EdgeData(src, dest, w));
            edgesCounter++;
        }

    }

    /**
     *gets all the nodes int he graph
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_data> getV() {
        return Vertices.values();
    }

    /**
     * gets all the neighbors of the node
     * @param node_id -the node
     * @return Collection<edge_data>
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        return Edges.get(node_id).values();

    }

    /**
     * this function removes a given node from the graph
     * this mean we go to all the neighbors of the given node and delete the given node
     * from their list of neighbors and then deletes the node ftom the graph and the
     * edges list
     * @param key-the key of the node we want to delete
     * @return the deleted node
     */
    @Override
    public node_data removeNode(int key) {
        if (Vertices.get(key) == null) {
            return null;
        }
        if (Edges.containsKey(key)) {
            for (int k : Edges.keySet()) {
                if (Edges.get(k).containsKey(key)) {
                    Edges.get(k).remove(key);
                    edgesCounter--;
                    mc++;
                }
            }
        }
        Edges.remove(key);
        mc++;
        node_data n = Vertices.get(key);
        Vertices.remove(key);
        return n;
    }

    /**
     *remves the edge between two nodes
     * @param src the src
     * @param dest the dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        if (Vertices.get(src) == null || Vertices.get(dest) == null) {
            return null;
        }
        if (Edges.containsKey(src)) {
            edge_data toReturn = Edges.get(src).remove(dest);
            if (toReturn != null) {
                this.edgesCounter--;
                mc++;
            }
            return toReturn;
        }
        return null;

    }

    /**
     *number of nodes in the graph
     * @return number of nodes
     */
    @Override
    public int nodeSize() {
        return Vertices.size();
    }

    /**
     * @return the number of edges in the graph
     */
    @Override
    public int edgeSize() { // every edge is an obj so might be smart to just count edge node...
        return Edges.size();
    }

    /**
     * @return the mc
     */
    @Override
    public int getMC() {
        return mc;
    }
}
