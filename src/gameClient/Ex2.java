package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.internal.bind.util.ISO8601Utils;
import gameClient.Arena;
import com.google.gson.Gson;
import gameClient.util.JPanal;
import gameClient.util.JavaFram;
import gameClient.util.Javi;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.*;

/**
 * this is our game:)
 */
public class Ex2 implements Runnable {
    private static Javi _aviWindow;
    private static JavaFram _win;
    private static JPanal jp;
    private static Arena _ar;
    private static DWGraph_Algo dwGraph;
    private static HashMap<Integer, CL_Pokemon> agentsPokemons;
    private static int cheakForRepet = 0;

    public static void main(String[] a) {
        _aviWindow = new Javi();
    }


    public void run() {
        while (true)
        {
            if (!_aviWindow.isActive())
            {
                break;
            }
        }
        int level_number = _aviWindow.getlevel();

        game_service game = Game_Server_Ex2.getServer(level_number);
        long id = _aviWindow.getId();
        game.login(id);

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

                for (int i = 0; i < _ar.getAgents().size(); i++)
                {
                    if (agentsPokemons.get(i) !=null) {
                        if (_ar.getAgents().get(i).getSrcNode() == agentsPokemons.get(i).get_edge().getSrc()) {
                            dt = 85;
                        }
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

    /**
     * this is where we init the game and we put each agent next to the src of the edge of the most value pokemon in the graph
     * @param game-the game
     * @throws JSONException
     */

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
        jp = new JPanal(game);
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

    /**
     * this is where we move the agent and we check if the agent gets to the destantion we gave him he need a new destantion with
     * the function nextNode
     * @param game-the game
     * @param graph-the graph
     */
    private static void moveAgants(game_service game, directed_weighted_graph graph) {
        String moveOut = game.move();
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
                }                game.chooseNextEdge(ag.getID(), dest);
                System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);

            }
        }
    }

    /**
     * this is where the ageant get his next destantion
     * first we updat our ist of pokemons
     * then we cheack if there is a closer agent to a pokemon there is no need to send this agent there too so
     * we removes him from the list
     * then we go throgh the list and find the closest pokemon from where we are to his src
     * then we send our agent to his src
     * and when we get to the src we send him to the dest on the same edge as the pokemon and we cathch him
     * @param game-the game
     * @param g-the graph
     * @param ag-our agent
     * @return the next dest
     */
    private static int nextNode(game_service game, directed_weighted_graph g, CL_Agent ag) {
        List<node_data> shorti;
        int src = ag.getSrcNode();

        List<CL_Pokemon> pokemons = Arena.json2Pokemons(game.getPokemons());
        for (CL_Pokemon p : pokemons) {
            Arena.updateEdge(p, g);
        }

        List<CL_Pokemon> removeP =new ArrayList<>();


        for (int i = 0; i < pokemons.size()-1; i++)
        {
            for (int k = 0; k < agentsPokemons.size()-1; k++)
            {
                if (k == ag.getID())
                {
                    k++;
                }

                if (_ar.getAgents().get(k).getNextNode() == -1)
                {
                    if (dwGraph.shortestPathDist(_ar.getAgents().get(k).getSrcNode() , pokemons.get(i).get_edge().getSrc()) <= dwGraph.shortestPathDist(ag.getSrcNode() , pokemons.get(i).get_edge().getSrc()) ){
                        removeP.add(pokemons.remove(i));
                    }
                }
                if (_ar.getAgents().get(k).getNextNode() != -1)
                {
                    if (dwGraph.shortestPathDist(_ar.getAgents().get(k).getNextNode() , pokemons.get(i).get_edge().getSrc()) <= dwGraph.shortestPathDist(ag.getSrcNode() , pokemons.get(i).get_edge().getSrc()) ){
                        removeP.add(pokemons.remove(i));
                    }
                }
            }
        }

        for (int i = 0; i < removeP.size(); i++)
        {
            pokemons.remove(removeP.get(i));
        }


        double closestD = dwGraph.shortestPathDist(src, pokemons.get(0).get_edge().getSrc());
        CL_Pokemon closestP = pokemons.get(0);
        for (int i = 0; i < pokemons.size(); i++)
        {
            double currentD = dwGraph.shortestPathDist(ag.getSrcNode(), pokemons.get(i).get_edge().getSrc());
            if (closestD > currentD)
            {
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
