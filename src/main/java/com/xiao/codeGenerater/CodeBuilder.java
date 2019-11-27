package com.xiao.codeGenerater;

import com.xiao.codeGenerater.manager.GeneraterManager;

/**
 * @Author xiaoyuqing
 * @CreateDate 2019/9/11 16:35
 */
public class CodeBuilder {

    public static void main(String[] args) {
        build("localhost/demo/user.xml");
    }

    private static void build(String xmlPath){
        if (xmlPath != null && xmlPath.length() > 4){
            try {
                //执行生成
                GeneraterManager.generate(xmlPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("配置文件路径异常！");
        }
    }

}
