package edu.school21.printer.App;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import edu.school21.printer.Logic.Logic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

public class App {

    private static final String IMAGE_PATH = "src/resources/it.bmp";

    private static final int LENGTH_OF_LIST_ARGUMENTS = 4;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Error: Not enough arguments!");
            System.exit(-1);
        }
        Logic logic = new Logic();
        try {
            JCommander.newBuilder()
                    .addObject(logic)
                    .build()
                    .parse(args);
        }
        catch (Exception e)
        {
            System.err.println("Can't parse arguments!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        BufferedImage image = parseIt();
        logic.outImage(image);
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

    public static class Args {
        @Parameter
        private List<String> parameters = new ArrayList<>();

        @Parameter(names = { "-log", "-verbose" }, description = "Level of verbosity")
        private Integer verbose = 1;

        @Parameter(names = "-groups", description = "Comma-separated list of group names to be run")
        private String groups;

        @Parameter(names = "-debug", description = "Debug mode")
        private boolean debug = false;
    }
}
