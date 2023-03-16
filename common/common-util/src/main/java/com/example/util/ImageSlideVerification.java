package com.example.util;

import com.alibaba.fastjson.JSONObject;
import com.example.vo.SlideVerificationVO;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.*;


public class ImageSlideVerification {

    /**
     * 源图目录路径
     */
    private static String ORIGIN_CATALOG = "captcha";

    /**
     * 模板图目录路径
     */
    private static String TEMPLATE_CATALOG = "captcha";

    /**
     * 描边图目录路径
     */
    private static String BORDER_CATALOG = "captcha";

    /**
     * Image-key
     */
    private static String IMAGE_KEY = "IMAGE:KEY:";


    /**
     * 获取滑动验证码
     * @return
     */
    public SlideVerificationVO getSlideCode(String imageToken) {
        //  原图
        File originalFile = getOriginalFile();
        //  滑块模板图
        File templateFile = getTemplateFile();
        //  滑块描边图片
        File borderFile = getBorderFile();

        // 生成随机x、y
        int x = generateX(originalFile, templateFile);
        int y = generateY(originalFile, templateFile);

        // 生成滑块
        String slide = cutSlideImage(originalFile, templateFile, borderFile, x, y);
        // 生成缺块图
        String back = cutBackImage(originalFile, templateFile, borderFile, x, y);
        // 生成可以
        if(StringUtils.isBlank(imageToken)){
            imageToken = UUID.randomUUID().toString().replace("-", "");
        }

        // 放入redis
        Map<String, Integer> cacheMap = new HashMap<>();
        cacheMap.put("x", x);
        cacheMap.put("y", y);
//        redisTemplate.opsForValue().set(IMAGE_KEY + imageToken, JSONObject.toJSONString(cacheMap), 5, TimeUnit.MINUTES);

        System.out.println(JSONObject.toJSONString(cacheMap));

        SlideVerificationVO vo = new SlideVerificationVO();
        vo.setImageToken(imageToken);
        vo.setBackImage(back);
        vo.setSlideImage(slide);
        vo.setY(y + "");

        return vo;
    }

    /**
     * 验证滑动验证码
     * @param imageToken
     * @param imageCode 数据结构 [{x:0,y:0},{x:10,y:10},{x:20,y:20}]
     * @return
     */
    public boolean checkSlideCode(String imageToken, String imageCode,boolean flag) {
        // redis 取值
        String key = IMAGE_KEY + imageToken;
//        String json = redisTemplate.opsForValue().get(key);
        String json = "";
        if (StringUtils.isEmpty(json)) {
            return false;
        }

        // 获取缓存中的x、y
        Map<String, Integer> cacheMap = JSONObject.parseObject(json, Map.class);
        int x = cacheMap.get("x");
        int y = cacheMap.get("y");

        // 获取参数中最后一个元素
        List<Map> imageCodeList = JSONObject.parseArray(imageCode, Map.class);
        Map codeMap = imageCodeList.get(imageCodeList.size() - 1);
        if (codeMap.get("x") == null || codeMap.get("y") == null) {
            return false;
        }
        BigDecimal codeX = new BigDecimal(codeMap.get("x").toString());
        BigDecimal codeY = new BigDecimal(codeMap.get("y").toString());

        // 验证 x
        System.out.println("x="+x+",codeX="+codeX);
        int errorX = codeX.intValue() - x;
        if (errorX > 5 || errorX < -5) {
            return false;
        }

        // 验证 y
        int errorY = codeY.intValue() - y;
        if (errorY > 1 || errorY < -1) {
            return false;
        }

        // *****验证路径*****
        // *****验证路径*****

        //删除redis
        if(flag){
//            redisTemplate.delete(key);
        }
        return true;
    }

    public static void main(String[] args) {
        String imageCode = "[{\"x\":68.58750915527344,\"y\":\"79\"}]";
        // 获取参数中最后一个元素
        List<Map> imageCodeList = JSONObject.parseArray(imageCode, Map.class);
        Map codeMap = imageCodeList.get(imageCodeList.size() - 1);
        BigDecimal codeX = new BigDecimal(codeMap.get("x").toString());
        BigDecimal codeY = new BigDecimal(codeMap.get("y").toString());


        System.out.println(codeX.intValue());
        System.out.println(codeY.intValue());

    }

    /**
     * 获取跟目录
     * @return
     */
    private String getClassPath() {
        return this.getClass().getResource("/").getPath();
    }

    /**
     * 获取随机原图图片
     */
    private File getOriginalFile() {
        StringBuilder builder = new StringBuilder(getClassPath());
        builder.append(ORIGIN_CATALOG);
        builder.append("/");
        builder.append(new Random().nextInt(6));
        builder.append(".png");

        return new File(builder.toString());
    }

    /**
     * 滑块模板图片
     */
    private File getTemplateFile() {
        StringBuilder builder = new StringBuilder(getClassPath());
        builder.append(TEMPLATE_CATALOG);
        builder.append("/");
        builder.append("template.png");

        return new File(builder.toString());
    }

    /**
     * 获取描边原图图片
     */
    private File getBorderFile() {
        StringBuilder builder = new StringBuilder(getClassPath());
        builder.append(BORDER_CATALOG);
        builder.append("/");
        builder.append("border.png");

        return new File(builder.toString());
    }

    /**
     * 随机X坐标
     * @param originalFile
     * @param templateFile
     * @return
     */
    private static int generateX(File originalFile , File templateFile) {
        try {
            BufferedImage originalImage = ImageIO.read(originalFile);
            BufferedImage templateImage = ImageIO.read(templateFile);

            //  原图宽度
            int width = originalImage.getWidth();

            //  模板宽度
            int templateImageWidth = templateImage.getWidth();
            Random random = new Random(System.currentTimeMillis());

            return random.nextInt(width - templateImageWidth) % (width - templateImageWidth - templateImageWidth + 1) + templateImageWidth;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 随机Y坐标
     * @param originalFile
     * @param templateFile
     * @return
     */
    private static int generateY(File originalFile , File templateFile) {
        try {
            BufferedImage originalImage = ImageIO.read(originalFile);
            BufferedImage templateImage = ImageIO.read(templateFile);
            //  原图高度
            int height = originalImage.getHeight();

            //  抠图模板高度
            int templateImageHeight = templateImage.getHeight();
            Random random = new Random(System.currentTimeMillis());
            if (templateImageHeight - height >= 0) {
                return random.nextInt(10);
            }

            return random.nextInt(height - templateImageHeight) % (height - templateImageHeight - templateImageHeight + 1) + templateImageHeight;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 裁剪滑块
     * @param originalFile
     * @param templateFile
     * @param borderFile
     * @param x
     * @param y
     * @return
     */
    private static String cutSlideImage(File originalFile , File templateFile, File borderFile, int x, int y) {
        ImageInputStream originImageInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            BufferedImage templateImage = ImageIO.read(templateFile);
            BufferedImage borderImage = ImageIO.read(borderFile);

            int with = templateImage.getWidth();
            int height = templateImage.getHeight();

            //  创建滑块
            BufferedImage cutoutImage = new BufferedImage(with, height, templateImage.getType());

            // 从原图中获取与滑块一样大小的4边形图片
            ImageReader originalReader = ImageIO.getImageReadersByFormatName("png").next();
            originImageInputStream = ImageIO.createImageInputStream(originalFile);
            //  图片输入流顺序读写
            originalReader.setInput(originImageInputStream, true);

            //  根据坐标生成矩形
            ImageReadParam imageReadParam = originalReader.getDefaultReadParam();
            Rectangle rectangle = new Rectangle(x, y, with, height);
            imageReadParam.setSourceRegion(rectangle);

            // 从原图中裁剪下来的图片
            BufferedImage cutImage = originalReader.read(0, imageReadParam);

            // 获取剪切图片矩阵颜色
            int[][] cutColorArray = getColorArray(cutImage);
            // 获取模板图片矩阵颜色
            int[][] templateColorArray = getColorArray(templateImage);
            // 获取边框图片矩阵颜色
            int[][] borderColorArray = getColorArray(borderImage);

            // 将模板图非透明像素设置到剪切图中
            for (int i = 0; i < templateColorArray.length; i++) {
                for (int j = 0; j < templateColorArray[0].length; j++) {
                    // 滑块模板颜色不为透明时设置剪切图颜色
                    int templateRGB = templateColorArray[i][j];
                    if (templateRGB != 16777215 && templateRGB < 0) {
                        cutoutImage.setRGB(i, j, cutColorArray[i][j]);
                    }
                    // 设置边框颜色
                    int borderRGB = borderColorArray[i][j];
                    if (borderRGB != 16777215 && borderRGB < 0) {
                        int[] colorArray = {(borderColorArray[i][j] & 0xff0000) >> 16,
                                (borderColorArray[i][j] & 0xff00) >> 8,
                                (borderColorArray[i][j] & 0xff)};

                        // 我的边框是黑色所以我这是加
                        colorArray[0] += (cutColorArray[i][j] & 0xff0000) >> 16;
                        colorArray[1] += (cutColorArray[i][j] & 0xff00) >> 8;
                        colorArray[2] += (cutColorArray[i][j] & 0xff);

                        cutoutImage.setRGB(i, j , new Color(colorArray[0] > 255 ? 255 : colorArray[0],
                                colorArray[1] > 255 ? 255 : colorArray[1],
                                colorArray[2] > 255 ? 255 : colorArray[2]).getRGB());
                    }
                }
            }

            //  图片绘图
            int bold = 5;
            Graphics2D graphics = cutoutImage.createGraphics();
            graphics.setBackground(Color.white);

            //  设置抗锯齿属性
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setStroke(new BasicStroke(bold, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            graphics.drawImage(cutoutImage, 0, 0, null);
            graphics.dispose();

            // 输出图片流
            byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(cutoutImage, "png", byteArrayOutputStream);
            //  图片转为二进制字符串
            byte[] cutoutImageBytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.reset();
            //  图片加密成base64字符串
            return "data:image/jpeg;base64,"+Base64.getEncoder().encodeToString(cutoutImageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (originImageInputStream != null) {
                    originImageInputStream.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 生成背景图
     * @param originalFile
     * @param templateFile
     * @param borderFile
     * @param x
     * @param y
     * @return
     */
    private static String cutBackImage(File originalFile , File templateFile, File borderFile, int x, int y) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {

            BufferedImage originalImage = ImageIO.read(originalFile);
            BufferedImage templateImage = ImageIO.read(templateFile);

            int with = templateImage.getWidth();
            int height = templateImage.getHeight();

            //  根据原图，创建支持alpha通道的rgb图片
            BufferedImage shadeImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

            // 获取原图片矩阵
            int[][] originColorArray = getColorArray(originalImage);

            //  将原图的像素拷贝到遮罩图
            for (int i = 0; i < originColorArray.length; i++) {
                for (int j = 0; j < originColorArray[0].length; j++) {
                    int originalRGB = originalImage.getRGB(i, j);

                    // 在模板区域内
                    if (i > x && i < (x + with) && j > y && j < (y + height)) {
                        int templateRGB = templateImage.getRGB(i - x, j - y);
                        //对源文件备份图像(x+i,y+j)坐标点进行透明处理
                        if (templateRGB != 16777215 && templateRGB < 0) {

                            int[] rgb = new int[3];
                            rgb[0] = (originalRGB & 0xff0000) >> 16;
                            rgb[1] = (originalRGB & 0xff00) >> 8;
                            rgb[2] = (originalRGB & 0xff);

                            // 对遮罩透明处理
                            shadeImage.setRGB(i, j, new Color(rgb[0]/3,rgb[1]/3,rgb[2]/3).getRGB());
                            continue;
                        }
                    }

                    //  无透明处理
                    shadeImage.setRGB(i, j, originalRGB);
                }
            }

            // 输出图片流
            byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(shadeImage, "png", byteArrayOutputStream);
            //  图片转为二进制字符串
            byte[] cutoutImageBytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.reset();
            //  图片加密成base64字符串
            return "data:image/jpeg;base64,"+Base64.getEncoder().encodeToString(cutoutImageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 图片生成图像矩阵
     * @param bufferedImage  图片源
     * @return 图片矩阵
     */
    private static int[][] getColorArray(BufferedImage bufferedImage) {
        int[][] matrix = new int[bufferedImage.getWidth()][bufferedImage.getHeight()];
        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                matrix[i][j] = bufferedImage.getRGB(i, j);
            }
        }
        return matrix;
    }

}
