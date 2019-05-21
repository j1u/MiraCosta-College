package GraphADT;

public abstract class AbstractGraph implements Graph{
    private boolean isDirected;
    private int numVertices;

    /**
     * Constructor that initializes number of vertices and if the graph is directed
     * @param numVertices
     * @param isDirected
     */
    public AbstractGraph(int numVertices, boolean isDirected){
        this.numVertices = numVertices;
        this.isDirected = isDirected;
    }

    /**
     * returns number of vertices in the graph
     * @return int containing the number of vertices
     */
    public int getNumVertices(){
        return numVertices;
    }

    /**
     * returns if the graph is directed or not
     * @return returns true if it is directed, false if not
     */
    public boolean isDirected(){
        return isDirected;
    }
}
