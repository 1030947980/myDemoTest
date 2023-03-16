package com.example.util;

import jxl.write.*;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Bing on 2022/2/25.
 */
public class FileUtil {

    public static boolean fileToZip(String sourcePath, String zipPath, String zipName) throws Exception {
        File sourceFile = new File(sourcePath);
        File zipFile = new File(zipPath + File.separator + zipName + ".zip");
        File[] files = sourceFile.listFiles();
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));

        if (files != null) {
            byte[] bufs = new byte[1024 * 10];
            for (int i = 0; i < files.length; i++) {
                // 创建ZIP实体,并添加进压缩包
                ZipEntry zipEntry = new ZipEntry(files[i].getName());
                zos.putNextEntry(zipEntry);
                // 读取待压缩的文件并写进压缩包里
                FileInputStream fis = new FileInputStream(files[i]);
                BufferedInputStream bis = new BufferedInputStream(fis, 1024 * 10);
                int read = 0;
                while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                    zos.write(bufs, 0, read);
                }
                bis.close();
                fis.close();
            }
            deleteFolder(sourceFile);
        }

        zos.close();
        fos.close();
        return true;
    }

    public static void deleteFolder(File folder){
        File[] files  = folder.listFiles();
        if (files != null){
            for(File f: files){
                if (f.isDirectory()){
                    deleteFolder(f);
                }else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    /**
     * 下载多媒体文件（请注意，视频文件不支持下载，调用该接口需http协议）
     *
     * @return
     */
    public static InputStream getInputStream(String url) {
        InputStream is = null;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            // 获取文件转化为byte流
            is = http.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }

    /**
     *下载多媒体文件
     * @param urlString 需要下载的文件完整地址
     * @param filename  下载的命名
     * @throws Exception
     */
    public static void download(String urlString,String savePath, String filename) throws Exception {
        try {
            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            //设置请求超时为5s
            con.setConnectTimeout(5*1000);
            // 输入流
            InputStream is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            File sf= new File(savePath);
            if(!sf.exists()){
                sf.mkdirs();
            }
            OutputStream os = new FileOutputStream(sf.getPath()+File.separator+filename);

            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(filename);
        }

    }


    /**
     * 列表数据转为Excel文件
     * @param wwb
     * @param listMap 数据
     * @param columnMap 每列对应的数据集中的key
     * @param header  表头
     * @throws Exception
     */
    public void write(WritableWorkbook wwb, List<Map<String,Object>> listMap, Map<Integer,String> columnMap, String [] header) throws Exception {
        try {
            WritableSheet ws;
            ws = wwb.createSheet("sheet",1);
            addHeader(ws,header);
            int i = 1;
            for (Map<String,Object> map : listMap ) {
                for(int key :columnMap.keySet()){
                    String cloumn = columnMap.get(key);
                    if(map.get(cloumn) != null){
                        addCell(ws, i, key, map.get(cloumn).toString());
                    }
                }
                i++;
            }
            wwb.write();
            wwb.close();
        } catch (IOException e) {
            e.printStackTrace();
            if (wwb != null) {
                wwb.close();
            }
            throw e;
        }
    }

    public void addHeader(WritableSheet ws, String[] header) throws WriteException {
        int i = 0;
        for (String h : header) {
            addCell(ws, 0, i, h);//表名，行，列，header
            i++;
        }
    }

    //添加单元格内容
    public void addCell(WritableSheet ws, int row, int column,  String data) throws WriteException {
        Label label = new Label(column ,row, data);
        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
        cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        label.setCellFormat(cellFormat);
        ws.addCell(label);
    }


}
