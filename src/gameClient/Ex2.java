package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import gameClient.Arena;
import com.google.gson.Gson;
import gameClient.util.JPanal;
import gameClient.util.JavaFram;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Ex2 implements Runnable {
    private static JavaFram _win;
    private static JPanal jp;
    private static Arena _ar;
    private static DWGraph_Algo dwGraph;
    private static HashMap<Integer, CL_Pokemon> agentsPokemons;
    private static int cheakForRepet = 0;

    public static void main(String[] a) {
        Thread client = new Thread(new Ex2());
        client.start();
    }


    public void run() {
        int level_number = 20;
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

//            for (int i = 0; i <_ar.getAgents().size() ; i++)
//            {
//                   Thread agents = new AgentT(i, game, _ar, jp);
//                   agents.start();
//            }

            while (game.isRunning()) {
                int ind = 0;
                moveAgants(game, g);
                long dt = 150;

                for (int i = 0; i < _ar.getAgents().size(); i++) {
                    if (_ar.getAgents().get(i).getSrcNode() == agentsPokemons.get(i).get_edge().getSrc()) {
                        dt = 85;
                    }
                }
//                        if (_ar.getAgents().get(i).get_curr_edge() != null)
//                        {
//                            System.out.println((long)((_ar.getAgents().get(i).get_curr_edge().getWeight()*1000)/_ar.getAgents().get(i).getSpeed()));
//                        }
//                    if (_ar.getAgents().get(i).get_curr_edge().getWeight()*1000)
//                    {
//
//                    }

                if (cheakForRepet == 2) {
                    dt = 50;
                    cheakForRepet = 0;
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

        for (int i = 0; i < numAgents; i++)
        {
            CL_Pokemon maxValuePokemon = pokemons.get(0);
            int x=0;
            for (int j = 1; j < pokemons.size(); j++)
            {
                if (maxValuePokemon.getValue()< pokemons.get(j).getValue())
                {
                    maxValuePokemon = pokemons.get(j);
                    x =j;
                }
            }
            pokemons.remove(x);
            game.addAgent(maxValuePokemon.get_edge().getSrc());
            agents = Arena.getAgents(game.getAgents(), g);
            _ar.setAgents(agents);
            //        _ar.getAgents().get(i).setCurrentPokemon(currrntP);
            agentsPokemons.put(i, maxValuePokemon);
            game.chooseNextEdge(i, maxValuePokemon.get_edge().getDest());
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
            int src =ag.getSrcNode();
            int dest = ag.getNextNode();
            double v = ag.getValue();
            if (dest == -1) {
                dest = nextNode(game, graph, ag);
                if (dest == src)
                {
                    cheakForRepet++;
                }
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
//        double closestD = dwGraph.shortestPathDist(src, pokemons.get(0).get_edge().getSrc());
//        CL_Pokemon closestP = pokemons.get(0);
        double closestD = dwGraph.shortestPathDist(src, pokemons.get(0).get_edge().getSrc());
        CL_Pokemon closestP = pokemons.get(0);
//        if (pokemons.get(0).equals(agentsPokemons.get(ag.getID())) == false) {
//            closestD = dwGraph.shortestPathDist(src, pokemons.get(0).get_edge().getSrc());
//            closestP = pokemons.get(0);
//        } else {
//            closestD = dwGraph.shortestPathDist(src, pokemons.get(1).get_edge().getSrc());
//            closestP = pokemons.get(1);
//        }
        List<CL_Pokemon> removeP =new ArrayList<>();
        for (int i = 0; i < pokemons.size(); i++)
        {
            for (int k = 0; k < agentsPokemons.size()-1; k++)
            {
                if (k == ag.getID())
                {
                    k++;
                }
                if (agentsPokemons.get(k).equals(pokemons.get(i)) == true)
                {
                    if (dwGraph.shortestPathDist(_ar.getAgents().get(ag.getID()).getSrcNode() , pokemons.get(i).get_edge().getSrc()) < dwGraph.shortestPathDist(_ar.getAgents().get(k).getSrcNode() , pokemons.get(i).get_edge().getSrc()))
                    {
                        removeP.add(pokemons.get(i));
                    }

                }
            }
        }
        for (int i = 0; i < removeP.size(); i++)
        {
            pokemons.remove(removeP.get(i));
            agentsPokemons.remove(removeP.get(i));
        }

        for (int i = 0; i < pokemons.size(); i++)
        {
            double currentD = dwGraph.shortestPathDist(src, pokemons.get(i).get_edge().getSrc());
            if (closestD > currentD) {
                closestD = currentD;
                closestP = pokemons.get(i);
            }
        }
        agentsPokemons.put(ag.getID(), closestP);
        if (src == agentsPokemons.get(ag.getID()).get_edge().getSrc())
        {
            return agentsPokemons.get(ag.getID()).get_edge().getDest();
        }
        else
        {
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