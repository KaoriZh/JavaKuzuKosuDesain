package indi.zlf.tetris;

/**
 * The unit class of all blocks
 */
public class Cell {

    /**
     * X-coordinate on game map
     */
    int x;

    /**
     * Y-coordinate on game map
     */
    int y;

    /**
     * Generator
     * @param x
     * @param y
     */
    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The projection of x-coordinate from game map to screen.
     * @param x x-coordinate on game map
     * @return the projection of x-coordinate
     */
    public static int PorjectionX(int x) {
        return x * Parameter.GameFrame.BLOCK_WIDTH + Parameter.GameFrame.MAP_OFFSET_X;
    }

    /**
     * The projection of y-coordinate from game map to screen.
     * @param y y-coordinate on game map
     * @return the projection of y-coordinate
     */
    public static int PorjectionY(int y) {
        return y * Parameter.GameFrame.BLOCK_HEIGHT + Parameter.GameFrame.MAP_OFFSET_Y;
    }

    /**
     * To judge whether it is movable to (x, y).
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true for movable and otherwise false.
     */
    public static boolean isMovable(int x, int y) {

        if(x < 0 || x >= Parameter.GameFrame.MAP_WIDTH || y >= Parameter.GameFrame.MAP_HEIGHT) {
            return false;
        }
        return true;

    }

}
