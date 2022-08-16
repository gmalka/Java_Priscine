package Java.edu.school21.printer.App;

import Java.edu.school21.printer.Logic.Logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class App {
    public static void main(String[] args) {
        BufferedImage image = parseIt(args);
        Logic.outImage(image, args[0].charAt(0), args[1].charAt(0));
    }

    public static BufferedImage parseIt(String[] args) {
        if (args.length != 3) {
            System.err.println("Error: Not enough arguments!");
            System.exit(-1);
        }
        try {
            File file = new File(args[args.length - 1]);
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
