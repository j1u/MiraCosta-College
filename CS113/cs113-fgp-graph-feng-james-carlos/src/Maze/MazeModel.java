package Maze;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

/**
 * MazeModel grid class that controls the maze generation algorithm (Depth first search) and the swing components
 *
 * @author James Lu, Carlos Miranda
 * @version 2.0
 */
public class MazeModel {

    /** The grid coordinates of the maze with dimensions already accounted for*/
    protected MazeCell[][] grid;
    /** The dimension of each box */
    protected int cellDimension;
    /** The current MazeCell in the stack that will be used to check nearby unvisited cells. */
    protected MazeCell current;
    /** The stack that will keep cells in visited order. It will be used to backtrack the cells that haven't been visited yet. Once it's empty it means there are no unvisited cells left. */
    protected Stack<MazeCell> stack;
    /** The width and height of the grid, does not account for the blocks in the grid. More like resolution in a sense */
    protected int width, height;
    /** The columns and rows using the value of: width/cellDimension and height/cellDimension. */
    protected int col, rows;
    /** The beginning and ending cell */
    protected MazeCell start, end;

    /**
     * Constructs a maze model with given width and height. The cell dimension will cut the width and height into blocks.
     * Example: a 10x10 model with cell dimension of 2 will create 25 boxes (5x5 in grid).
     * @param width
     * @param height
     * @param cellDimension
     */
    public MazeModel(int width, int height, int cellDimension) {

        if (width%cellDimension != 0) {
            width = width + (width%cellDimension);
        }

        if (height%cellDimension != 0) {
            height = height + (height%cellDimension);

        }

        this.cellDimension = cellDimension;

        /** Divides the width by dimension to check how many columns will be created */
        this.col = width / this.cellDimension;
        /** Divides the height by dimension to check how many rows will be created */
        this.rows = height / this.cellDimension;
        this.grid = new MazeCell[col][rows];
        this.stack = new Stack<>(); //init
        this.width = width;
        this.height = height;

        /** Create the grid with cells */
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < col; j++) {
                MazeCell cell = new MazeCell(j, i);
                grid[j][i] = cell;
            }
        }

        /** Initialize the starting position at the top left corner with the opening on the north direction */
        this.current = grid[0][0];
        this.current.setDirection(0, false);

        this.start = this.current;

        /** By pushing the cell into the stack, we declare the "first and last" data of the stack to be the start position. Do not change any value in the cell so mark is as visited*/
        stack.push(current);
        current.visited = true;

        /** Mark the end of the grid at the bottom right corner. The east will be the opening of the grid. */
        this.end = grid[col - 1][rows - 1];
        this.end.setDirection(1, false);

    }

    /** gets a random maze cell using the current cell as a pos */
    public void randomDFS() {
        MazeCell next = null;
        Integer direction = getRandomNeighbor();

        if (direction == null) { // if there are no unvisited neighbors
            current = stack.pop();
        } else {
            if (direction == 0) {
                next = grid[current.colIndex][current.rowIndex - 1];

            } else if (direction == 1) {
                next = grid[current.colIndex + 1][current.rowIndex];

            } else if (direction == 2) {
                next = grid[current.colIndex][current.rowIndex + 1];

            } else if (direction == 3) {
                next = grid[current.colIndex - 1][current.rowIndex];
            }


            current.setDirection(direction, false);
            next.setOppositeDirection(direction, false);

            stack.push(next);
            next.visited = true;

            current = next;

        }
    }

    /**
     * Selects a random adjacent cell
     *
     * @return MazeCell object of the randomly selected cell
     */
    public Integer getRandomNeighbor() {
        HashSet<Integer> direction = new HashSet<>();
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            direction.add(i);
        } // 0 = North, 1 = East, 2 = South, 3 = West

        direction = edgeCellRemoval(direction);

        direction = visitedCellRemoval(direction);

        if (direction.isEmpty()) { //if there are no unvisited neighbors, theyve all been visited
            return null;
        }

        int index = random.nextInt(direction.size()); //selects a random int from 0 to direction size
        Iterator<Integer> iter = direction.iterator();
        for (int i = 0; i < index; i++) {
            iter.next(); //randomly iterate through set
        }

        return iter.next(); //selection is the next item in the set
    }

    /**
     * Helper method to prevent index out of bounds errors on edge pieces
     *
     * @param direction the set of directions (integers 0 - 3)
     * @return direction set with removed direction depending on current cell position
     */
    private HashSet<Integer> edgeCellRemoval(HashSet<Integer> direction) {


        if (current.colIndex == 0) { //left edge piece, remove possibility of a west cell from set
            direction.remove(3);
        }
        if (current.colIndex == col - 1) { //right edge piece, remove possibility of a east cell from set
            direction.remove(1);
        }
        if (current.rowIndex == 0) { //top edge piece, remove possibility of a north cell from set
            direction.remove(0);
        }
        if (current.rowIndex == rows - 1) { //bottom edge piece, remove possibility of a south cell from set
            direction.remove(2);
        }

        return direction;
    }

    /**
     * Helper method to remove visited cells from possible direction set
     *
     * @param direction the set of directions (integers 0 - 3)
     * @return direction set with removed direction depending on which adjacent cells have been visited
     */
    private HashSet<Integer> visitedCellRemoval(HashSet<Integer> direction) {
        if (direction.contains(0)) { //if there is an adjacent cell north
            MazeCell newCell = grid[current.colIndex][current.rowIndex - 1];
            if (newCell.visited) {
                direction.remove(0);
            } //remove if visited

        }
        if (direction.contains(1)) { //if there is an adjacent cell east
            MazeCell newCell = grid[current.colIndex + 1][current.rowIndex];
            if (newCell.visited) {
                direction.remove(1);
            } //remove if visited
        }
        if (direction.contains(2)) { //if there is an adjacent cell south
            MazeCell newCell = grid[current.colIndex][current.rowIndex + 1];
            if (newCell.visited) {
                direction.remove(2);
            } //remove if visited
        }
        if (direction.contains(3)) { //if there is an adjacent cell west
            MazeCell newCell = grid[current.colIndex - 1][current.rowIndex];
            if (newCell.visited) {
                direction.remove(3);
            } //remove if visited
        }

        return direction;
    }

}
