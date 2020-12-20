package api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BoazGraph
{
    @SerializedName(value = "Edges")
    List<EdgeData> edges;
    @SerializedName(value = "Nodes")
    List<BoazNode> nodes;


    static class BoazNode {
        int id;
        String pos;
    }

}
