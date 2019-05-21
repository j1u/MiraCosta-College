package GraphADT;

import java.util.Objects;

public class Edge {
    private double weight;
    private int source;
    private int destination;
    private static final double DEFAULT_WEIGHT = 1.0;

    public Edge(int source, int destination){
        this.source = source;
        this.destination = destination;
        this.weight = DEFAULT_WEIGHT;
    }

    public Edge(int source, int destination, double weight){
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public int getSource(){
        return source;
    }

    public int getDestination(){
        return destination;
    }

    public double getWeight(){
        return weight;
    }

    @Override
    public boolean equals(Object o){
        if (o == this){
            return true;
        }

        if(o.getClass() != this.getClass()){
            return false;
        }

        Edge edge = (Edge) o;
        if (edge.source != this.source || edge.destination != this.destination){
            return false;
        }

        return true;
    }


    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getDestination());
    }

    public String toString(){
        return "Source: " + this.source + ", Destination: " + this.destination + ", Weight: " + this.weight;
    }
}
