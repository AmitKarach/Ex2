package gameClient;
import Server.Game_Server_Ex2;
import api.game_service;
import api.edge_data;
import api.node_data;
import api.NodeData;
import api.DWGraph_DS;
import api.DWGraph_Algo;
import com.google.gson.Gson;

public class Ex2 {

    public static void main(String[] args) {
        int level_number = 4;
        game_service game = Game_Server_Ex2.getServer(level_number);
        game.addAgent(6);
        String s = game.getGraph();
        System.out.println(game.getAgents());
//        Gson gson = new Gson();
//       DWGraph_DS g = new DWGraph_DS();
////        System.out.println(g.nodeSize());
//
//        DWGraph_Algo graph =new DWGraph_Algo();
//        graph.init(g);
//        graph.load(s);

       // game.getGraph();

    }
}
