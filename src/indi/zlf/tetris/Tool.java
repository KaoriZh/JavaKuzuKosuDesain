package indi.zlf.tetris;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Tool class
 */
public class Tool {

    /**
     * method to get image with file path
     * @param path the file path of image
     * @return an image object
     */
    public static Image getImage(String path) {
        BufferedImage bufferedImagei = null;
        try {
            URL url = Tool.class.getClassLoader().getResource(path);
            bufferedImagei = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImagei;
    }

}
