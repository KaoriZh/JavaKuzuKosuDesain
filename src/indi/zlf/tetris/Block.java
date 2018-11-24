package indi.zlf.tetris;

/**
 * Class of blocks
 * Generate different kinds of block with different index.
 */
public class Block {

    /**
     * Image of the block
     */
    int imageIndex;

    /**
     * To save the center-corresponding position
     * of each cell except the center in each type.
     */
    Cell[][] cells = new Cell[4][3];

    /**
     * The current type of the block
     */
    int currentType = 0;

    /**
     * The rotation period of the block
     */
    int period;

    /**
     * The score player can get when the block is successfully placed.
     */
    int score;

    /**
     * Set attributes of the block.
     * @param imageIndex
     * @param period
     * @param score
     */
    private void setParameter(int imageIndex, int period, int score) {
        this.imageIndex = imageIndex;
        this.period = period;
        this.score = score;
    }

    /**
     * Generate different kinds of block with different index.
     */
    public Block(int index) {

        if (index == 0) {
            //T
            setParameter(0, 4, 5);

            cells[0][0] = new Cell(-1, 0);
            cells[0][1] = new Cell(0, -1);
            cells[0][2] = new Cell(1, 0);

            cells[1][0] = new Cell(0, -1);
            cells[1][1] = new Cell(1, 0);
            cells[1][2] = new Cell(0, 1);

            cells[2][0] = new Cell(1, 0);
            cells[2][1] = new Cell(0, 1);
            cells[2][2] = new Cell(-1, 0);

            cells[3][0] = new Cell(0, 1);
            cells[3][1] = new Cell(-1, 0);
            cells[3][2] = new Cell(0, -1);

        } else if (index == 1) {
            //UZ
            setParameter(1, 2, 8);

            cells[0][0] = new Cell(-1, 0);
            cells[0][1] = new Cell(0, -1);
            cells[0][2] = new Cell(1, -1);

            cells[1][0] = new Cell(0, -1);
            cells[1][1] = new Cell(1, 0);
            cells[1][2] = new Cell(1, 1);

        } else if (index == 2) {
            //Z
            setParameter(2, 2, 8);

            cells[0][0] = new Cell(-1, -1);
            cells[0][1] = new Cell(0, -1);
            cells[0][2] = new Cell(1, 0);

            cells[1][0] = new Cell(1, -1);
            cells[1][1] = new Cell(1, 0);
            cells[1][2] = new Cell(0, 1);

        } else if (index == 3) {
            //I
            setParameter(3, 2, 6);

            cells[0][0] = new Cell(-1, 0);
            cells[0][1] = new Cell(1, 0);
            cells[0][2] = new Cell(2, 0);

            cells[1][0] = new Cell(0, -1);
            cells[1][1] = new Cell(0, 1);
            cells[1][2] = new Cell(0, 2);

        } else if (index == 4) {
            //UL
            setParameter(4, 4, 7);

            cells[0][0] = new Cell(-1, -1);
            cells[0][1] = new Cell(-1, 0);
            cells[0][2] = new Cell(1, 0);

            cells[1][0] = new Cell(0, -1);
            cells[1][1] = new Cell(1, -1);
            cells[1][2] = new Cell(0, 1);

            cells[2][0] = new Cell(-1, 0);
            cells[2][1] = new Cell(1, 0);
            cells[2][2] = new Cell(1, 1);

            cells[3][0] = new Cell(0, -1);
            cells[3][1] = new Cell(-1, 1);
            cells[3][2] = new Cell(0, 1);

        } else if (index == 5) {
            //L
            setParameter(5, 4, 7);

            cells[0][0] = new Cell(-1, 0);
            cells[0][1] = new Cell(1, -1);
            cells[0][2] = new Cell(1, 0);

            cells[1][0] = new Cell(0, -1);
            cells[1][1] = new Cell(0, 1);
            cells[1][2] = new Cell(1, 1);

            cells[2][0] = new Cell(-1, 0);
            cells[2][1] = new Cell(1, 0);
            cells[2][2] = new Cell(-1, 1);

            cells[3][0] = new Cell(-1, -1);
            cells[3][1] = new Cell(0, -1);
            cells[3][2] = new Cell(0, 1);

        } else if (index == 6) {
            //O
            setParameter(6, 1, 4);

            cells[0][0] = new Cell(0, -1);
            cells[0][1] = new Cell(1, -1);
            cells[0][2] = new Cell(1, 0);

        }

    }

}