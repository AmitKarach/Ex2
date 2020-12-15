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
import java.util.Iterator;
import java.util.List;

public class Ex2 implements Runnable {
    private static JavaFram _win;
    private static JPanal jp;
    private static Arena _ar;

    public static void main(String[] a) {
        Thread client = new Thread(new Ex2());
        client.start();
    }


    public void run() {
        int level_number = 23;
        game_service game = Game_Server_Ex2.getServer(level_number);

        DWGraph_DS g = new DWGraph_DS();
        DWGraph_Algo graph = new DWGraph_Algo();
        String s = game.getGraph();
        graph.init(g);
        graph.loadGraph(s);
        try {
            initGame(game);
            game.startGame();
            _win.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());

            int ind = 0;
            long dt = 100;



            while (game.isRunning()) {
                moveAgants(game, g);
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

//        initGame(game);

    }


    public static void initGame(game_service game) throws JSONException
    {
        //starts the graph
        DWGraph_DS g = new DWGraph_DS();
        DWGraph_Algo graph = new DWGraph_Algo();
        String s = game.getGraph();
        graph.init(g);
        graph.loadGraph(s);

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
            CL_Pokemon currrntP = pokemons.remove(i);
            game.addAgent(currrntP.get_edge().getSrc());
            agents= Arena.getAgents(game.getAgents(), g);
            _ar.setAgents(agents);
            _ar.getAgents().get(i).setCurrentPokemon(currrntP);
            game.chooseNextEdge(i,currrntP.get_edge().getDest());
        }
    }




    private static void moveAgants(game_service game, directed_weighted_graph graph) {
        String moveOut = game.move();
        List<CL_Agent> agentsLocation = Arena.getAgents(moveOut, graph);
        _ar.setAgents(agentsLocation);
        List<CL_Pokemon> pokemons = Arena.json2Pokemons(game.getPokemons());
        _ar.setPokemons(pokemons);

        for (int i = 0; i < agentsLocation.size(); i++) {
            CL_Agent ag = agentsLocation.get(i);
            int id = ag.getID();
            int dest = ag.getNextNode();
            int src = ag.getSrcNode();
            double v = ag.getValue();
            if (dest == -1) {
                dest = nextNode(graph, src);
                game.chooseNextEdge(ag.getID(), dest);
                System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
            }
        }
    }

    /**
     * a very simple random walk implementation!
     *
     * @param g
     * @param src
     * @return
     */
    private static int nextNode(directed_weighted_graph g, int src) {
        int ans = -1;
        Collection<edge_data> ee = g.getE(src);
        Iterator<edge_data> itr = ee.iterator();
        int s = ee.size();
        int r = (int) (Math.random() * s);
        int i = 0;
        while (i < r) {
            itr.next();
            i++;
        }
        ans = itr.next().getDest();
        return ans;
    }


}

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
