package edu.school21.printer.Logic;

import java.awt.image.BufferedImage;

public class Logic {
    public static void outImage(BufferedImage image, char noColorChar, char coloredChar)
    {
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if ((image.getRGB(j, i) & 0x00FFFFFF) == 0)
                    System.out.print(coloredChar);
                else
                    System.out.print(noColorChar);
            }
            System.out.println();
        }
    }
}