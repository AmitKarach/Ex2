package ex2.src;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class DS_DWGraph implements directed_weighted_graph, Serializable {
    private HashMap<Integer,node_data> Vertices;
    private HashMap<Integer,HashMap<Integer,edge_data>> Edges;
    private int mc,edgecount,id;


    public DS_DWGraph ()
    {
        Vertices=new HashMap<>();
        Edges=new HashMap<>();
        id=0;
    }
    /**
     * returns the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_data getNode(int key)
        {
            if(Vertices.get(key) != null){
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
    @Override
    public edge_data getEdge(int src, int dest) {
        try {
            return Edges.get(src).get(dest);
        }
        catch(Exception e) {
            return null;
        }
    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     *
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        {
            id++;
            int sizeBefore = this.nodeSize();
            this.Vertices.put(id,new NodeData(new GLocation(n.getLocation()),id));
            this.Edges.put(id,new HashMap<Integer,edge_data>());
            if(this.nodeSize() -1 == sizeBefore) {this.mc++;}
        }
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
        if(this.getNode(src)==null || this.getNode(dest)==null || w<0 || src == dest) return;
        int sizeBefore = this.edgeSize();
        try {
            if(this.Edges.get(src).get(dest)==null)
                this.edgecount++;
            this.Edges.get(src).put(dest, new EdgeData(src,dest,w));
        }
        catch(NullPointerException e){
            Edges.put(src,new HashMap<Integer,edge_data>());
            Edges.get(src).put(dest, new EdgeData(src,dest,w));
            edgecount++;
        }
        if(this.edgeSize() -1 == sizeBefore) {this.mc++;}
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
        return this.Vertices.values();
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
        node_data nodeToRemove = this.getNode(key);
        if(nodeToRemove != null) {
            for (Iterator<node_data> iterator = this.getV().iterator(); iterator.hasNext();) {
                node_data v = (node_data) iterator.next();
                this.removeEdge(v.getKey(), key);
            }
            if(this.Edges.get(key)!=null) {
                this.edgecount-=this.Edges.get(key).size();
                this.mc+=this.Edges.get(key).size();
            }
            this.Vertices.remove(key);
            this.Edges.remove(key);
            this.mc++;
        }

        return nodeToRemove;
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
        edge_data tempremove = this.getEdge(src,dest);
        if (tempremove!=null){
            Edges.get(src).remove(dest); // Because weighted graph, discconect one direction
            edgecount-=1;
            mc++;
        }

        return tempremove;
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
        return edgecount;
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
