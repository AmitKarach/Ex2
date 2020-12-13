package api;

import com.google.gson.annotations.SerializedName;

public class NodeData implements node_data {
    /**
     * Returns the key (id) associated with this node.
     *
     * @return
     */
    @SerializedName(value = "id")
    private final int key;
    private int tag;
    private String info;
    private double wei;
    @SerializedName(value = "pos")
    private GLocation gl;

// TODO: Might Need this, Coontructor inc Location -
//    public NodeData(geo_location location,int id) {
//        this.key = id;
//        this.setLocation(location);
//        this.setWeight(0);
//        this.setInfo("");
//        this.setTag(0);
//    }

    public NodeData(int k) {
        key = k;
    }

    @Override
    public int getKey() {
        return key;
    }

    /**
     * Returns the location of this node, if
     * none return null.
     *
     * @return
     */
    @Override
    public geo_location getLocation() {
        if (gl != null)
            return gl;
        return null;
    }

    /**
     * Allows changing this node's location.
     *
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(geo_location p) {
        gl =(GLocation) p;
    }

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
     * Returns the weight associated with this node.
     *
     * @return
     */
    @Override
    public double getWeight() {
        return wei;
    }

    /**
     * Allows changing this node's weight.
     *
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        wei = w;
    }

    /**
     * Returns the remark (meta data) associated with this node.
     *
     * @return
     */
    @Override
    public String getInfo() {
        return info;
    }

    /**
     * Allows changing the remark (meta data) associated with this node.
     *
     * @param s
     */
    @Override
    public void setInfo(String s) {
        info = s;
    }

    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     *
     * @return
     */
    @Override
    public int getTag() {
        return tag;
    }

    /**
     * Allows setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        tag = t;
    }

}
