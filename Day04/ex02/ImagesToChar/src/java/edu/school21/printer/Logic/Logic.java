package edu.school21.printer.Logic;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

import java.awt.image.BufferedImage;

@Parameters(separators = "=")
public class Logic {
    @Parameter(names = {"--white", "-white"})
    private String white;
    @Parameter (names = {"--black", "-black"})
    private String black;

    public void outImage(BufferedImage image)
    {
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if ((image.getRGB(j, i) & 0x00FFFFFF) == 0)
                    System.out.print(Ansi.colorize("  ", resolveColor(white)));
                else
                    System.out.print(Ansi.colorize("  ", resolveColor(black)));
            }
            System.out.println();
        }
    }

    private Attribute resolveColor(String input) {
        switch (input) {
            case "RED":
                return Attribute.RED_BACK();
            case "GREEN":
                return Attribute.GREEN_BACK();
            case "BLUE":
                return Attribute.BLUE_BACK();
            case "BLACK":
                return Attribute.BLACK_BACK();
            case "WHITE":
                return Attribute.WHITE_BACK();
            case "YELLOW":
                return Attribute.YELLOW_BACK();
            case "BRIGHT_RED":
                return Attribute.BRIGHT_RED_BACK();
            case "BRIGHT_GREEN":
                return Attribute.BRIGHT_GREEN_BACK();
            case "BRIGHT_BLUE":
                return Attribute.BRIGHT_BLUE_BACK();
            case "BRIGHT_BLACK":
                return Attribute.BRIGHT_BLACK_BACK();
            case "BRIGHT_WHITE":
                return Attribute.BRIGHT_WHITE_BACK();
            case "BRIGHT_YELLOW":
                return Attribute.BRIGHT_YELLOW_BACK();
            default: {
                System.err.println("Color dont finded");
                System.exit(-1);
            }
        }
        return null;
    }
}