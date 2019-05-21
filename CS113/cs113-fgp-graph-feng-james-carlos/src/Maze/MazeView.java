package Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Stack;

/**
 *
 *
 * @author James Lu, Carlos Miranda
 * @version 2.0
 */
public class MazeView extends JComponent implements ActionListener {

    /**
     * The Maze that will be drawn to the grid
     */
    private MazeModel maze;
    /**
     * The astar search algorithm. Has methods that will let the timer know when to stop and create the path.
     */
    private AStar search;
    /**
     * Creates a queue of the path to take from start to finish using the maze cells
     */
    private java.util.Queue<MazeCell> cellsPath;
    /**
     * A scheduled timer to keep looping the paintComponent
     */
    private Timer tm = new Timer(1, this);

    /**
     * A constructor that initializes the maze and search algorithm
     *
     * @param maze   the maze that will be drawn
     * @param search the search algorithm
     */
    public MazeView(MazeModel maze, AStar search) {
        this.maze = maze;
        this.search = search;
        this.cellsPath = new LinkedList<>();
    }


    /**
     * Paints the maze to the screen
     *
     * @param g The graphics package
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);//calls superclass to paint an image

        drawGrid(g); //draws the grid to the screen

        drawVisited(g, maze.current); //draws the square and shows how the graph theory works while visiting cells

        if (maze.stack.empty()) { //once it reaches the end
            drawCompletePath(g, maze.end); //draw the path
        }

        tm.start(); //start timer

        if (search.completed) { //once the algorithm is completely done searching
            drawLines(g); //draw start to finish and stop
            tm.stop();
        }
    }

    /**
     * Performs the action in the timer loop.
     *
     * @param e The action event from the timer loop
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!maze.stack.empty()) { //if its not an empty stack
            maze.randomDFS(); //selects random cell that hasnt been visited
        } else {
            search.run();//once the stack is empty run the search algorithm

        }

        revalidate();
        repaint(); //paint the image again
    }

    /**
     * The size of window resolution
     *
     * @return
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(maze.width + 50, maze.height + 50);
    }

    /**
     * Helper method to draw the grid in JFrame
     *
     * @param g graphics component
     */
    private void drawGrid(Graphics g) {
        int x, y;
        MazeCell cell;

        /** loop all columns */
        for (int i = 0; i < maze.col; i++) {
            /** loop all rows */
            for (int j = 0; j < maze.rows; j++) {

                cell = maze.grid[i][j]; //coordinate
                x = cell.colIndex * maze.cellDimension; //x coord
                y = cell.rowIndex * maze.cellDimension; //y coord

                g.setColor(Color.BLACK);

                //draws the walls of the cell
                if (cell.directions[0]) {
                    g.drawLine(x, y, x + maze.cellDimension, y);
                }
                if (cell.directions[1]) {
                    g.drawLine(x + maze.cellDimension, y, x + maze.cellDimension, y + maze.cellDimension);
                }
                if (cell.directions[2]) {
                    g.drawLine(x + maze.cellDimension, y + maze.cellDimension, x, y + maze.cellDimension);
                }
                if (cell.directions[3]) {
                    g.drawLine(x, y + maze.cellDimension, x, y);
                }
            }
        }
    }

    /**
     * Draws the lines from start to finish.
     *
     * @param g the graphics
     */
    private void drawLines(Graphics g) {

        MazeCell old = this.cellsPath.remove(); //remove first path/edge
        Stack<Color> colors = getGradient(this.cellsPath.size()); // the stack of colors in gradient from red to blue using size as the ratio

        while (!this.cellsPath.isEmpty()) //while the stack isnt empty, keep making edges towards the cells. make those paths
        {
            int oldX, oldY, newX, newY;
            oldX = old.colIndex * maze.cellDimension + maze.cellDimension / 2;
            oldY = old.rowIndex * maze.cellDimension + maze.cellDimension / 2;
            MazeCell curr = this.cellsPath.remove();
            newX = curr.colIndex * maze.cellDimension + maze.cellDimension / 2;
            newY = curr.rowIndex * maze.cellDimension + maze.cellDimension / 2;

            g.setColor(colors.pop());
            g.drawLine(oldX, oldY, newX, newY); //connect the old cell to the new cell from half points
            old = curr; //update

        }
    }

    /**
     * Creates a gradient of red to blue colors with given size, n, as the distance.
     * @param n number of shades of red to blue
     * @return a stack of colors
     */
    private Stack<Color> getGradient(int n) {

        Stack<Color> colors = new Stack<>();

        for (int i = 0; i < n; i++) //ratio size
        {
            float ratio = (float) i / n; //ratio between loop and size of path
            //The next formula is a linear gradient to go from red to blue
            int R = (int) (Color.BLUE.getRed() * ratio + Color.RED.getRed() * (1 - ratio));
            int G = (int) (Color.BLUE.getGreen() * ratio + Color.RED.getGreen() * (1 - ratio));
            int B = (int) (Color.BLUE.getBlue() * ratio + Color.RED.getBlue() * (1 - ratio));


            Color color = new Color(R, G, B);
            colors.push(color); //push the color into the stack
        }
        return colors;
    }

    /**
     * Draws the complete path in dots. Will be replaced with lines in gradient of colors
     * @param g
     * @param cell
     */
    private void drawCompletePath(Graphics g, MazeCell cell) {

        if (cell == null) { //base case
            return;
        }

        int x, y;

        x = (cell.colIndex * maze.cellDimension) + maze.cellDimension / 2;
        y = (cell.rowIndex * maze.cellDimension) + maze.cellDimension / 2;

        if (!this.cellsPath.contains(cell)) {
            this.cellsPath.add(cell);
        }

        g.setColor(Color.BLUE);
        g.drawLine(x, y, x, y);

        drawCompletePath(g, cell.parent);

    }

    /**
     * Helper method to draw the visited cells in JFrame
     *
     * @param g graphics component
     */
    private void drawVisited(Graphics g, MazeCell cell) {
        int x, y;
        x = cell.colIndex * maze.cellDimension;
        y = cell.rowIndex * maze.cellDimension;

        if (cell.visited) {
            g.setColor(Color.RED);
            g.fillRect(x, y, maze.cellDimension, maze.cellDimension);
        }

    }
}
