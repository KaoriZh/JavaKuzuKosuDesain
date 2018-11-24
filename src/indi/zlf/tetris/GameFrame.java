package indi.zlf.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

/**
 * The main class of game frame
 */
public class GameFrame extends JFrame {

    /**
     * Generator
     * Set basic attributes of frame
     */
    public GameFrame() {

        setTitle(new String("TETRIS"));
        setSize(new Dimension(Parameter.GameFrame.MINIMUM_FRAME_WIDTH, Parameter.GameFrame.MINIMUM_FRAME_HEIGHT));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);
        addKeyListener(gamePanel);

        setLocationRelativeTo(null);

        Thread thread = new Thread(gamePanel);
        thread.start();

    }

}

/**
 * The panel component of game frame
 */
class GamePanel extends JPanel implements Runnable, KeyListener {

    /**
     * Game level
     */
    private static int level;

    /**
     * To record whether game is running or not.
     */
    private static boolean isRunning;

    /**
     * To record whether game is over or not.
     */
    private static boolean isOver;

    /**
     * The score of player
     */
    private static int score;

    /**
     * The index in block set of the block player is controlling.
     */
    private int currentBlockIndex;

    /**
     * The index in block set of the next block to be controlled.
     */
    private int nextBlockIndex;

    /**
     * The center of the current block
     */
    private static Cell currentCenter;

    /**
     * Background image
     */
    private static Image backgroundImage;

    /**
     * Block images
     */
    private static Image[] blockImages = new Image[7];

    /**
     * Images of numbers
     */
    private static Image[] numberImages = new Image[10];

    /**
     * Image for GAME OVER
     */
    private static Image overImage;

    /**
     * Game map
     * To record the indexs of existing cells.
     * Initialize to -1 ,which means there is no cell.
     */
    private static int[][] map = new int[Parameter.GameFrame.MAP_HEIGHT][Parameter.GameFrame.MAP_WIDTH];

    /**
     * "Current block" set
     */
    private static Block[] currentBlocks = new Block[7];

    /**
     * "Next block" set
     */
    private static Block[] nextBlocks = new Block[7];

    /**
     * Statically load images and initialize the game.
     */
    static {

        backgroundImage = Tool.getImage("img/GameBackground.jpg");
        for (int i = 0; i < 7; i++) {
            blockImages[i] = Tool.getImage("img/Block_" + i + ".png");
        }
        for (int i = 0; i < 10; i++) {
            numberImages[i] = Tool.getImage("img/" + i + ".png");
        }
        overImage = Tool.getImage("img/GAMEOVER.jpg");

        for (int i = 0; i < Parameter.GameFrame.MAP_HEIGHT; i++) {
            for (int j = 0; j < Parameter.GameFrame.MAP_WIDTH; j++) {
                map[i][j] = -1;
            }
        }
        for (int i = 0; i < 7; i++) {
            currentBlocks[i] = new Block(i);
            nextBlocks[i] = new Block(i);
        }

        isOver = false;
        isRunning = true;
        level = 1;
        score = 0;
        currentCenter = new Cell(3, 0);

    }

    /**
     * randomly initialize currentBlockIndex and nextBlockIndex.
     */
    GamePanel() {
        currentBlockIndex = (int) (Math.random() * 7);
        nextBlockIndex = (int) (Math.random() * 7);
    }

    /**
     * Process controller
     */
    @Override
    public void run() {

        int counter = 0;
        while (!isOver) {
            try {
                Thread.sleep(Parameter.GameFrame.REFRESH_INTERVAL);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (isRunning) {
                counter++;
                if (counter >= 7 - level) {
                    counter = 0;
                    check(1);
                }
            }
            repaint();
        }


    }

    /**
     * To judge whether the block can be moved down.
     * @param step the step to move down when it can be.
     */
    private void check(int step) {

        if (isMovable(0, 1)) {
            currentCenter.y += step;
        } else {
            map[currentCenter.y][currentCenter.x] = currentBlocks[currentBlockIndex].imageIndex;
            int currentType = currentBlocks[currentBlockIndex].currentType;
            for (int i = 0; i < 3; i++) {
                int x = currentCenter.x + currentBlocks[currentBlockIndex].cells[currentType][i].x;
                int y = currentCenter.y + currentBlocks[currentBlockIndex].cells[currentType][i].y;
                if (y >= 0) {
                    map[y][x] = currentBlocks[currentBlockIndex].imageIndex;
                } else {
                    isOver = true;
                }
            }
            score += currentBlocks[currentBlockIndex].score;
            delete();
            generateNext();
        }

    }

    /**
     * To judge whether translational motion is available.
     * @param x the offset of x-coordinate
     * @param y the offset of y-coordinate
     * @return true for available and otherwise false
     */
    private boolean isMovable(int x, int y) {

        int centerX = currentCenter.x + x;
        int centerY = currentCenter.y + y;
        if (!Cell.isMovable(centerX, centerY) || map[centerY][centerX] != -1) {
            return false;
        }

        int currentType = currentBlocks[currentBlockIndex].currentType;
        for (int i = 0; i < 3; i++) {
            int nextX = centerX + currentBlocks[currentBlockIndex].cells[currentType][i].x;
            int nextY = centerY + currentBlocks[currentBlockIndex].cells[currentType][i].y;
            if (Cell.isMovable(nextX, nextY)) {
                if (nextY >= 0 && map[nextY][nextX] != -1) {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;

    }

    /**
     * To judge whther the block is rotatable.
     * @return true for rotatable and otherwise false
     */
    private boolean isRotatable() {

        int nextType = (currentBlocks[currentBlockIndex].currentType + 1) % currentBlocks[currentBlockIndex].period;
        for (int i = 0; i < 3; i++) {
            int nextX = currentCenter.x + currentBlocks[currentBlockIndex].cells[nextType][i].x;
            int y = currentBlocks[currentBlockIndex].cells[nextType][i].y;
            int nextY = currentCenter.y + y;
            if (y >= 0 && (!Cell.isMovable(nextX, nextY) || map[nextY][nextX] != -1)) {
                return false;
            }
        }

        return true;

    }

    /**
     * To judge whether there exists row which can be removed,
     * and if so, remove all of these rows.
     */
    private void delete() {

        for (int i = 0; i < Parameter.GameFrame.MAP_HEIGHT; i++) {

            boolean isCancellable = true;
            for (int j = 0; j < Parameter.GameFrame.MAP_WIDTH; j++) {
                if (map[i][j] == -1) {
                    isCancellable = false;
                    break;
                }
            }

            if (isCancellable) {
                for (int j = i; j > 0; j--) {
                    for (int k = 0; k < Parameter.GameFrame.MAP_WIDTH; k++) {
                        map[j][k] = map[j - 1][k];
                    }
                }

                for (int j = 0; j < Parameter.GameFrame.MAP_WIDTH; j++) {
                    map[0][j] = -1;
                }
            }

        }

    }

    /**
     * Reset the type of the current block.
     * Shift to next block.
     * Generate the preview of the next block.
     */
    private void generateNext() {

        currentBlocks[currentBlockIndex].currentType = 0;
        currentCenter.x = 3;
        currentCenter.y = 0;
        currentBlockIndex = nextBlockIndex;
        nextBlockIndex = (int) (Math.random() * 7);

    }

    /**
     * listener for key-pressed motion
     * @param e key event
     */
    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_P) {
            if (isRunning) isRunning = false;
            else isRunning = true;
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            level = Math.min(level + 1, 6);
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            level = Math.max(level - 1, 1);
        } else if (isRunning) {

            if (e.getKeyCode() == KeyEvent.VK_DOWN && isMovable(0, 1)) {
                currentCenter.y += 1;
                check(0);
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT && isMovable(-1, 0)) {
                currentCenter.x -= 1;
                check(0);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && isMovable(1, 0)) {
                currentCenter.x += 1;
                check(0);
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE && isRotatable()) {
                currentBlocks[currentBlockIndex].currentType += 1;
                currentBlocks[currentBlockIndex].currentType %= currentBlocks[currentBlockIndex].period;
                check(0);
            }

        }

    }

    /**
     * listener for key-typed motion
     * @param e key event
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * listener for key-released motion
     * @param e key event
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Override paint method to paint frame.
     * @param g  paintbrush object
     */
    @Override
    public void paint(Graphics g) {

        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        drawMap(g);
        drawNextBlock(g);
        drawScoreAndLevel(g);
        drawCurrentBlock(g);
        if (isOver) {
            g.drawImage(overImage, 150, 250, this);
        }

    }

    /**
     * Paint the existing cells.
     * @param g paintbrush object
     */
    private void drawMap(Graphics g) {
        for (int i = 0; i < Parameter.GameFrame.MAP_HEIGHT; i++) {
            for (int j = 0; j < Parameter.GameFrame.MAP_WIDTH; j++) {
                if (map[i][j] != -1) {
                    g.drawImage(blockImages[map[i][j]], Cell.PorjectionX(j), Cell.PorjectionY(i), this);
                }
            }
        }
    }

    /**
     * Paint the preview of next block.
     * @param g paintbrush object
     */
    private void drawNextBlock(Graphics g) {

        int index = nextBlocks[nextBlockIndex].imageIndex;
        g.drawImage(blockImages[index], Parameter.GameFrame.NEXT_BLOCK_OFFSET_X, Parameter.GameFrame.NEXT_BLOCK_OFFSET_Y, this);
        for (int i = 0; i < 3; i++) {
            int x = Parameter.GameFrame.NEXT_BLOCK_OFFSET_X + nextBlocks[nextBlockIndex].cells[0][i].x * Parameter.GameFrame.BLOCK_WIDTH;
            int y = Parameter.GameFrame.NEXT_BLOCK_OFFSET_Y + nextBlocks[nextBlockIndex].cells[0][i].y * Parameter.GameFrame.BLOCK_HEIGHT;
            g.drawImage(blockImages[index], x, y, this);
        }

    }

    /**
     * Paint the level and score.
     * @param g paintbrush object
     */
    private void drawScoreAndLevel(Graphics g) {

        g.drawImage(numberImages[level], Parameter.GameFrame.LEVEL_OFFSET_X, Parameter.GameFrame.LEVEL_OFFSET_Y, this);

        if (score == 0) {
            g.drawImage(numberImages[0], Parameter.GameFrame.SCORE_OFFSET_X, Parameter.GameFrame.SCORE_OFFSET_Y, this);
        } else {
            Stack<Integer> scoreStack = new Stack<>();
            int s = score;
            while (s != 0) {
                scoreStack.push(s % 10);
                s /= 10;
            }
            int x = Parameter.GameFrame.SCORE_OFFSET_X;
            int y = Parameter.GameFrame.SCORE_OFFSET_Y;
            while (!scoreStack.empty()) {
                Integer number = scoreStack.peek();
                scoreStack.pop();
                g.drawImage(numberImages[number], x, y, this);
                x += Parameter.GameFrame.NUMBER_WIDTH;
            }
        }

    }

    /**
     * Paint the block currently controlled.
     * @param g paintbrush object
     */
    private void drawCurrentBlock(Graphics g) {

        if (map[currentCenter.y][currentCenter.x] != -1) {
            isOver = true;
            return;
        }
        int currentType = currentBlocks[currentBlockIndex].currentType;
        int[] currentX = new int[3];
        int[] currentY = new int[3];
        for (int i = 0; i < 3; i++) {
            int x = currentBlocks[currentBlockIndex].cells[currentType][i].x;
            int y = currentBlocks[currentBlockIndex].cells[currentType][i].y;
            currentX[i] = currentCenter.x + x;
            currentY[i] = currentCenter.y + y;
            if (y < 0) {
                continue;
            } else if (map[currentY[i]][currentX[i]] != -1) {
                isOver = true;
                return;
            }
        }
        int index = currentBlocks[currentBlockIndex].imageIndex;
        g.drawImage(blockImages[index], Cell.PorjectionX(currentCenter.x), Cell.PorjectionY(currentCenter.y), this);
        for (int i = 0; i < 3; i++) {
            if (currentY[i] >= 0) {
                g.drawImage(blockImages[index], Cell.PorjectionX(currentX[i]), Cell.PorjectionY(currentY[i]), this);
            }
        }

    }

}