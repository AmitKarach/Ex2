hello and welcome to our pokemon game

to activate it simlply open the jar in the file and enter you name and level 
or open the jar with command line and again enter id and level number
lets see what score you will get:)

this is what we have here


I. File list       File Type
----------------------------
[API]:
node_data----------Java File/Interface	
NodeData-----------Java File/Class 
edge_data----------Java File/Interface	
EdgeData-----------Java File/Class
geo_location----------Java File/Interface	
GLocation-----------Java File/Class 
directed_weighted_graph--------------Java File/Interface
DWGraph_DS-----------Java File/Class
dw_graph_algorithims--Java File/Interface
DWGraph_Algo---------Java File/Class
BoazGraph---------JavaFile/Class Help Class for Json Convertion to Graph 
----------------------------
A bit about Graphs and basic terminoligy(Our Project represents a directed weighted graph).
An undirected graph is graph, i.e., a set of objects (called vertices or nodes) that are connected together,
where all the edges are bidirectional. An undirected graph is sometimes called an undirected network.
In contrast, a graph where the edges point in a direction is called a directed graph.

Weighted Graph - A weighted graph or a network is a graph in which a number (the weight) is assigned to each edge. Such weights might represent for example costs, lengths or capacities, depending on the problem at hand. 
Such graphs arise in many contexts, for example in shortest path problems such as the traveling salesman problem. 

One can formally define an undirected graph as G = (V, E), where V is a set whose elements are called vertices (singular: vertex), 
and E is a set of paired vertices, whose elements are called edges (sometimes links or lines).

----------------------------

Terminology:
A graph consists of:

A set, V, of vertices (nodes)

A collection, E, of pairs of vertices from V called edges (arcs)

Edges, also called arcs, are represented by (u, v) and are either:

Directed if the pairs are ordered (u, v)

u the origin
v the destination

Undirected if the pairs are unordered

Then a graph can be:

Directed graph (di-graph) if all the edges are directed
Undirected graph (graph) if all the edges are undirected
Mixed graph if edges are both directed or undirected
Illustrate terms on graphs

End-vertices of an edge are the endpoints of the edge.

Two vertices are adjacent if they are endpoints of the same edge.

An edge is incident on a vertex if the vertex is an endpoint of the edge.

Outgoing edges of a vertex are directed edges that the vertex is the origin.
Incoming edges of a vertex are directed edges that the vertex is the destination.

Degree of a vertex, v, denoted deg(v) is the number of incident edges.
Out-degree, outdeg(v), is the number of outgoing edges.
In-degree, indeg(v), is the number of incoming edges.

Parallel edges or multiple edges are edges of the same type and end-vertices
Self-loop is an edge with the end vertices the same vertex
Simple graphs have no parallel edges or self-loops

Path is a sequence of alternating vetches and edges such that each successive vertex is connected by the edge.  Frequently only the vertices are listed especially if there are no parallel edges.
Cycle is a path that starts and end at the same vertex.
Simple path is a path with distinct vertices.
Directed path is a path of only directed edges
Directed cycle is a cycle of only directed edges.

Sub-graph is a subset of vertices and edges.
Spanning sub-graph contains all the vertices.

Connected graph has all pairs of vertices connected by at least one path.
Connected component is the maximal connected sub-graph of a unconnected graph.
Forest is a graph without cycles.
Tree is a connected forest (previous type of trees are called rooted trees, these are free trees)
Spanning tree is a spanning subgraph that is also a tree.

------------------------------

Each Class and it's functions and it's parameters:

NodeData: Class Representing The Node and it's data + Functions to apply on.
------------------------------
Parameters:
---
private final int key;
private int tag;
private String info;
private double wei;
private GLocation gl;
-------------
Functions:
public NodeInfo - Constructors
public int getKey(); - Return the key (id) associated with this node.
public geo_location getLocation() - Return coordinates of Node
public void setLocation(geo_location p) - Sets coordinates of Node
public String toString() - Prints Node
public double getWeight() - return weight of node
public void setWeight(double w) - Set Weight
public String getInfo(); - Returns the meta data associated with this node.
public void setInfo(String s); - Change the meta data.
public double getTag(); - Recieve temporal data.(WEIGHT)
public void setTag(double t); - Set temporal data.
public int compareTo(Object o); - A function to compare two node tags
public boolean equals(Object obj) - This function checks if two have equal weights
------------------------------
DWGraph_DS Class Representing The Graph and it's data + Functions to apply on.
------------------------------
Parameters:
    private final HashMap<Integer, node_data> Vertices;
    private final HashMap<Integer, HashMap<Integer, edge_data>> Edges;
    private int edgesCounter;
    private int mc;
-------------
Functions:
public WGraph_DS(); - Weighted Graph Constructor
public edge_data getEdge(int src, int dest) - Returns Edge if Exists
public node_info getNode(int key); - This function based on a given node ID checks if the node exists on the graph. If so return T, Else Null.
public void addNode(node_data n) - Add's a node to the Graph
public void connect(int src, int dest, double w) - Connects\Create's a edge between two nodes
public Collection<node_data> getV() -  returns collection representing all the nodes in the graph
public Collection<edge_data> getE(int node_id) - returns a collection of all edges comming out of specific node
public node_data removeNode(int key) - removes node from graph
public edge_data removeEdge(int src, int dest) - Deletes Edge from Graph
public int nodeSize() - Returns number of Vertices in Graph
public int edgeSize() - Returns Number of Edges
public int getMC() - Returns ModCount



-------------

------------------------------
DWGraph_Algo Class Representing The Algorithims that are appliciable on my Graph.
------------------------------
    directed_weighted_graph graph;
    ArrayList<Double> wieghts = new ArrayList<>();
    ArrayList<node_data> parents = new ArrayList<>();
-------------
Functions:
public DWGraph_Algo() - Constructor
public void init(directed_weighted_graph g) - Simple init
public directed_weighted_graph getGraph() - Get for Graph
public directed_weighted_graph copy() - Returns HardCopy of Graph
public boolean isConnected() - Returns T/F if graph is connected
public double shortestPathDist(int src, int dest) - Returns Least number of nodes to pass in order to get from src to dest
public List<node_data> shortestPath(int src, int dest) - Returns a list of nodes that represent shortest path from src to dest 
public boolean save(String file) - Saves this weighted (directed) graph to the given file name - in JSON format
public boolean load(String file); -      * This method load a graph to this graph algorithm. if the file was successfully loaded - the underlying graph of this class will be changed (to the loaded one), in case the graph was not loaded the original graph should remain "as is"



Part 2 - Ex2 - 
About the game - The game consists of 24 level(0-23) The main goal of the game is to eat as many pokemons given a specific number of Agents within the time limit.

I control the Agents Via a given class named game_service that is implemented on the server.

public interface game_service extends Serializable{	

public String getGraph(); -  Returns a JSON representation of graph as a JSON String.

default directed_weighted_graph getJava_Graph_Not_to_be_used() - Returns an interface to the graph (should NOT be used) for final version - for testing only.

public String getPokemons(); - Returns a JSON string, representing all Pokemons

public String getAgents(); - Returns a JSON string, representing all the Agents

public boolean addAgent(int start_node); - This method allows the user to add & locate the agents,

public long startGame(); - Start a new game

public boolean isRunning(); - Returns the current status of the game

public long stopGame(); - Stops the game

public long chooseNextEdge(int id, int next_node); - allows the client algorithm to direct each agent to the "next" edge

public long timeToEnd(); - return the number of mili-seconds till the game is over

public String move(); - moves all the agents along each edge

public boolean login(long id); -Performs a login - so the results of the game will be stored in the data-base after the game 









CREATED BY NADAV SUISA 
AND AMIT KARACH




