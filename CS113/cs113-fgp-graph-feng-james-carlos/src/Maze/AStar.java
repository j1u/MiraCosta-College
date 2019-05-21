package Maze;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * AStar class that controls the AStar search algorithm
 *
 * @author James Lu
 * @version 1.0
 */
public class AStar {

    protected MazeModel maze; //reference to the maze class and it's variables

    protected PriorityQueue<MazeCell> open; //open set is the discovered cells that haven't been evaluated
    protected PriorityQueue<MazeCell> closed; //closed set is the cells that have already been evaluated

    protected MazeCell current;
    protected boolean completed;
    private Integer[] adjacencies; //adjacencies to the current cell
    private MazeCell neighbor; //the selected neighbor from the adjacent cells

    public AStar(MazeModel maze){
        this.maze = maze;

        this.open = new PriorityQueue<>(100, new MazeCellComparator()); //init open
        this.closed = new PriorityQueue<>(100, new MazeCellComparator()); //init closed

        this.open.add(maze.start); //init open queue with the starting cell

        this.current = maze.start; //set current cell to maze start

        this.current.setGScore(0); //the current distance from start to start is 0
        this.current.setFScore(heuristicFct(current)); //the estimated distance from start to finish

        this.completed = false;
    }

    /**
     * Runs the AStar search algorithm, uses dijkstra like path finding oriented by weights. The equation that is used
     * to find the optimal path is: f(n) = g(n) + heuristicFct. (where g(n) is the distance from the start node to that node
     * and the heuristic function is the predicted distance from the cell to the end.)
     */
    public void run() {
        if (!open.isEmpty()){ //if the open set is not empty

            current = open.poll(); //the current cell is the first item in the queue

            adjacencies = getNeighbors(); //gets valid neighbors

            open.remove(current);
            closed.add(current);

            for (int i = 0; i < adjacencies.length; i++){ //each adjacent cell of current
                if (adjacencies[i] == 0) { //if first adjacency is 0 (North)
                    neighbor = maze.grid[current.colIndex][current.rowIndex - 1];

                } else if (adjacencies[i] == 1) { //if first adjacency is 1 (East)
                    neighbor = maze.grid[current.colIndex + 1][current.rowIndex];

                } else if (adjacencies[i] == 2) { //if first adjacency is 2 (South)
                    neighbor = maze.grid[current.colIndex][current.rowIndex + 1];

                } else if (adjacencies[i] == 3) { //if first adjacency is 3 (West)
                    neighbor = maze.grid[current.colIndex - 1][current.rowIndex];
                }
                if (closed.contains(neighbor)){ //neighbor is in closed set
                    continue; //skip
                }

                double tentativeGScore = current.gScore + 1; //distance from start to neighbor

                if (!open.contains(neighbor)){ //neighbor is not in open
                    open.add(neighbor); //add neighbor to open
                }else if(tentativeGScore >= neighbor.gScore){ //distance from start to neighbor >= total gscore
                    continue; //skip
                }

                neighbor.setParent(current); //came from = current
                neighbor.setParentDirection(adjacencies[i]); //direction of parent

                neighbor.gScore = tentativeGScore; //total gscore  = distance from start to neighbor

                neighbor.fScore = neighbor.gScore + heuristicFct(neighbor); //fscore = gscore + heuristic cost
            }
        }else {
            completed = true;
        }
    }

    /**
     * Helper method that retrieves the reachable adjacent neighbors
     *
     * @return Array of possible adjacent neighbors int Integer form [0] = North, [1] = East, [2] = South, [3] = West
     */
    private Integer[] getNeighbors(){
        HashSet<Integer> direction = new HashSet<>();

        for (int i = 0; i < 4; i++) { // 0 = North, 1 = East, 2 = South, 3 = West
            direction.add(i);
        }

        direction = removeEdges(direction); //prevents index out of bounds errors if current is an edge cell

        //removes adjacent cells from consideration if there is a wall between the current and adjacent cells
        direction = removeWalls(direction);

        if (direction.isEmpty()) { //if there are no unvisited neighbors
            return null;
        }

        return direction.toArray(new Integer[0]);
    }

    /**
     * Helper method to prevent index out of bounds errors on edge pieces
     *
     * @param direction the set of directions (integers 0 - 3)
     * @return direction set with removed direction depending on current cell position
     */
    private HashSet<Integer> removeEdges(HashSet<Integer> direction){
        if (current.colIndex == 0) { //left edge piece, remove possibility of a west cell from set
            direction.remove(3);
        }
        if (current.colIndex == maze.col - 1) { //right edge piece, remove possibility of a east cell from set
            direction.remove(1);
        }
        if (current.rowIndex == 0) { //top edge piece, remove possibility of a north cell from set
            direction.remove(0);
        }
        if (current.rowIndex == maze.rows - 1) { //bottom edge piece, remove possibility of a south cell from set
            direction.remove(2);
        }

        return direction;
    }

    /**
     * Helper method to remove adjacent cells from consideration if there is a wall between the current cell and it's neighbors
     * @param direction a hash set of directions from 0 to 3 inclusive
     * @return hash set with removed adjacencies
     */
    private HashSet<Integer> removeWalls(HashSet<Integer> direction){
        boolean[] walls = current.getDirection(); //get the state of each wall for the current cell

        for (int i = 0; i < walls.length; i++){
            if (walls[i]){direction.remove(i); }
        }

        return direction;
    }

    /**
     * Heuristic function that weighs the distance from param cell to the end cell
     *
     * @param cell the cell to be weighed
     * @return A double that represents the predicted weight of param cell to end
     */
    private double heuristicFct(MazeCell cell){
        //manhattan distance is the distance from this cell to the end cell
        int manhattanDistance = (Math.abs(cell.colIndex - maze.end.colIndex) + Math.abs(cell.rowIndex - maze.end.rowIndex));

        /**
         * return manhattan distance with a slight preference towards the end cell, used to break tie breakers
         * this is because if heuristicfct + cost fct = 0 then it may check more cells than actually needed
         */
        return manhattanDistance * (1.0 / 5000);
    }

    /**
     * Inner class of a custom comparator that uses fScore to weigh each cell
     */
    private class MazeCellComparator implements Comparator<MazeCell> {

        @Override
        public int compare(MazeCell cell, MazeCell otherCell) {
            if (cell.fScore < otherCell.fScore){
                return 1;
            }else if (cell.fScore > otherCell.fScore){
                return -1;
            }
            return 0;
        }
    }
}
