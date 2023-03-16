package com.example.util;

import com.alibaba.fastjson.JSONObject;
import com.example.vo.SlideVerificationVO;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseGifCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Bing on 2022/8/28.
 * 普通图形验证码
 */
public class CaptchaUtil {
    //算术类型验证码 ArithmeticCaptcha   算术验证码len表示几位数运算text()表示的是公式的结果，其他类型验证码表示位数
    //中文类型验证吗 ChineseCaptcha
    //英文与数字验证码 SpecCaptcha
    //中文动态验证码 ChineseGifCaptcha
    //英文与数字动态验证码 GifCaptcha


    public static JSONObject getCaptchaImg(String key){
        JSONObject result = new JSONObject();

        SpecCaptcha captcha = new SpecCaptcha();
        captcha.setFont(new Font("宋体,楷体,微软雅黑", Font.PLAIN, 28));
        captcha.setLen(6);//算术验证码len表示几位数运算text()表示的是公式的结果，其他类型验证码表示位数
        String verifyCode = captcha.text();
        String base64Img = captcha.toBase64();//格式化处理

        Map<String, String> data = new HashMap<>();
        data.put("key", key);
        data.put("image", base64Img);
        data.put("format", "png");

        result.put("verifyCode",verifyCode);
        result.put("verifyCodeObj",data);
        return result;
    }

    public static JSONObject getCaptchaGif(String key){
        JSONObject result = new JSONObject();

        GifCaptcha captcha = new GifCaptcha();
        captcha.setFont(new Font("宋体,楷体,微软雅黑", Font.PLAIN, 28));
        captcha.setLen(6);//算术验证码len表示几位数运算text()表示的是公式的结果，其他类型验证码表示位数
        String verifyCode = captcha.text();
        String base64Img = captcha.toBase64();//格式化处理

        Map<String, String> data = new HashMap<>();
        data.put("key", key);
        data.put("image", base64Img);
        data.put("format", "gif");

        result.put("verifyCode",verifyCode);
        result.put("verifyCodeObj",data);
        return result;
    }

    public static JSONObject getCaptchaChineseGif(String key){
        JSONObject result = new JSONObject();

        ChineseGifCaptcha captcha = new ChineseGifCaptcha();
        captcha.setFont(new Font("宋体,楷体,微软雅黑", Font.PLAIN, 28));
        captcha.setLen(6);//算术验证码len表示几位数运算text()表示的是公式的结果，其他类型验证码表示位数
        String verifyCode = captcha.text();
        String base64Img = captcha.toBase64();//格式化处理

        Map<String, String> data = new HashMap<>();
        data.put("key", key);
        data.put("image", base64Img);
        data.put("format", "gif");

        result.put("verifyCode",verifyCode);
        result.put("verifyCodeObj",data);
        return result;
    }

    public static JSONObject getCaptchaArithmetic(String key){
        JSONObject result = new JSONObject();

        ArithmeticCaptcha captcha = new ArithmeticCaptcha();
        captcha.setFont(new Font("宋体,楷体,微软雅黑", Font.PLAIN, 28));
        captcha.setLen(3);//算术验证码len表示几位数运算text()表示的是公式的结果，其他类型验证码表示位数
        String verifyCode = captcha.text();
        String base64Img = captcha.toBase64();//格式化处理

        Map<String, String> data = new HashMap<>();
        data.put("key", key);
        data.put("image", base64Img);
        data.put("format", "gif");

        result.put("verifyCode",verifyCode);
        result.put("verifyCodeObj",data);
        return result;
    }

    public static void main(String[] args) {
//        String verifyCode= "";
//        JSONObject captObj = null;
//        captObj = getCaptchaImg("img");
//        verifyCode = captObj.getString("verifyCode");
//        System.out.println("img："+verifyCode+" \n"+ captObj.getJSONObject("verifyCodeObj").getString("image"));
//
//        captObj = getCaptchaGif("gif");
//        verifyCode = captObj.getString("verifyCode");
//        System.out.println("gif："+verifyCode+" \n"+ captObj.getJSONObject("verifyCodeObj").getString("image"));
//
//        captObj = getCaptchaChineseGif("gif");
//        verifyCode = captObj.getString("verifyCode");
//        System.out.println("gif："+verifyCode+" \n"+ captObj.getJSONObject("verifyCodeObj").getString("image"));
//
//        captObj = getCaptchaArithmetic("ari");
//        verifyCode = captObj.getString("verifyCode");
//        System.out.println("ari："+verifyCode+" \n"+ captObj.getJSONObject("verifyCodeObj").getString("image"));

        //滑动验证码
        ImageSlideVerification imageSlideVerification = new ImageSlideVerification();
        SlideVerificationVO tmp =  imageSlideVerification.getSlideCode("testtt");
        System.out.println(1);
    }

}
