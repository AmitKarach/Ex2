package api;

import com.google.gson.annotations.SerializedName;

/**
 * this is the reprisantion of the edges on the graph
 */
public class EdgeData implements edge_data {
    /**
     * each edge has a few fields:
     * src - the source of the edge
     * dest- the destaniton of the edge
     * weight - the weight of the edge
     *
     */
    @SerializedName(value = "src")
    private int src;
    @SerializedName(value = "dest")
    private int dest;
    private int tag;
    @SerializedName(value = "w")
    private double weight;
    private String info;

    public EdgeData() {

    }

    public EdgeData(int s, int d, double w) {
        src = s;
        dest = d;
        weight = w;
    }

    /**
     * gets the id of the src node
     *
     * @return src
     */
    @Override
    public int getSrc() {
        return src;
    }

    /**
     * gets the id of the dest node
     *
     * @return dest
     */
    @Override
    public int getDest() {
        return dest;
    }

    /**
     * @return the weight of the edge
     */
    @Override
    public double getWeight() {
        return weight;
    }

    /**
     * Returns the info of the edge
     *
     * @return weight
     */
    @Override
    public String getInfo() {
        return info;
    }

    /**
     * sets the info of the edge
     * @param s- the given info
     */
    @Override
    public void setInfo(String s) {
        info = s;
    }

    /**
     *gets the tag of the edge
     * @return the tag
     */
    @Override
    public int getTag() {
        return tag;
    }

    /**
     * sets the tag of the edge
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        tag = t;
    }

    /**
     * checks f two edges are equals
     * @param obj -the other edge
     * @return true of they are equals
     */
    @Override
    public boolean equals(Object obj) {
        EdgeData n = (EdgeData) obj;
        return n.getDest() == dest && n.getSrc() == src;
    }
}
