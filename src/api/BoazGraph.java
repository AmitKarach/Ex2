package api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * this class is here to help us change the node from his json form to our loved node
 */
public class BoazGraph
{
    @SerializedName(value = "Edges")
    List<EdgeData> edges;
    @SerializedName(value = "Nodes")
    List<BoazNode> nodes;


    /**
     * the node that we use here
     */
    static class BoazNode {
        int id;
        String pos;
    }

}
