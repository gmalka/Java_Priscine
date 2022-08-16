package edu.school21.printer.App;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import edu.school21.printer.Logic.Logic;
import java.io.File;

public class App {

    private static final String IMAGE_PATH = "src/resources/image.bmp";

    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        if (args.length != 2) {
            System.err.println("Error: Not enough arguments!");
            System.exit(-1);
        }
        BufferedImage image = parseIt();
        Logic.outImage(image, args[0].charAt(0), args[1].charAt(0));
    }

    public static BufferedImage parseIt() {
        try {
            File file = new File(IMAGE_PATH);
            if (file.exists()) {
                return ImageIO.read(file);
            } else throw new Exception("not found file");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(-1);
        }
        return null;
    }
}
