package com.example.util;

import com.alibaba.fastjson.JSONObject;

/**
 * * 坐标转换工具类
 *  * WGS84: Google Earth采用，Google Map中国范围外使用
 *  * GCJ02: 火星坐标系，中国国家测绘局制定的坐标系统，由WGS84机密后的坐标。Google Map中国和搜搜地图使用，高德
 *  * BD09:百度坐标，GCJ02机密后的坐标系
 *
 * Created by Bing on 2021/5/8.
 */

public class GpsUtil {
    public static final String BAIDU_LBS_TYPE = "bd09ll";
    public static double pi = Math.PI;
    public static double a = 6378245.0;
    public static double ee = 0.00669342162296594323;


    /**
     * 84 to 火星坐标系 (GCJ-02) World Geodetic System ==> Mars Geodetic System
     * @param lat
     * @param lon
     */
    public  JSONObject gps84_To_Gcj02(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return null;
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return getPosition(mgLat, mgLon);
    }

    /**
     * * 火星坐标系 (GCJ-02) to 84 * * @param lon * @param lat * @return
     */
    public  JSONObject gcj_To_Gps84(double lat, double lon) {
        JSONObject gps = transform(lat, lon);
        double lontitude = lon * 2 - gps.getDouble("lon");
        double latitude = lat * 2 - gps.getDouble("lat");
        return getPosition(latitude, lontitude);
    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
     *
     * @param gg_lat
     * @param gg_lon
     */
    public  JSONObject gcj02_To_Bd09(double gg_lat, double gg_lon) {
        double x = gg_lon, y = gg_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return getPosition(bd_lat, bd_lon);
    }

    public  String gcj02_To_Bd09_Str(double gg_lat, double gg_lon) {
        double x = gg_lon, y = gg_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return bd_lon+","+bd_lat;
    }

    /**
     * * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 * * 将 BD-09 坐标转换成GCJ-02 坐标 * * @param
     * bd_lat * @param bd_lon * @return
     */
    public  JSONObject bd09_To_Gcj02(double bd_lat, double bd_lon) {
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return getPosition(gg_lat, gg_lon);
    }

    public  String  bd09_To_Gcj02_str(double bd_lat, double bd_lon) {
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return gg_lon+","+gg_lat;
    }

    /**
     * (BD-09)-->84
     * @param bd_lat
     * @param bd_lon
     * @return
     */
    public JSONObject bd09_To_Gps84(double bd_lat, double bd_lon) {

        JSONObject gcj02 = bd09_To_Gcj02(bd_lat, bd_lon);
        JSONObject map84 = gcj_To_Gps84(gcj02.getDouble("lat"),
                gcj02.getDouble("lon"));
        return map84;
    }

    public JSONObject Gps84_To_bd09(double bd_lat, double bd_lon) {

        JSONObject gcj02 = gps84_To_Gcj02(bd_lat, bd_lon);
        JSONObject map84 = gcj02_To_Bd09(gcj02.getDouble("lat"),
                gcj02.getDouble("lon"));
        return map84;
    }

//    /**
//     * (BD-09)-->84
//     * @param bd_lat
//     * @param bd_lon
//     * @return
//     */
//    public  JSONObject bd09_To_Gps84(double bd_lat, double bd_lon) {
//
//        JSONObject gcj02 = bd09_To_Gcj02(bd_lat, bd_lon);
//        JSONObject map84 = gcj_To_Gps84(gcj02.getDouble("lat"),
//                gcj02.getDouble("lon"));
//        return map84;
//
//    }

    /**
     * is or not outOfChina
     * @param lat
     * @param lon
     * @return
     */
    public  boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }

    public  JSONObject transform(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return getPosition(lat, lon);
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return getPosition(mgLat, mgLon);
    }

    public  double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
                + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }


    public  double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
                * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0
                * pi)) * 2.0 / 3.0;
        return ret;
    }

    public JSONObject getPosition(double lat, double lon){
        JSONObject result = new JSONObject();
        result.put("lat",lat);
        result.put("lon",lon);
        return result;
    }

//    /**
//     * 百度转火星
//     * @param bd_lat
//     * @param bd_lon
//     */
//    public static String  bd_decrypt(double bd_lat, double bd_lon)
//    {
//        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
//        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
//        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
//        double gg_lon = z * Math.cos(theta);
//        double gg_lat = z * Math.sin(theta);
//        return gg_lat+","+gg_lon;
//    }
//
//    public static String bd_encrypt(double gg_lat, double gg_lon) {
//        double x = gg_lon, y = gg_lat;
//        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
//        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
//        double bd_lon = z * Math.cos(theta) + 0.0065;
//        double bd_lat = z * Math.sin(theta) + 0.006;
//        return bd_lat + "," + bd_lon;
//    }

}
