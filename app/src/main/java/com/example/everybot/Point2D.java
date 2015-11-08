package com.example.everybot;

/**
 * Created by Raymond on 06/11/2015.
 */
public class Point2D {
    double x;
    double y;
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    @Override
    public String toString() {
        return "Point2D [x=" + x + ", y=" + y + "]";
    }
}
