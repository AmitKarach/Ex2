package gameClient;
import Server.Game_Server_Ex2;
import api.game_service;
import api.edge_data;
import api.node_data;
import api.NodeData;
import api.DWGraph_DS;
import gameClient.Arena;
import api.DWGraph_Algo;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Ex2 {

    public static void main(String[] args) throws JSONException {
        int level_number = 5;
        game_service game = Game_Server_Ex2.getServer(level_number);
//        game.addAgent(6);
//        String s = game.getGraph();
////        System.out.println(game.toString());
//        Gson gson = new Gson();
//        GameServer gameServer = gson.fromJson(game.toString(),GameServer.class );
//        DWGraph_DS g = new DWGraph_DS();
//////        System.out.println(g.nodeSize());
////
//        DWGraph_Algo graph =new DWGraph_Algo();
//        graph.init(g);
//        graph.loadGraph(s);
//        List<CL_Pokemon> pokemons = Arena.json2Pokemons(game.getPokemons());
//        game.getGraph();
//        System.out.println(gameServer.agents);
//        System.out.println(game.getPokemons());

        initGame(game);

    }


    public static void initGame(game_service game) throws JSONException {
        //starts the graph
        DWGraph_DS g = new DWGraph_DS();
        DWGraph_Algo graph =new DWGraph_Algo();
        String s = game.getGraph();
        graph.init(g);
        graph.loadGraph(s);

        //gets number of agents
        JSONObject gameString = new JSONObject(game.toString());
        JSONObject server = gameString.getJSONObject("GameServer");
        int numAgents = server.getInt("agents");
        System.out.println(numAgents);

        //get pokemons
        List<CL_Pokemon> pokemons = Arena.json2Pokemons(game.getPokemons());
        for (CL_Pokemon p: pokemons)
        {
            Arena.updateEdge(p, g);
        }

//        int x=0;
        for (int i=0; i< numAgents; i++)
        {
            CL_Pokemon currrntP =pokemons.remove(i);
            if (currrntP.getType() ==-1)
            {
                game.addAgent(currrntP.get_edge().getDest());
            }
            if (currrntP.getType() ==1)
            {
                game.addAgent(currrntP.get_edge().getSrc());
            }

        }

    }
}
