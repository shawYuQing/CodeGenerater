package com.xiao.codeGenerater.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by xiaoyq on 2019/2/21.
 */
public class FileUtil {

    /**
     * 创建文件
     * @param path
     * @param pix
     * @param bytes
     */
    public static void createFile(String path, String pix, byte[] bytes) {
        FileOutputStream out = null;
        try {
            File file = new File(path + pix);
            if (!file.exists()){
                file.createNewFile();
            }

            out = new FileOutputStream(file);
            out.write(bytes);
            out.flush();

            LogPrintUtil.print("/* ");
            LogPrintUtil.print(" 写入文件 " + file.getPath() + " 成功。");
            LogPrintUtil.print("*/");
        } catch (Exception e) {
            LogPrintUtil.print("/* ");
            LogPrintUtil.print(" 写入文件 " + path + pix + " 失败。");
            LogPrintUtil.print("*/");
            e.printStackTrace();
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建文件夹
     * @param name
     */
    public static String createFolder(String name, String target){
        if (target == null || target.length() == 0){
            target = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        }
        File folder = new File(target + "/" + name);
        if (!folder.exists()){
            folder.mkdirs();
        }
        return folder.getPath();
    }

}
