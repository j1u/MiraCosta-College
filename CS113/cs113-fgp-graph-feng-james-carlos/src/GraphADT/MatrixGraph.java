package GraphADT;

import java.util.Iterator;

public class MatrixGraph extends AbstractGraph{
    private double[][] edges;

    /**
     * Constructor that initializes number of vertices and if the graph is directed
     *
     * @param numVertices
     * @param isDirected
     */
    public MatrixGraph(int numVertices, boolean isDirected) {
        super(numVertices, isDirected);
        edges = new double[numVertices][numVertices];

        for (int i = 0; i < numVertices; i++){
            for (int j = 0; j < numVertices; j++){
                edges[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    @Override
    public void insert(Edge edge) {
        int row, column;
        row = edge.getSource();
        column = edge.getDestination();

        edges[row][column] = edge.getWeight();

        if (!isDirected()){
            edges[column][row] = edge.getWeight();
        }
    }

    @Override
    public boolean isEdge(int source, int destination) {
        return edges[source][destination] != Double.POSITIVE_INFINITY;
    }

    @Override
    public Edge getEdge(int source, int destination) {
        if (edges[source][destination] != Double.POSITIVE_INFINITY){
            return new Edge(source, destination, edges[source][destination]);
        }

        return null;
    }

    @Override
    public Iterator<Edge> edgeIterator(int source) {
        return new EdgeIter(source);
    }

    protected class EdgeIter implements Iterator{

        private double rowPos;
        private double columnPos;
        private double current;

        private double[][] arr;

        public EdgeIter(int source){
            this.arr = edges;
            this.current = arr[source][0];
            this.rowPos = source;
            this.columnPos = 0;
        }

        @Override
        public boolean hasNext() {
            return rowPos <= getNumVertices() || columnPos <= getNumVertices() ;
        }

        @Override
        public Object next() {
            return null;
        }

        @Override
        public void remove() {

        }
    }

}
