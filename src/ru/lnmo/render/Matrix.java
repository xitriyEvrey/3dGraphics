package ru.lnmo.render;

public class Matrix {
    double[][] M;
    int m;
    int n;

    public Matrix(double[][] M) {
        this.M = M;
        m = M.length;
        n = M[0].length;
    }


    double get(int index1, int index2){
        return M[index1][index2];
    }

    void set(int index1, int index2, double value){
        M[index1][index2] = value;
    }

    Matrix mult(Matrix M){
        if (n != M.m) return null;
        Matrix res = new Matrix(new double[m][M.n]);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < M.n; j++) {
                for (int k = 0; k < n; k++) {
                    double oldValue = res.get(i, j);
                    res.set(i, j, oldValue + get(i, k)*M.get(k, j));
                }
            }
        }
        return res;
    }
}
