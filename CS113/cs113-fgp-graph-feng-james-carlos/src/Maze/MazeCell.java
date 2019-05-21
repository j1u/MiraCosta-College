package Maze;
/**
 * Class MazeCell that contains data regarding each cell in the grid
 *
 * @author James Lu, Carlos Miranda
 * @version 2.0
 */
public class MazeCell {

    /** Index of each cell using the grid format */
    protected int colIndex, rowIndex;
    /** The directions using index as the direction and the value if the direction is valid.
     * [0] = North, [1] = East, [2] = South, [3] = West */
    protected boolean[] directions;
    /** If the grid has been manipulated already. */
    protected boolean visited;

    protected MazeCell parent;
    protected int parentDirection;
    protected double gScore;
    protected double fScore;

    /**
     * Constructs a MazeCell object with given coordinates
     * @param colIndex The column index in the grid
     * @param rowIndex The row index in the grid
     */
    public MazeCell(int colIndex, int rowIndex) {
        this.colIndex = colIndex;
        this.rowIndex = rowIndex;
        this.directions = new boolean[]{true, true, true, true}; //all walls must be true to have a grid

        this.parent = null;
        this.parentDirection = 0;
        this.gScore = Double.POSITIVE_INFINITY;
        this.fScore = Double.POSITIVE_INFINITY;
    }

    public boolean[] getDirection() {
        return this.directions;
    }

    /**
     * Removes or adds a "wall" from a cell by changing the boolean array
     * @param index the index of the wall, (0 - 3)
     * @param hasWall removal or addition of the wall using true or false
     */
    public void setDirection(int index, boolean hasWall) {
        this.directions[index] = hasWall;
    }

    /**
     * Removes or adds a "wall" opposite from the cell by changing the boolean array. The reason we want to have this method is because a box will overlap with another box's sketch. So reverse 180deg to clear it.
     * @param index the index from which you want the opposite direction (0 changes walls of 2, 1 changes walls of 3, etc.)
     * @param hasWall removal or addition of the wall using true or false
     */
    public void setOppositeDirection(int index, boolean hasWall) {
        if ((index + 2) > 3) {
            index = (index + 2) % 4;
        } else {
            index = index + 2;
        }
        setDirection(index, hasWall);
    }

    /**
     * Sets the gScore of this cell
     * @param newCost the desired cost of the gScore
     */
    public void setGScore(int newCost){
        this.gScore = newCost;
    }

    /**
     * Sets the fScore of this cell
     * @param newCost the desired cost of the fScore
     */
    public void setFScore(double newCost){
        this.fScore = newCost;
    }

    /**
     * Sets the parent of this cell (used by Astar search alg)
     * @param cell the parent of this cell
     */
    public void setParent(MazeCell cell){
        this.parent = cell;
    }

    /**
     * Sets the direction from which the parent of this cell is located (used by Astar search alg)
     * @param direction the direction of the parent [0] = North, [1] = East, [2] = South, [3] = West
     */
    public void setParentDirection(int direction){
        if ((direction + 2) > 3) {
            direction = (direction + 2) % 4;
        } else {
            direction = direction + 2;
        }
        this.parentDirection = direction;
    }
}
