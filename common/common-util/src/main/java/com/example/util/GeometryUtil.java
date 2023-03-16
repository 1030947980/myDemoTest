package com.example.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.entity.Point;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * 坐标工具类
 */

public class GeometryUtil {

    private static double EARTH_RADIUS = 6378.137;// 6378.137赤道半径6378137

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 通过经纬度计算两点之间的距离(单位：千米)
     * @param latone
     * @param lngone
     * @param lattwo
     * @param lngtwo
     * @return
     */
    public static double getDistance(double latone, double lngone, double lattwo, double lngtwo) {
        double radlatone = rad(latone);
        double radlattwo = rad(lattwo);
        double a = radlatone - radlattwo;
        double b = rad(lngone) - rad(lngtwo);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radlatone) * Math.cos(radlattwo)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s;
        return s;
    }

    /**
     * 获取坐标点到多边形边缘最小距离  单位：m
     * 多边形按坐标顺序 切首尾相连 A->B->C->A
     * @param point
     * @param points
     * @param pointIndex 返回小数点位数
     * @return
     */
    public static String getPointToEdgeDistance(Point point, List<Point> points,Integer pointIndex) {
        if (points.size()==0){
            return null;
        }
        double dist = Double.MAX_VALUE;
        for (int i=0,j=1;i<points.size()-1&&j<points.size();){
            Point point1 = points.get(i);
            Point point2 = points.get(j);
            double dis = getDistance(point.getX(),point.getY(), point1.getX(), point1.getY());
            double dis2 = getDistance(point.getX(),point.getY(), point2.getX(), point2.getY());

            //获取该点与边的垂足
            Point foot = getFoot(point,point1,point2);
            if (onLineSegment(foot,point1,point2)){//垂足点在线段上,求出最小距离
                dis2 = getDistance(point.getX(),point.getY(), foot.getX(), foot.getY());
            }
            double min = dis<dis2?dis:dis2;
            if (min<dist){
                dist = min;
            }
            i++;j++;
        }
        dist = dist*1000;

        return String.format("%."+pointIndex+"f",dist);
    }


    /**
     * 判断坐标是否在多边形区域内
     * @param pointLon
     * @param pointLat
     * @param rangePositions 多边形坐标 [{"lon":118.19302036660137,"lat":24.494515439791996},{"lon":118.19401849369201,"lat":24.49606682685256}]
     * @return
     */
    public boolean isInPolygon(double pointLon, double pointLat, JSONArray rangePositions) {

        // 将要判断的横纵坐标组成一个点
        double[] lon =  rangePositions.stream().mapToDouble(item->((JSONObject)item).getDouble("lon")).toArray();
        double[] lat =  rangePositions.stream().mapToDouble(item->((JSONObject)item).getDouble("lat")).toArray();;
        Point2D.Double point = new Point2D.Double(pointLon, pointLat);
        // 将区域各顶点的横纵坐标放到一个点集合里面
        List<Point2D.Double> pointList = new ArrayList<Point2D.Double>();
        double polygonPoint_x = 0.0, polygonPoint_y = 0.0;
        for (int i = 0; i < lon.length; i++) {
            polygonPoint_x = lon[i];
            polygonPoint_y = lat[i];
            Point2D.Double polygonPoint = new Point2D.Double(polygonPoint_x, polygonPoint_y);
            pointList.add(polygonPoint);
        }
        return check(point, pointList);
    }

    private static boolean check(Point2D.Double point, List<Point2D.Double> polygon) {
        java.awt.geom.GeneralPath peneralPath = new java.awt.geom.GeneralPath();

        Point2D.Double first = polygon.get(0);
        // 通过移动到指定坐标（以双精度指定）,将一个点添加到路径中
        peneralPath.moveTo(first.x, first.y);
        polygon.remove(0);
        for (Point2D.Double d : polygon) {
            // 通过绘制一条从当前坐标到新指定坐标（以双精度指定）的直线,将一个点添加到路径中。
            peneralPath.lineTo(d.x, d.y);
        }
        // 将几何多边形封闭
        peneralPath.lineTo(first.x, first.y);
        peneralPath.closePath();
        // 测试指定的 Point2D 是否在 Shape 的边界内。
        return peneralPath.contains(point);
    }

    /**
     * 是否在线段AB上
     */
    public static boolean onLineSegment(Point p,Point p1,Point p2){
        double maxx,minx,maxy,miny;
        maxx = p1.x >p2.x ?p1.x :p2.x ;    //矩形的右边长
        minx = p1.x >p2.x ?p2.x :p1.x ;     //矩形的左边长
        maxy = p1.y >p2.y ?p1.y :p2.y ;    //矩形的上边长
        miny = p1.y >p2.y ?p2.y :p1.y ;     //矩形的下边长

        if( ((p.x -p1.x )*(p2.y -p1.y) == (p2.x -p1.x) *(p.y -p1.y)) && ( p.x >= minx && p.x <= maxx ) && ( p.y >= miny && p.y <= maxy)){
            return true;
        }else {
            return false;
        }
    }

    /**
     *获取P点到直线AB的垂足坐标
     */
    public static Point getFoot(Point p,Point p1,Point p2){
        Point foot=new Point();

        double dx=p1.x-p2.x;
        double dy=p1.y-p2.y;

        double u=(p.x-p1.x)*dx+(p.y-p1.y)*dy;
        u/=dx*dx+dy*dy;

        foot.x=p1.x+u*dx;
        foot.y=p1.y+u*dy;

        return foot;
    }

}