package gameClient;

import api.*;
import gameClient.util.JPanal;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AgentT extends Thread {
    private int id;
    private game_service game;
    private Arena _ar;
    private JPanal jp;
    private static DWGraph_Algo algo;
    private directed_weighted_graph g;
    private static CL_Pokemon pokemonsTaken[];

//    private static DWGraph_Algo dwGraph;
//    private static HashMap<Integer, CL_Pokemon> agentsPokemons;

    public AgentT(int id, game_service game, Arena _ar, JPanal jp) {
        this.id = id;
        this.game = game;
        this._ar = _ar;
        this.jp = jp;
        algo = new DWGraph_Algo();
        algo.loadGraph(game.getGraph());
        g = algo.getGraph();
        JSONObject gameString = null;
        try {
            gameString = new JSONObject(game.toString());
            JSONObject server = gameString.getJSONObject("GameServer");
            int numAgents = server.getInt("agents");
            pokemonsTaken = new CL_Pokemon[numAgents];
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        super.run();
        agent();
    }

    public void agent() {


        while (game.isRunning()) {
            moveAgants(game, g);
            int ind = 0;
            long destnitionTime = 150;

            if (_ar.getAgents().get(id).getSrcNode() ==getPokemonTakenList(id).get_edge().getSrc()) {
                destnitionTime = 85;
            }
//                if (_ar.getAgents().get(i).get_curr_edge() != null) {
//                    System.out.println((long) ((_ar.getAgents().get(i).get_curr_edge().getWeight() * 1000) / _ar.getAgents().get(i).getSpeed()));
//                }
//                    if (_ar.getAgents().get(i).get_curr_edge().getWeight()*1000)
//                    {
//
//                    }
            try {
                if (ind % 1 == 0) {
                    jp.repaint();
                }
                Thread.sleep(destnitionTime);
                ind++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void moveAgants(game_service game, directed_weighted_graph graph) {
        String moveOut = game.move();
        List<CL_Agent> agentsLocation = Arena.getAgents(moveOut, graph);
        _ar.setAgents(agentsLocation);
        List<CL_Pokemon> pokemons = Arena.json2Pokemons(game.getPokemons());
        _ar.setPokemons(pokemons);


        CL_Agent myAgent = agentsLocation.get(id);
        int dest = myAgent.getNextNode();
        double v = myAgent.getValue();
        if (dest == -1) {
            dest = nextNode(game, graph, myAgent);
//                if (dest == myAgent.get_curr_edge().getSrc() )
//                {
//                    cheakForRepet++;
//                }
            game.chooseNextEdge(myAgent.getID(), dest);
            System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
        }
    }


    private static int nextNode(game_service game, directed_weighted_graph g, CL_Agent ag) {
        List<node_data> shorti;

        int src = ag.getSrcNode();

        List<CL_Pokemon> pokemons = Arena.json2Pokemons(game.getPokemons());
        for (CL_Pokemon p : pokemons) {
            Arena.updateEdge(p, g);
        }
        double closestD = algo.shortestPathDist(src, pokemons.get(0).get_edge().getSrc());
        ;
        CL_Pokemon closestP = pokemons.get(0);
        boolean t = true;
        for (int i = 1; i < pokemons.size(); i++)
        {
            for (int k = 0; k < pokemonsTaken.length; k++)
            {if (getPokemonTakenList(k) !=null) {
                if (getPokemonTakenList(k).equals(pokemons.get(i)) == true) {
                    t = false;
                }
            }
            }
            if (t == true) {
                double currentD = algo.shortestPathDist(src, pokemons.get(i).get_edge().getSrc());
                if (closestD > currentD) {
                    closestD = currentD;
                    closestP = pokemons.get(i);
                }
            }
        }
        setPokemonTakenList(ag.getID(), closestP);

        if (src == getPokemonTakenList(ag.getID()).get_edge().getSrc()) {
            return getPokemonTakenList(ag.getID()).get_edge().getDest();
        } else {
            shorti = algo.shortestPath(src, closestP.get_edge().getSrc());
            return shorti.get(1).getKey();
        }
    }

    private static synchronized void setPokemonTakenList(int id, CL_Pokemon pikatcu)
    {
        pokemonsTaken[id] = pikatcu;
    }

    private static synchronized CL_Pokemon getPokemonTakenList(int id) {
        return pokemonsTaken[id];
    }
}
