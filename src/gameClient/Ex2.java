package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import gameClient.Arena;
import com.google.gson.Gson;
import gameClient.util.JPanal;
import gameClient.util.JavaFram;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Ex2 implements Runnable {
    private static JavaFram _win;
    private static JPanal jp;
    private static Arena _ar;
    private static DWGraph_Algo dwGraph;
    private static HashMap<Integer, CL_Pokemon> agentsPokemons;

    public static void main(String[] a) {
        Thread client = new Thread(new Ex2());
        client.start();
    }


    public void run() {
        int level_number = 10;
        game_service game = Game_Server_Ex2.getServer(level_number);

        DWGraph_DS g = new DWGraph_DS();
        dwGraph = new DWGraph_Algo();
        agentsPokemons = new HashMap<>();
        String s = game.getGraph();
        dwGraph.init(g);
        dwGraph.loadGraph(s);
        try {
            initGame(game);
            game.startGame();
            _win.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());

            int ind = 0;


            while (game.isRunning()) {

                moveAgants(game, g);
                long dt = 150;
                for (int i = 0; i < _ar.getAgents().size(); i++) {
                    if (_ar.getAgents().get(i).getSrcNode() == agentsPokemons.get(i).get_edge().getSrc()) {
                        dt = 85;
                    }
                }
                try {
                    if (ind % 1 == 0) {
                        jp.repaint();
                    }
                    Thread.sleep(dt);
                    ind++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String res = game.toString();

        System.out.println(res);
        System.exit(0);
//        initGame(game);

    }


    public static void initGame(game_service game) throws JSONException {
        //starts the graph
        DWGraph_DS g = new DWGraph_DS();
        dwGraph = new DWGraph_Algo();
        String s = game.getGraph();
        dwGraph.init(g);
        dwGraph.loadGraph(s);

        //graphic stuff
        _ar = new Arena();
        _ar.setPokemons(Arena.json2Pokemons(game.getPokemons()));
        _win = new JavaFram("test Ex2");
        jp = new JPanal();
        _ar.setGraph(g);
        _win.add(jp);
        jp.update(_ar);
        //gets number of agents

        JSONObject gameString = new JSONObject(game.toString());
        JSONObject server = gameString.getJSONObject("GameServer");
        int numAgents = server.getInt("agents");
        System.out.println(numAgents);

        //get pokemons
        List<CL_Agent> agents;
        List<CL_Pokemon> pokemons = Arena.json2Pokemons(game.getPokemons());
        for (CL_Pokemon p : pokemons) {
            Arena.updateEdge(p, g);
        }

        for (int i = 0; i < numAgents; i++) {
            CL_Pokemon currrntP = pokemons.remove(i);
            game.addAgent(currrntP.get_edge().getSrc());
            agents = Arena.getAgents(game.getAgents(), g);
            _ar.setAgents(agents);
            //        _ar.getAgents().get(i).setCurrentPokemon(currrntP);
            agentsPokemons.put(i, currrntP);
            game.chooseNextEdge(i, currrntP.get_edge().getDest());
        }
    }


    private static void moveAgants(game_service game, directed_weighted_graph graph) {
        String moveOut = game.move();
//        System.out.println(moveOut);
        List<CL_Agent> agentsLocation = Arena.getAgents(moveOut, graph);
        _ar.setAgents(agentsLocation);
        List<CL_Pokemon> pokemons = Arena.json2Pokemons(game.getPokemons());
        _ar.setPokemons(pokemons);

        for (int i = 0; i < agentsLocation.size(); i++) {
            CL_Agent ag = agentsLocation.get(i);
            int id = ag.getID();
            int dest = ag.getNextNode();
            double v = ag.getValue();
            if (dest == -1) {
                dest = nextNode(game, graph, ag);
                game.chooseNextEdge(ag.getID(), dest);
                System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);

            }
        }
    }


    private static int nextNode(game_service game, directed_weighted_graph g, CL_Agent ag) {
        List<node_data> shorti;
        int src = ag.getSrcNode();

        List<CL_Pokemon> pokemons = Arena.json2Pokemons(game.getPokemons());
        for (CL_Pokemon p : pokemons) {
            Arena.updateEdge(p, g);
        }
        double closestD;
        CL_Pokemon closestP;
        if (pokemons.get(0).equals(agentsPokemons.get(ag.getID())) == false) {
            closestD = dwGraph.shortestPathDist(src, pokemons.get(0).get_edge().getSrc());
            closestP = pokemons.get(0);
        } else {
            closestD = dwGraph.shortestPathDist(src, pokemons.get(1).get_edge().getSrc());
            closestP = pokemons.get(1);
        }
        boolean t = true;
        for (int i = 1; i < pokemons.size(); i++) {
            for (int k = 0; k < agentsPokemons.size(); k++) {
                if (agentsPokemons.get(k).equals(pokemons.get(i)) == true) {
                    t = false;
                }
            }
            if (t == true) {
                double currentD = dwGraph.shortestPathDist(src, pokemons.get(i).get_edge().getSrc());
                if (closestD > currentD) {
                    closestD = currentD;
                    closestP = pokemons.get(i);
                }
            }
        }
        agentsPokemons.put(ag.getID(), closestP);

        if (src == agentsPokemons.get(ag.getID()).get_edge().getSrc()) {
            return agentsPokemons.get(ag.getID()).get_edge().getDest();
        } else {
            shorti = dwGraph.shortestPath(src, closestP.get_edge().getSrc());
            return shorti.get(1).getKey();
        }
    }
}
        /*if (src == agentsPokemons.get(ag.getID()).get_edge().getDest()) {
            List<CL_Pokemon> pokemons = Arena.json2Pokemons(game.getPokemons());
            for (CL_Pokemon p : pokemons) {
                Arena.updateEdge(p, g);
            }
            double closestD;
            CL_Pokemon closestP;
            if (pokemons.get(0).equals(agentsPokemons.get(ag.getID())) == false) {
                closestD = dwGraph.shortestPathDist(src, pokemons.get(0).get_edge().getSrc());
                closestP = pokemons.get(0);
            } else {
                closestD = dwGraph.shortestPathDist(src, pokemons.get(1).get_edge().getSrc());
                closestP = pokemons.get(1);
            }

            boolean t = true;
            for (int i = 1; i < pokemons.size(); i++)
            {
                for (int k = 0; k < agentsPokemons.size(); k++)
                {
                    if (agentsPokemons.get(k).equals( pokemons.get(i)) == true)
                    {
                        t = false;
                    }
                }
                if (t == true)
                {
                    double currentD = dwGraph.shortestPathDist(src, pokemons.get(i).get_edge().getSrc());
                    if (closestD > currentD)
                    {
                        closestD = currentD;
                        closestP = pokemons.get(i);
                    }
                }
            }
            agentsPokemons.put(ag.getID(), closestP);
            if (src != closestP.get_edge().getSrc())
            {
                shorti = dwGraph.shortestPath(src, closestP.get_edge().getSrc());
                return shorti.get(1).getKey();
            }
        }

        if (src == agentsPokemons.get(ag.getID()).get_edge().getSrc())
        {
            return agentsPokemons.get(ag.getID()).get_edge().getDest();
        }

        shorti = dwGraph.shortestPath(src, agentsPokemons.get(ag.getID()).get_edge().getSrc());
        return shorti.get(1).getKey();
    }*/


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

//            closestD = dwGraph.shortestPathDist(src, pokemons.get(j).get_edge().getSrc());
//            while (closestD ==-1)
//            {
//                pokemons.remove(j);
//                j++;
//                closestD = dwGraph.shortestPathDist(src, pokemons.get(j).get_edge().getSrc());
//            }
//            closestP = pokemons.get(j);