package gameClient;

import com.google.gson.annotations.SerializedName;

public class GameServer
{
    @SerializedName(value = "pokemons")
    int pokemons;
    @SerializedName(value = "is_logged_in")
    boolean is_logged_in;
    @SerializedName(value = "moves")
    int moves;
    @SerializedName(value = "grade")
    int grade;
    @SerializedName(value = "game_level")
    int game_level;
    @SerializedName(value = "max_user_level")
    int max_user_level;
    @SerializedName(value = "id")
    int id;
    @SerializedName(value = "graph")
    String graph;
    @SerializedName(value = "agents")
    int agents;

}
