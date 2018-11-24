package indi.zlf.tetris;

/**
 * Class for parameters
 */
public class Parameter {

    /**
     * Parameters of start frame
     */
    class StartFrame {

        /**
         * The offset, width and height of buttons(unit: px)
         */
        public static final int BUTTON_OFFSET = 360;
        public static final int BUTTON_WIDTH = 80;
        public static final int BUTTON_HEIGHT = 50;

        /**
         * The width and height of frame(unit: px)
         */
        public static final int MINIMUM_FRAME_WIDTH = 500;
        public static final int MINIMUM_FRAME_HEIGHT = 300;

        /**
         * Text of "Help"
         */
        public static final String TEXT = "HELP\n" +
                "←/→ : MOVE\n" +
                "↓ : QUICK DROP\n" +
                "SPACE : ROTATE\n" +
                "W/S : CHANGE LEVEL\n" +
                "P : STOP\n";

    }

    /**
     * Parameters of game frame
     */
    class GameFrame {

        /**
         * The minimum width and height of frame(unit: px)
         */
        public static final int MINIMUM_FRAME_WIDTH = 800;
        public static final int MINIMUM_FRAME_HEIGHT = 800;

        /**
         * The interval of screen refresh(unit: ms)
         */
        public static final int REFRESH_INTERVAL = 100;

        /**
         * The number of rows and columns of game map
         */
        public static final int MAP_HEIGHT = 16;
        public static final int MAP_WIDTH = 8;

        /**
         * The width and height of cell(unit: px)
         */
        public static final int BLOCK_WIDTH = 49;
        public static final int BLOCK_HEIGHT = 41;

        /**
         * The projection offset of game map(unit: px)
         */
        public static final int MAP_OFFSET_X = 43;
        public static final int MAP_OFFSET_Y = 69;

        /**
         * The projection offset of the "NEXT BLOCK" field(unit: px)
         */
        public static final int NEXT_BLOCK_OFFSET_X = 568;
        public static final int NEXT_BLOCK_OFFSET_Y = 128;

        /**
         * The projection offset of the "LEVEL" field(unit: px)
         */
        public static final int LEVEL_OFFSET_X = 630;
        public static final int LEVEL_OFFSET_Y = 203;

        /**
         * The projection offset of the "SCORE" field(unit: px)
         */
        public static final int SCORE_OFFSET_X = 630;
        public static final int SCORE_OFFSET_Y = 271;

        /**
         * The width of the number images(unit: px)
         */
        public static final int NUMBER_WIDTH = 36;

    }

}
