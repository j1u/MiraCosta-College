package GraphADT;

import java.util.*;

public class ListGraph extends AbstractGraph{
    private ArrayList<List<Edge>> edges;

    /**
     * Creates a graph object using the list implementation
     * @param numVertices initial number of vertices for the graph
     * @param isDirected determines of graph is directed or not
     */
    public ListGraph(int numVertices, boolean isDirected){
        super(numVertices, isDirected);
        edges = new ArrayList<List<Edge>>(numVertices);

        for (int i = 0; i < numVertices; i++){
            edges.add(i, new LinkedList<Edge>());
        }
    }

    /**
     * Creates an iterator for the nested linked list at the source
     * @param source The source vertex
     * @return a list iterator at the source
     */
    public ListIterator<Edge> edgeIterator(int source){
        return edges.get(source).listIterator();
    }

    /**
     * returns if a vertex contains an edge
     * @param source The source vertex
     * @param destination The destination vertex
     * @return true if the edge is found in the list, false if not
     */
    public boolean isEdge(int source, int destination){
        return edges.get(source).contains(new Edge(source, destination));
    }

    /**
     * Gets the edge between two vertices
     * @param source The source vertex
     * @param destination The destination vertex
     * @return the GraphADT.Edge object between the source and destination
     */
    public Edge getEdge(int source, int destination){
        Edge target = new Edge(source, destination, Double.POSITIVE_INFINITY);

        for (List<Edge> e : edges){
            for (Edge edge : e){
                if (edge.equals(target)){
                    return edge;
                }
            }
        }

        return null;

    }

    /**
     * Inserts an edge between two vertices
     * @param edge The new edge
     */
    public void insert(Edge edge){
        edges.get(edge.getSource()).add(edge);
        if (!this.isDirected()){
            edges.get(edge.getDestination()).add(new Edge(edge.getDestination(),edge.getSource(), edge.getWeight()));
        }
    }
}
