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
     * returns the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_data getNode(int key) {
        if (Vertices.get(key) != null) {
            return Vertices.get(key);
        }
        return null;
    }

    /**
     * returns the data of the edge (src,dest), null if none.
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return
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
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     *
     * @param n
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
     * Connects an edge with weight w between node src to node dest.
     * * Note: this method should run in O(1) time.
     *
     * @param src  - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the cost (aka time, price, etc) between src-->dest.
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
     * This method returns a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_data> getV() {
        return Vertices.values();
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the edges getting out of
     * the given node (all the edges starting (source) at the given node).
     * Note: this method should run in O(k) time, k being the collection size.
     *
     * @param node_id
     * @return Collection<edge_data>
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        return Edges.get(node_id).values();

    }

    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(k), V.degree=k, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
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
     * Deletes the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
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
     * Returns the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return Vertices.size();
    }

    /**
     * Returns the number of edges (assume directional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() { // every edge is an obj so might be smart to just count edge node...
        return Edges.size();
    }

    /**
     * Returns the Mode Count - for testing changes in the graph.
     *
     * @return
     */
    @Override
    public int getMC() {
        return mc;
    }
}
