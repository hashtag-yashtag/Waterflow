/**
 * WaterFlow
 * Water flow problem which start every empty cell in the first row.
 *
 * @author Yash Gupta
 * @CS login ID: ygupta
 * @PSO section: P17
 * @date September 9, 2016
 */

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class WaterFlow {
    public int rows; //number of rows of the grid
    public int columns; //number of columns of grid
    public int[][] delayTimeGrid; //time of water in the grid, and 0 means the water is blocked
    public int[][] reachTimeGrid; //time of water flow reach that point
    public boolean[][] earliestPathGrid; //a boolean grid that identify the shortest path. For visualization purpose
    public List<Cell> earliestPath = new LinkedList<Cell>(); //The earliest flow path
    public WaterFlowVisualization visualization;
    public Scanner s = new Scanner(System.in);
    public boolean visual = true;
    public boolean reached = true;
    public LinkedList<Cell>[] list = new LinkedList[4];
    //TODO: add variables that you need


    /**
     * The default constructor
     * Read Input from terminal, do not modify it
     **/
    public WaterFlow() {
        //get inputs
        rows = s.nextInt();
        columns = s.nextInt();
        delayTimeGrid = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                delayTimeGrid[i][j] = s.nextInt();  //Taking input for delayTimeGrid
            }
        }

        for (int i = 0; i < list.length; i++) {
            list[i] = new LinkedList<Cell>();
        }
        //TODO: initial the variables
        reachTimeGrid = new int[rows][columns];
        earliestPathGrid = new boolean[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                reachTimeGrid[i][j] = -1;       //initializing all elements in reachTimeGrid as -1
                earliestPathGrid[i][j] = false;     //initializing all elements at earliestPathGrid as false
            }
        }
    }

    /**
     * @param rows    takes input of rows from the AutoTesting
     * @param columns takes input of rows from the AutoTesting
     * @param grid    takes input of delayTimeGrid from the AutoTesting
     */
    /*public WaterFlow(int rows, int columns, int[][] grid) {
        //get inputs
        this.rows = rows;
        this.columns = columns;

        delayTimeGrid = grid;

        for (int i = 0; i < list.length; i++) {
            list[i] = new LinkedList<Cell>();
        }
        //TODO: initial the variables
        reachTimeGrid = new int[rows][columns];
        earliestPathGrid = new boolean[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                reachTimeGrid[i][j] = -1;
                earliestPathGrid[i][j] = false;
            }
        }
    }*/

    /**
     * Update the water flow once.
     *
     * @return Null
     */
    public void flow() {
        // Don't Change this part, it's visualize Part
        if (visual) try {
            Thread.sleep(500);
            //count++;
            //if (count == 5) Thread.sleep(20000);
            visualization.repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //TODO: Start your implementation of progress here

        for (int j = 0; j < list[0].size(); j++) {
            Cell cell = list[0].get(j); //Pulling cell from the list[0]

            int row = cell.row; //Pulling row number from the cell
            int column = cell.column;   //Pulling column number from the cell

            int right = getElementRight(row, column);   //getting the element to the right of the current element
            int left = getElementLeft(row, column);     //getting the element to the left of the current element
            int down = getElementDown(row, column);     //getting the element to the bottom of the current element

            if (right != -1) {  //See if the element is at an invalid location

                if (right != 0 && reachTimeGrid[row][column + 1] == -1) {
                    list[right].add(new Cell(row, column + 1));     //adding the element to the linked list
                    reachTimeGrid[row][column + 1] = reachTimeGrid[row][column] + delayTimeGrid[row][column];
                    //ReachTimeGrid at the new position would be the sum of reachTimeGrid at previous position
                    //And the delayTimeGrid at the previous position
                }
            }
            if (left != -1 && reachTimeGrid[row][column - 1] == -1) { //See if the element is at an invalid location
                if (left != 0) {

                    list[left].add(new Cell(row, column - 1));      //adding the elememnt to the linked list
                    reachTimeGrid[row][column - 1] = reachTimeGrid[row][column] + delayTimeGrid[row][column];
                    //ReachTimeGrid at the new position would be the sum of reachTimeGrid at previous position
                    //And the delayTimeGrid at the previous position
                }
            }
            if (down != -1 && reachTimeGrid[row + 1][column] == -1) { //See if the element is at an invalid location
                if (down != 0) {

                    list[down].add(new Cell(row + 1, column));      //adding the element to the linked list
                    reachTimeGrid[row + 1][column] = reachTimeGrid[row][column] + delayTimeGrid[row][column];
                    //ReachTimeGrid at the new position would be the sum of reachTimeGrid at previous position
                    //And the delayTimeGrid at the previous position
                }
            }
        }

        /**
         * Shifting lists one up, while clearing the top list to the array
         */
        list[0] = list[1];
        list[1] = list[2];
        list[2] = list[3];
        list[3] = new LinkedList<Cell>();

    }


    /**
     * Calculate the waterflow until it ends.
     */
    public void determineFlow() {

        /**
         * Scanning the first row and adding
         * all the 1's to list[0]
         * all the 2's to list[1]
         * all the 3's to list[2]
         *
         * The Algorithm basically adds all the elements to the lists respectively and then
         * emptying the list[0] into the reachTimeGrid, and then shifting lists upwards.
         */
        for (int i = 0; i < columns; i++) {
            Cell cell = new Cell(0, i);
            if (delayTimeGrid[0][i] == 1) {
                list[0].add(cell);
                reachTimeGrid[0][i] = 0;
            }
            if (delayTimeGrid[0][i] == 2) {
                list[1].add(cell);
                reachTimeGrid[0][i] = 0;
            }
            if (delayTimeGrid[0][i] == 3) {
                list[2].add(cell);
                reachTimeGrid[0][i] = 0;
            }
            if (delayTimeGrid[0][i] == 0) {
                reachTimeGrid[0][i] = -1;
            }
        }


        while (list[0].size() > 0 || list[1].size() > 0 || list[2].size() > 0 || list[3].size() > 0) {
            this.flow();
        }
    }

    /**
     * @param row
     * @param col
     * @return the element to the right of the current element and -1 for invalid positions
     */
    public int getElementRight(int row, int col) {
        if (col == columns - 1 || delayTimeGrid[row][col + 1] == 0) {
            return -1;
        }

        return delayTimeGrid[row][col + 1];
    }


    /**
     * @param row
     * @param col
     * @return the element to the Left of the current element and -1 for invalid positions
     */
    public int getElementLeft(int row, int col) {
        if (col == 0 || delayTimeGrid[row][col - 1] == 0) {
            return -1;
        }
        return delayTimeGrid[row][col - 1];
    }

    /**
     * @param row
     * @param col
     * @return the element to the bottom of the current element and -1 for invalid positions
     */
    public int getElementDown(int row, int col) {
        if (row == rows - 1 || delayTimeGrid[row + 1][col] == 0) {
            return -1;
        }

        return delayTimeGrid[row + 1][col];
    }

    /**
     * @param row
     * @param col
     * @return the element to the top of the current element and -1 for invalid positions
     */
    public int getElementUp(int row, int col) {
        if (row == 0 || delayTimeGrid[row - 1][col] == 0) {
            return -1;
        }

        return delayTimeGrid[row - 1][col];
    }


    /**
     * Create the Visualization of the Waterflow
     */
    public void visualize() {
        visualization = new WaterFlowVisualization(this);

        JFrame frame = new JFrame("Water Flow Visualization");
        frame.add(visualization);
        visualization.init();
        visualization.start();
        frame.setSize(visualization.getSize());
        frame.setVisible(true);
    }

    /**
     * Find one shortest path and update the shortestGrid.
     */
    public void earliestFlowPath() {
        Cell cell;

        int min = Integer.MAX_VALUE;    //Max value is to check the number to the arrays
        int r = 0, c = 0;

        /**
         * gets the smallest element from the last row in reachTimeGrid
         */
        for (int i = 0; i < columns; i++) {

            if (reachTimeGrid[rows - 1][i] != -1) {
                if (reachTimeGrid[rows - 1][i] + delayTimeGrid[rows - 1][i] < min) {
                    min = reachTimeGrid[rows - 1][i] + delayTimeGrid[rows - 1][i];
                    r = rows - 1;
                    c = i;
                }
            }
        }

        cell = new Cell(r, c);
        earliestPath.add(cell);
        while (cell.row != 0) {

            int left = Integer.MAX_VALUE, right = Integer.MAX_VALUE, up = Integer.MAX_VALUE;

            /**
             * Add element Left, Right or Up from reachTimeGrid to the earliest path
             */
            if (cell.column != 0 && reachTimeGrid[cell.row][cell.column - 1] != -1) {
                left = getElementLeft(cell.row, cell.column) + reachTimeGrid[cell.row][cell.column - 1];
            }

            if (cell.column != columns - 1 && reachTimeGrid[cell.row][cell.column + 1] != -1) {
                right = getElementRight(cell.row, cell.column) + reachTimeGrid[cell.row][cell.column + 1];
            }

            if (cell.row != 0 && reachTimeGrid[cell.row - 1][cell.column] != -1) {
                up = getElementUp(cell.row - 1, cell.column) + reachTimeGrid[cell.row - 1][cell.column];
            }


            /**
             * Preference: Up, Left then Right.
             * Adds the smallest element from the three directions and adds it to the earliestFlowPath
             * Linked List
             */
            if (up <= left && up <= right) {
                earliestPath.add(new Cell(cell.row - 1, cell.column));
                cell = new Cell(cell.row - 1, cell.column);
            } else if (left < up && left <= right) {
                earliestPath.add(new Cell(cell.row, cell.column - 1));
                cell = new Cell(cell.row, cell.column - 1);
            } else if (right < left && right < up) {
                earliestPath.add(new Cell(cell.row, cell.column + 1));
                cell = new Cell(cell.row, cell.column + 1);
            }

        }
        for (int i = 0; i < earliestPath.size(); i++) {
            earliestPathGrid[earliestPath.get(i).row][earliestPath.get(i).column] = true;
        }
    }

    /***************************
     * GETTER METHODS
     ***************************/

    public int[][] getDelayTimeGrid() {
        return delayTimeGrid;
    }


    public int[][] getReachTimeGrid() {
        return reachTimeGrid;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public boolean[][] getEarliestPathGrid() {
        return earliestPathGrid;
    }

    public boolean isReached() {
        return reached;
    }
}
