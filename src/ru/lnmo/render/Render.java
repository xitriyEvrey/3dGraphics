package ru.lnmo.render;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Render {

   /* public static void render(BufferedImage img){
//        img.setRGB(500, 300, new Color(255, 0, 200).getRGB());
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                img.setRGB(i, j, new Color(i * j % 256, (i + j) % 256, (i * i + j * j) % 256).getRGB() );
            }
        }
    }*/

    //Стоит начать с этого
    public static void renderLine(BufferedImage img, double x1, double y1, double x2, double y2, Color color){
        double k = (y1-y2)/(x1-x2);
        double b = (x1*y2-x2*y1)/(x1-x2);
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                if (k*x+b == y){
                    img.setRGB(x, y, color.getRGB());
                }else {
                    img.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }
    }
}
