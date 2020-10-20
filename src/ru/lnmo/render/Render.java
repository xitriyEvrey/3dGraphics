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
    public static void renderLine(BufferedImage img, double x1, double y1, double x2, double y2, Color color) {
        if ((int) x1 == (int) x2 && (int) y1 == (int) y2){
            img.setRGB((int) x1, (int) y2, color.getRGB());
            return;
        }
        double dx = Math.abs(x2 - x1);
        double dy = Math.abs(y2 - y1);
        for (int x = (int) Math.min(x1, x2); x <= (int) Math.max(x1, x2); x++) {
            for (int y = (int) Math.min(y1, y2); y <= (int) Math.max(y1, y2); y++) {
                if (dx > dy) {
                    img.setRGB(x, (int) (((x - x1) * (y2 - y1) / (x2 - x1)) + y1), color.getRGB());
                } else {
                    img.setRGB((int) (((y - y1) * (x2 - x1) / (y2 - y1)) + x1), y, color.getRGB());
                }
            }
        }
    }

    static Vector OBJrotate(double alpha, double beta, double gamma, Vector toRotate){
        Matrix Mx = new Matrix(new double[][]
                {{1,               0,                0},
                        {0, Math.cos(alpha), -Math.sin(alpha)},
                        {0, Math.sin(alpha), Math.cos(alpha)}});
        Matrix My = new Matrix(new double[][]
                {{Math.cos(beta),  0, Math.sin(beta)},
                        {0,               1,              0},
                        {-Math.sin(beta), 0, Math.cos(beta)}});
        Matrix Mz = new Matrix(new double[][]
                {{Math.cos(gamma), -Math.sin(gamma), 0},
                        {Math.sin(gamma), Math.cos(gamma),  0},
                        {0,               0,                 1}});
        Matrix M = Mx.mult(My).mult(Mz);
        Matrix M_toRotate = toRotate.toMatrix();
        Matrix M_toRotate_rotated = M.mult(M_toRotate);
        return new Vector(new double[]{M_toRotate_rotated.get(0, 0), M_toRotate_rotated.get(1, 0), M_toRotate_rotated.get(2, 0)});
    }




//    public static void renderTriangle(BufferedImage img, int x1, int y1, int x2, int y2, int x3, int y3, Color color){
//       Vector AB = new Vector(new double[]{x2, y2}).sum(new Vector(new double[]{x1, y1}).scMult(-1));
//       Vector AC = new Vector(new double[]{x3, y3}).sum(new Vector(new double[]{x1, y1}).scMult(-1));
//        for (int x = Math.min(x1, Math.min(x2, x3)); x <= Math.max(x1, Math.max(x2, x3)); x++) {
//            for (int y = Math.min(y1, Math.min(y2, y3)); y < Math.max(y1, Math.max(y2, y3)); y++) {
//                Vector PA = new Vector(new double[]{x1, y1}).sum(new Vector(new double[]{x, y}).scMult(-1));
//                Vector V = new Vector(new double[]{AB.get(0), AC.get(0), PA.get(0)}).CrossProd(new Vector(new double[]{AB.get(1), AC.get(1), PA.get(1)}));
//                double u = (V.get(0)/V.get(2));
//                double v = (V.get(1)/V.get(2));
//                if (u + v <= 1 && u >= 0 && v >= 0){
//                    img.setRGB(x, y, new Color((int) (u*255), (int) (v*255), (int) ((1-u-v)*255)).getRGB());
//                }
//            }
//        }
//    }

    public static void renderOBJTriangle(BufferedImage img, double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, Vector normal1, Vector normal2, Vector normal3, Vector sight, int moveX, int moveY, double[][] zBuffer, Vector light, double alpha, double beta, double gamma, BufferedImage texture, double tx1, double ty1, double tx2, double ty2, double tx3, double ty3){
        Vector v1 = new Vector(new double[]{x1, y1, z1});
        Vector v2 = new Vector(new double[]{x2, y2, z2});
        Vector v3 = new Vector(new double[]{x3, y3, z3});
        v1 = OBJrotate(alpha, beta, gamma, v1);
        v2 = OBJrotate(alpha, beta, gamma, v2);
        v3 = OBJrotate(alpha, beta, gamma, v3);
        x1 = v1.get(0);
        y1 = v1.get(1);
        z1 = v1.get(2);
        x2 = v2.get(0);
        y2 = v2.get(1);
        z2 = v2.get(2);
        x3 = v3.get(0);
        y3 = v3.get(1);
        z3 = v3.get(2);
        normal1 = OBJrotate(alpha, beta, gamma, normal1);
        normal2 = OBJrotate(alpha, beta, gamma, normal2);
        normal3 = OBJrotate(alpha, beta, gamma, normal3);
        double l1 = normal1.scProd(sight);
        double l2 = normal2.scProd(sight);
        double l3 = normal3.scProd(sight);
        Vector AB = new Vector(new double[]{x2, y2, z2}).sum(new Vector(new double[]{x1, y1, z1}).scMult(-1));
        Vector AC = new Vector(new double[]{x3, y3, z3}).sum(new Vector(new double[]{x1, y1, z1}).scMult(-1));
        Vector normal = AB.CrossProd(AC).normalize();
        if (normal.scProd(sight) < 0) return;
//        double color = normal.scProd(sight);
        x1 += moveX;
        y1 += moveY;
        x2 += moveX;
        y2 += moveY;
        x3 += moveX;
        y3 += moveY;
        for (int x = (int) Math.min(x1, Math.min(x2, x3)); x <= Math.max(x1, Math.max(x2, x3)); x++) {
            for (int y = (int) Math.min(y1, Math.min(y2, y3)); y <= Math.max(y1, Math.max(y2, y3)); y++) {
                Vector PA = new Vector(new double[]{x1, y1}).sum(new Vector(new double[]{x, y}).scMult(-1));
                Vector V = new Vector(new double[]{AB.get(0), AC.get(0), PA.get(0)}).CrossProd(new Vector(new double[]{AB.get(1), AC.get(1), PA.get(1)}));
                double u = (V.get(0)/V.get(2));
                double v = (V.get(1)/V.get(2));
                if (u + v <= 1 && u >= 0 && v >= 0){
                    double l = (l1*(1-u-v) + l2*u + l3*v);
                    l = Math.max(0, l);
                    double z = (z1*(1-u-v) + z2*u + z3*v);
                    double tx = (tx1*(1-u-v) + tx2*u + tx3*v)*texture.getWidth();
                    double ty = (ty1*(1-u-v) + ty2*u + ty3*v)*texture.getHeight();
                    if (z < zBuffer[x][y]/* && color > 0*/) {
//                        img.setRGB(x, y, new Color((int) (color*255), (int) (color*255), (int) (color*255)).getRGB());
                        img.setRGB(x, y, texture.getRGB((int) tx, (int) ty));
//                        img.setRGB(x, y, new Color((int) (l*255), (int) (l*255), (int) (l*255)).getRGB());
                        zBuffer[x][y] = z;
                    }
                }
            }
        }
//        renderLine(img, x1, y1, x2, y2, Color.BLACK);
//        renderLine(img, x2, y2, x3, y3, Color.BLACK);
//        renderLine(img, x3, y3, x1, y1, Color.BLACK);
//        System.out.println("(" + x1 + ", " + y1 + "), " + "(" + x2 + ", " + y2 + "), " + "(" + x3 + ", " + y3 + ")");
    }

}