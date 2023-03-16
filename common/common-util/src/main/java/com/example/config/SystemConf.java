package com.example.config;


import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class SystemConf {

    // 别处登录
    public static final int LOGIN_OTHER = 999;
    // 登录超时
    public static final int LOGIN_TIMEOUT = 998;
    // 未登录
    public static final int NOT_LOGIN = 997;
    //未认证
    public static final int NOT_CERTIFIED = 996;

    // 文件保存临时路径
    private static final String TEMP_PATH = "upload_temp_path";

    // 血糖餐前最小值
    public static final double HEALTH_STANDARD_ST_MIN_BEFORE = 4;
    // 血糖餐前最大值
    public static final double HEALTH_STANDARD_ST_MAX_BEFORE = 7;
    // 血糖餐后最小值
    public static final double HEALTH_STANDARD_ST_MIN_AFTER = 4;
    // 血糖餐后最大值
    public static final double HEALTH_STANDARD_ST_MAX_AFTER = 11.1;

    // 舒张压最小值
    public static final double HEALTH_STANDARD_SZY_MIN = 60;
    // 舒张压最大值
    public static final double HEALTH_STANDARD_SZY_MAX = 90;
    // 收缩压最小值
    public static final double HEALTH_STANDARD_SSY_MIN = 90;
    // 收缩压最大值
    public static final double HEALTH_STANDARD_SSY_MAX = 140;

    // 同一手机号大最短信数
    public static final int MAX_SMS_MOBILE = 5;
    // 发送短信验证码间隔(分钟)
    public static final int SMS_INTERVAL = 2;

    private static Object lock = new Object();

    // 全局系统配置信息
    private static SystemConf systemConf;
    // 系统配置文件
    private Properties systemProperties;
    //im列表
    private String imListGet;

    public static SystemConf getInstance() {
        if (systemConf == null) {
            synchronized (lock) {
                systemConf = new SystemConf();
            }
        }
        return systemConf;
    }

    /**
     * 加载系统配置文件
     *
     * @return
     */
    public Properties getSystemProperties() {
        if (systemProperties == null) {
            InputStream is = null;
            try {
                is = this.getClass().getResourceAsStream("/system.properties");
                systemProperties = new Properties();
                systemProperties.load(is);
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return systemProperties;
    }
    


}
