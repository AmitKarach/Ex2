package gameClient;

import api.DWGraph_DS;
import api.NodeData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;

public class Json
{
    public DWGraph_DS graphFromJson (String file)
    {

        DWGraph_DS graph = new DWGraph_DS();
        Gson g = new Gson();
        graph= g.fromJson(file, DWGraph_DS.class);
        ArrayList<NodeData>= g.getAsJsonArray("Nodes");
    }
}
