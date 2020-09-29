package ru.lnmo.render;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main extends JFrame {

    static final int w = 1366;
    static final int h = 728;

    static final int X = 500;
    static final int Y = 250;

    static double[][] vertex = new double[100000][3];
    static double[][] normals = new double[100000][3];
    static double[][] texture_coordinates = new double[100000][2];
    static int[][][] triangles = new int[100000][3][3];

    static final int k = 64;

    public static void draw(Graphics2D g) throws FileNotFoundException {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        /*int X = 1000;
        int Y = 500;
        double x = 0;
        double y = 500;
        double alpha = 2*Math.PI/k;
        for (int i = 0; i < k; i++) {
            Render.renderLine(img, X, Y, (int) (X + x), (int) (Y + y), Color.BLACK);
            double x_ = x*Math.cos(alpha) + y*Math.sin(alpha);
            double y_ = -x*Math.sin(alpha) + y*Math.cos(alpha);
            x = x_;
            y = y_;
            g.drawImage(img, 0,0, null);
        }*/
        //Render.renderTriangle(img, 100, 100, 479, 524, 275, 542, Color.BLACK);
        readOBJ(g, img);
        g.drawImage(img, 0, 0, null);
    }

    static void BuildArrays() throws FileNotFoundException {
        String path = "/home/student/IdeaProjects/3dGraphics_/obj"; //путь к файлу
        Scanner s = new Scanner(new File(path));
        int vertex_index = 0;
        int normals_index = 0;
        int texture_coordinates_index = 0;
        int triangles_index = 0;
        while (s.hasNextLine()){
            String Line = s.nextLine();
            String[] arr = Line.split(" ");
            if (arr.length == 0) continue;
            String type = arr[0];
            if (type.equals("v")){
                vertex[vertex_index][0] = Double.parseDouble(arr[1]);
                vertex[vertex_index][1] = Double.parseDouble(arr[2]);
                vertex[vertex_index][2] = Double.parseDouble(arr[3]);
                vertex_index++;
            }
            else if (type.equals("vt")){
                texture_coordinates[texture_coordinates_index][0] = Double.parseDouble(arr[1]);
                texture_coordinates[texture_coordinates_index][1] = Double.parseDouble(arr[2]);
                texture_coordinates_index++;
            }
            else if (type.equals("vn")){
                normals[normals_index][0] = Double.parseDouble(arr[1]);
                normals[normals_index][1] = Double.parseDouble(arr[2]);
                normals[normals_index][2] = Double.parseDouble(arr[3]);
                normals_index++;
            }
            else if (type.equals("f")){
                String[] vertex1_indices = arr[1].split("/");
                String[] vertex2_indices = arr[2].split("/");
                String[] vertex3_indices = arr[3].split("/");
                triangles[triangles_index][0][0] = Integer.parseInt(vertex1_indices[0]);
                triangles[triangles_index][0][1] = Integer.parseInt(vertex1_indices[1]);
                triangles[triangles_index][0][2] = Integer.parseInt(vertex1_indices[2]);
                triangles[triangles_index][1][0] = Integer.parseInt(vertex2_indices[0]);
                triangles[triangles_index][1][1] = Integer.parseInt(vertex2_indices[1]);
                triangles[triangles_index][1][2] = Integer.parseInt(vertex2_indices[2]);
                triangles[triangles_index][2][0] = Integer.parseInt(vertex3_indices[0]);
                triangles[triangles_index][2][1] = Integer.parseInt(vertex3_indices[1]);
                triangles[triangles_index][2][2] = Integer.parseInt(vertex3_indices[2]);
                triangles_index++;
            }
        }
        vertex = Arrays.copyOf(vertex, vertex_index);
        normals = Arrays.copyOf(normals, normals_index);
        texture_coordinates = Arrays.copyOf(texture_coordinates, texture_coordinates_index);
        triangles = Arrays.copyOf(triangles, triangles_index);
    }


    static void readOBJ(Graphics2D g, BufferedImage img) throws FileNotFoundException {
        BuildArrays();
        DrawOBJ(g);
    }


    static void DrawOBJ(Graphics2D g){
        for (int i = 0; i < triangles.length; i++) {
            g.drawLine((int) vertex[triangles[i][0][0] - 1][0] + X, (int) vertex[triangles[i][0][0] - 1][1] + Y, (int) vertex[triangles[i][1][0] - 1][0] + X, (int) vertex[triangles[i][1][0] - 1][1] + Y);
            g.drawLine((int) vertex[triangles[i][1][0] - 1][0] + X, (int) vertex[triangles[i][1][0] - 1][1] + Y, (int) vertex[triangles[i][2][0] - 1][0] + X, (int) vertex[triangles[i][2][0] - 1][1] + Y);
            g.drawLine((int) vertex[triangles[i][2][0] - 1][0] + X, (int) vertex[triangles[i][2][0] - 1][1] + Y, (int) vertex[triangles[i][0][0] - 1][0] + X, (int) vertex[triangles[i][0][0] - 1][1] + Y);
        }
    }


    //магический код позволяющий всему работать, лучше не трогать
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        Main jf = new Main();
        jf.setSize(w, h);//размер экрана
        jf.setUndecorated(false);//показать заголовок окна
        jf.setTitle("");
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.createBufferStrategy(2);
        //в бесконечном цикле рисуем новый кадр
        while (true) {
            long frameLength = 1000 / 60; //пытаемся работать из рассчета  60 кадров в секунду
            long start = System.currentTimeMillis();
            BufferStrategy bs = jf.getBufferStrategy();
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            g.clearRect(0, 0, jf.getWidth(), jf.getHeight());
            draw(g);

            bs.show();
            g.dispose();

            long end = System.currentTimeMillis();
            long len = end - start;
            if (len < frameLength) {
                Thread.sleep(frameLength - len);
            }
        }

    }

    public void keyTyped(KeyEvent e) {
    }

    //Вызывается когда клавиша отпущена пользователем, обработка события аналогична keyPressed
    public void keyReleased(KeyEvent e) {


    }
}