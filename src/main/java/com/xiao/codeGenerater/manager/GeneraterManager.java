package com.xiao.codeGenerater.manager;

import com.xiao.codeGenerater.analysis.SQLAnalysis;
import com.xiao.codeGenerater.builder.EntityBuilder;
import com.xiao.codeGenerater.builder.MapperBuilder;
import com.xiao.codeGenerater.builder.XMLBuilder;
import com.xiao.codeGenerater.config.BuildConfig;
import com.xiao.codeGenerater.entity.Table;
import com.xiao.codeGenerater.utils.FileUtil;

import java.net.URL;

/**
 * @Author xiaoyuqing
 * @CreateDate 2019/9/11 16:58
 */
public class GeneraterManager {

    /**
     * 生成
     * @param buildConfig
     */
    public static void generate(String xmlPath) throws Exception {
        //解析xml文件配置信息
        BuildConfig buildConfig = ConfigManager.analysis(xmlPath);

        //查询表创建语句
        String sql = MySqlManager.getTableCreateSql(buildConfig);

        //解析语句
        SQLAnalysis sqlAnalysis = new SQLAnalysis(buildConfig);
        Table table = sqlAnalysis.analysis(sql);

        //创建文件生成目录
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        if (path.endsWith("classes/")){
            path = path.substring(0, path.lastIndexOf("classes"));
        }
        String floderPath = "result/" + buildConfig.getDriveHost() + "/" + buildConfig.getDriveDatabase() + "/" + table.getName();
        buildConfig.setSavePath(FileUtil.createFolder(floderPath, path));

        //生成实体类
        EntityBuilder entityBuilder = new EntityBuilder();
        entityBuilder.build(buildConfig, table);

        //生成mapper类
        MapperBuilder mapperBuilder = new MapperBuilder();
        mapperBuilder.build(buildConfig, table);

        //生成xml
        XMLBuilder xmlBuilder = new XMLBuilder();
        xmlBuilder.build(buildConfig, table);
    }

}
