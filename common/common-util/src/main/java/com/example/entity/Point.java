package com.example.entity;

public class Point {

    /**
     * x轴坐标
     */
   public double x;
    /**
     * y轴坐标
     */
    public double y;

    public Point setPoint(Point point){
        this.x = point.getX();
        this.y = point.getY();
        return this;
    }

    public Point() {
    }

    public Point(Float[] point){
        this.x = point[0];
        this.y = point[1];
    }

    public Point(Double[] point) {
        this.x = point[0];
        this.y = point[1];
    }

    public Point(double x, double y) {
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


}