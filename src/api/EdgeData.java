package api;

import com.google.gson.annotations.SerializedName;

/**
 * this will reprisent to us our edges on the graph
 */
public class EdgeData implements edge_data {
    /**
     * each edge has a few fileds
     * a src - the source of the edge
     * a dest= the destenaition
     * a weight -the weight of the edge
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
     * @return the src
     */
    @Override
    public int getSrc() {
        return src;
    }

    /**
     * @return the dest
     */
    @Override
    public int getDest() {
        return dest;
    }

    /**
     * @return the weight of this edge (positive value).
     */
    @Override
    public double getWeight() {
        return weight;
    }

    /**
     * @return the edge info
     */
    @Override
    public String getInfo() {
        return info;
    }

    /**
     *edit the info
     * @param s-the new info
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
     * edit the tag
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        tag = t;
    }

    /**
     * checks if two edges ar equals
     * @param obj-the other edge
     * @returntrue if they are equals
     */
    @Override
    public boolean equals(Object obj) {
        EdgeData n = (EdgeData) obj;
        return n.getDest() == dest && n.getSrc() == src;
    }
}
