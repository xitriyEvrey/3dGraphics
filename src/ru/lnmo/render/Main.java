package ru.lnmo.render;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Main extends JFrame {

    static final int w = 1366;
    static final int h = 728;

    static final int k = 64;

    public static void draw(Graphics2D g) {
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
        Render.renderTriangle(img, 100, 100, 100, 200, 200, 100, Color.BLACK);
        g.drawImage(img, 0, 0, null);
    }



    //магический код позволяющий всему работать, лучше не трогать
    public static void main(String[] args) throws InterruptedException {
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



