package ru.lnmo.render;

public class Vector {
    double[] V;
    int dimension;

    public Vector(double[] v) {
        this.V = v;
        dimension = v.length;
    }

    double get(int index){
        return V[index];
    }

    void set(int index, double value){
        V[index] = value;
    }

    Vector sum(Vector V){
        if (dimension != V.dimension) return null;
        Vector res = new Vector(new double[dimension]);
        for (int i = 0; i < dimension; i++) {
            res.set(i, get(i) + V.get(i));
        }
        return res;
    }

    Vector scMult(double sc){
        Vector res = new Vector(new double[dimension]);
        for (int i = 0; i < dimension; i++) {
            res.set(i, get(i)*sc);
        }
        return res;
    }

    double scProd(Vector V){
        if (dimension != V.dimension) return 0;
        double res = 0;
        for (int i = 0; i < dimension; i++) {
            res += get(i) * V.get(i);
        }
        return res;
    }

    Vector CrossProd(Vector V){
        if (dimension == V.dimension && dimension == 3){
            return new Vector(new double[]{get(1) * V.get(2) - get(2) * V.get(1), get(2) * V.get(0) - get(0) * V.get(2), get(0) * V.get(1) - get(1) * V.get(0)});
        }
        return null;
    }

    Matrix toMatrix(){
        Matrix res = new Matrix(new double[dimension][1]);
        for (int i = 0; i < dimension; i++) {
            res.set(i, 0, get(i));
        }
        return res;
    }
}
