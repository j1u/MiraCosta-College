package Maze;

import javax.swing.*;
import java.awt.*;

/**
 * Scalable maze generator that creates a maze with given height, width, and cell size
 * @author James Lu
 * @version 1.0
 */
public class MazeGenerator {

    public static void main(String[] args) {

        int mazeDimension = 500;
        int cellDimension = 20;

        JFrame window = new JFrame();

        MazeModel maze = new MazeModel(mazeDimension,mazeDimension, cellDimension);

        AStar search = new AStar(maze);

        MazeView view = new MazeView(maze, search);

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(view);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(panel);

        window.pack();
        window.setSize(panel.getPreferredSize());
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
