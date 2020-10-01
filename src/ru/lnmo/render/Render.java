package ru.lnmo.render;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Render {

//    public static void render(BufferedImage img){
//          img.setRGB(500, 300, new Color(255, 0, 200).getRGB());
//        for (int i = 0; i < img.getWidth(); i++) {
//            for (int j = 0; j < img.getHeight(); j++) {
//                img.setRGB(i, j, new Color(i * j % 256, (i + j) % 256, (i * i + j * j) % 256).getRGB() );
//            }
//        }
//    }

    //Стоит начать с этого
    public static void renderLine(BufferedImage img, int x1, int y1, int x2, int y2, Color color) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
            for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                if (dx > dy) {
                    img.setRGB(x, ((x - x1) * (y2 - y1) / (x2 - x1)) + y1, color.getRGB());
                } else {
                    img.setRGB(((y - y1) * (x2 - x1) / (y2 - y1)) + x1, y, color.getRGB());
                }
            }
        }
    }




    public static void renderTriangle(BufferedImage img, int x1, int y1, int x2, int y2, int x3, int y3, Color color){
       Vector AB = new Vector(new double[]{x2, y2}).sum(new Vector(new double[]{x1, y1}).scMult(-1));
       Vector AC = new Vector(new double[]{x3, y3}).sum(new Vector(new double[]{x1, y1}).scMult(-1));
        for (int x = Math.min(x1, Math.min(x2, x3)); x <= Math.max(x1, Math.max(x2, x3)); x++) {
            for (int y = Math.min(y1, Math.min(y2, y3)); y < Math.max(y1, Math.max(y2, y3)); y++) {
                Vector PA = new Vector(new double[]{x1, y1}).sum(new Vector(new double[]{x, y}).scMult(-1));
                Vector V = new Vector(new double[]{AB.get(0), AC.get(0), PA.get(0)}).CrossProd(new Vector(new double[]{AB.get(1), AC.get(1), PA.get(1)}));
                double u = (V.get(0)/V.get(2));
                double v = (V.get(1)/V.get(2));
                if (u + v <= 1 && u >= 0 && v >= 0){
                    img.setRGB(x, y, new Color((int) (u*255), (int) (v*255), (int) ((1-u-v)*255)).getRGB());
                }
            }
        }
    }

    public static void renderOBJTriangle(BufferedImage img, int x1, int y1, int x2, int y2, int x3, int y3, double l1, double l2, double l3){
        Vector AB = new Vector(new double[]{x2, y2}).sum(new Vector(new double[]{x1, y1}).scMult(-1));
        Vector AC = new Vector(new double[]{x3, y3}).sum(new Vector(new double[]{x1, y1}).scMult(-1));
        for (int x = Math.min(x1, Math.min(x2, x3)); x <= Math.max(x1, Math.max(x2, x3)); x++) {
            for (int y = Math.min(y1, Math.min(y2, y3)); y < Math.max(y1, Math.max(y2, y3)); y++) {
                Vector PA = new Vector(new double[]{x1, y1}).sum(new Vector(new double[]{x, y}).scMult(-1));
                Vector V = new Vector(new double[]{AB.get(0), AC.get(0), PA.get(0)}).CrossProd(new Vector(new double[]{AB.get(1), AC.get(1), PA.get(1)}));
                double u = (V.get(0)/V.get(2));
                double v = (V.get(1)/V.get(2));
                if (u + v <= 1 && u >= 0 && v >= 0){
                    double l = (l1 + l2 + l3)/3;
                    img.setRGB(x, y, new Color((int) (l*u*255), (int) (l*v*255), (int) (l*(1-u-v)*255)).getRGB());
                }
            }
        }
    }

}





//(x-x1)/(x2-x1)=(y-y1)/(y2-y1)

//(u, v, 1) = ((AB)x, (AC)x, (PA)x)*((AB)y, (AC)y, (PA)y)