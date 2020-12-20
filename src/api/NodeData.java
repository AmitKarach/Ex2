package api;

import com.google.gson.annotations.SerializedName;

/**
 * this will reprisent our nodes in the graph
 */
public class NodeData implements node_data, Comparable {
    /**
     * every node have a few fileds :
     * the key- the i of the node
     * the weight -the weight of the node
     * and the location gl of the node
     */
    @SerializedName(value = "id")
    private final int key;
    private int tag;
    private String info;
    private double wei;
    @SerializedName(value = "pos")
    private GLocation gl;


    public NodeData(int k) {
        key = k;
    }

    public NodeData(int k, double weight, GLocation location) {
        key = k;
        wei = weight;
        gl = location;
    }

    public NodeData(int k, double weight, GLocation location, int ta) {
        key = k;
        wei = weight;
        gl = location;
        tag = ta;
    }

    public NodeData(int k, double weight) {
        key = k;
        wei = weight;
    }

    /**
     * @return the id
     */
    @Override
    public int getKey() {
        return key;
    }

    /**
     * @return the location of the node
     */
    @Override
    public geo_location getLocation() {
        if (gl != null)
            return gl;
        return null;
    }

    /**
     * changes the location of the node
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(geo_location p) {
        gl = (GLocation) p;
    }

    /**
     * @return prints the node
     */
    @Override
    public String toString() {
        return "NodeData{" +
                "key=" + key +
                ", tag=" + tag +
                ", info='" + info + '\'' +
                ", wei=" + wei +
                ", gl=" + gl +
                '}';
    }

    /**
     * @return the weight of the node
     */
    @Override
    public double getWeight() {
        return wei;
    }

    /**
     * changes the weight of the node
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        wei = w;
    }

    /**
     * @return the node info
     */
    @Override
    public String getInfo() {
        return info;
    }

    /**
     *changes the nodes info
     * @param s- the new info
     */
    @Override
    public void setInfo(String s) {
        info = s;
    }

    /**
     * @return the tag
     */
    @Override
    public int getTag() {
        return tag;
    }

    /**
     * changes the tag
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        tag = t;
    }

    /**
     * cheacks if two nodes are bigger
     * @param o the other node
     * @return -1 if the other node is bigger
     * 1 if this node is bigger
     * 0 if they are equals
     */
    @Override
    public int compareTo(Object o) {
        node_data n = (node_data) o;
        if (n.getWeight() > wei)
            return -1;
        if (n.getWeight() < wei)
            return 1;
        return 0;
    }


}
