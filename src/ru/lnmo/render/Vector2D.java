package ru.lnmo.render;

public class Vector2D {
    double x;
    double y;


    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    Vector2D sum(Vector2D v){
        return new Vector2D(x + v.x, y + v.y);
    }

    Vector2D multSc(int Sc){
        return new Vector2D(x*Sc, y*Sc);
    }

}
