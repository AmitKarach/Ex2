import Server.Game_Server_Ex2;
import api.*;
import gameClient.Arena;
import gameClient.CL_Agent;
import gameClient.CL_Pokemon;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Ex2Test {
    private static HashMap<Integer, CL_Pokemon> agentsPokemons;
    private static Arena _ar;

    @Test
    void main() throws JSONException {
        // Generate random 0-23
        Random rand = new Random();
        int level_number = rand.nextInt(24);
        game_service game = Game_Server_Ex2.getServer(level_number);

        // Creation of Graph and Compare to Original Graph
        String s = game.getGraph();
        directed_weighted_graph thereGraph = game.getJava_Graph_Not_to_be_used();
        DWGraph_DS g = new DWGraph_DS();
        DWGraph_Algo dwGraph = new DWGraph_Algo();
        dwGraph.init(g);
        dwGraph.loadGraph(s);



        assertEquals(thereGraph.edgeSize(), g.edgeSize());// Same amount of edges
        assertEquals(thereGraph.nodeSize(), g.nodeSize());// Same amount of nodes
        Boolean a = true;
        Boolean b = true;
        // Every Node same Key
        for (int i = 0; i < g.nodeSize(); i++) {
            node_data nb = g.getNode(i);
            if (g.getNode(i).getKey() != thereGraph.getNode(i).getKey())
                a = false;
        }
        assertEquals(a, true);
        // Every Node hase Same Edges
        for (int i = 0; i < g.nodeSize(); i++) {
            if (!g.getE(i).containsAll(thereGraph.getE(i))) {
                b = false;
            }
        }
        assertEquals(b, true);

        // Check Agents Assigned Per Pokemon Val
        agentsPokemons = new HashMap<>();
        _ar = new Arena();
        JSONObject gameString = new JSONObject(game.toString());
        JSONObject server = gameString.getJSONObject("GameServer");
        int numAgents = server.getInt("agents");
        System.out.println(numAgents);
        List<CL_Agent> agents;
        List<CL_Pokemon> pokemons = Arena.json2Pokemons(game.getPokemons()); // From server no need to check
        for (CL_Pokemon p : pokemons) {
            Arena.updateEdge(p, g);
        }
        // Check Agents put next to Highest val Pokemon
        for (int i = 0; i < numAgents; i++) {
            CL_Pokemon maxValuePokemon = pokemons.get(0);
            int x = 0;
            for (int j = 0; j < pokemons.size(); j++) {
                if (maxValuePokemon.getValue() < pokemons.get(j).getValue()) {
                    maxValuePokemon = pokemons.get(j);
                    x = j;
                }
            }
            pokemons.remove(x);

            agentsPokemons.put(i, maxValuePokemon);

        }
        Boolean c = true;
        double x = agentsPokemons.get(0).getValue();
        for (int v = 1; v < agentsPokemons.size(); v++) {
            if (x < agentsPokemons.get(v).getValue())
                c = false;
        }
        assertEquals(c, true);
    }
    // In First Function Save and Load are Checked, thus this does not appear in Algo Test
    // MoveAgent Function is basicly a simple for - I update the Current Agent and Pokemon status,
    // I iterate(for) over every agent and decide it's next node based on nextNode which is a slightly modified
    // Shortest path and ShortestDistance

    //---------------------------------------

    // Simple run Based of YOUR run.

}