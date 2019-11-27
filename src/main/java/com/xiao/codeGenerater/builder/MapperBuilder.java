package com.xiao.codeGenerater.builder;

import com.xiao.codeGenerater.config.BuildConfig;
import com.xiao.codeGenerater.entity.DbField;
import com.xiao.codeGenerater.entity.Table;
import com.xiao.codeGenerater.utils.FileUtil;
import com.xiao.codeGenerater.utils.LogPrintUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by xiaoyq on 2019/2/20.
 */
public class MapperBuilder {

    /**
     * mapper构建
     * @param conf
     * @param table
     */
    public void build(BuildConfig conf, Table table){
        StringBuilder root = new StringBuilder();
        root.append("package ").append(conf.getMapperBasePackage()).append(";\n\n");

        root.append("import java.util.List;\n");
        root.append("import org.apache.ibatis.annotations.Param;\n\n");

        root.append("import ").append(conf.getEntityBasePackage()).append(".").append(table.getName()).append(";\n\n");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date());
        root.append("/**\n");
        root.append(" * ").append(table.getComment()).append(" mapper\n");
        root.append(" * Created by Author on ").append(date).append(".\n");
        root.append(" */\n");

        root.append("public interface ").append(table.getName()).append("Mapper {\n\n");

        //insert
        root.append("    /**\n");
        root.append("     * 全字段插入，所有字段都会插入\n");
        root.append("     * @param model：插入实体类\n");
        root.append("     */\n");
        root.append("    int insert(").append(table.getName()).append(" model);\n\n");

        //insertSelective
        root.append("    /**\n");
        root.append("     * 选择字段插入：实体类中不为空的字段会被插入\n");
        root.append("     * @param model：插入实体类\n");
        root.append("     */\n");
        root.append("    int insertSelective(").append(table.getName()).append(" model);\n\n");

        //updateSelective
        root.append("    /**\n");
        root.append("     * 选择字段更新\n");
        root.append("     * @param model：属性不为空，会被修改\n");
        root.append("     * @param selective：属性不为空，会当作判断条件\n");
        root.append("     */\n");
        root.append("    int updateSelective(").append("@Param(\"model\") ").append(table.getName()).append(" model");
        root.append(", @Param(\"selective\") ").append(table.getName()).append(" selective);\n\n");

        //selectListSelective
        root.append("    /**\n");
        root.append("     * 选择字段查找列表\n");
        root.append("     * @param selective：属性不为空，会当作判断条件\n");
        root.append("     */\n");
        root.append("    List<").append(table.getName()).append("> selectListSelective(");
        root.append(table.getName()).append(" selective);\n\n");

        //selectOneSelective
        root.append("    /**\n");
        root.append("     * 选择字段查找单个信息\n");
        root.append("     * @param selective：属性不为空，会当作判断条件\n");
        root.append("     */\n");
        root.append("    ").append(table.getName()).append(" selectOneSelective(");
        root.append(table.getName()).append(" selective);\n\n");

        //deleteSelective
        root.append("    /**\n");
        root.append("     * 选择字段删除\n");
        root.append("     * @param selective：属性不为空，会当作判断条件\n");
        root.append("     */\n");
        root.append("    int deleteSelective(").append(table.getName()).append(" selective);\n\n");

        root.append("}");

        String rootString = root.toString();

        LogPrintUtil.print();
        LogPrintUtil.print("/* ");
        LogPrintUtil.print(" 生成mapper " + table.getName() + "Mapper.java");
//        LogPrintUtil.print(" 时间：" + date);
        LogPrintUtil.print("*/");
//        LogPrintUtil.print();
//        LogPrintUtil.print(rootString);
//        LogPrintUtil.print();

        LogPrintUtil.print();
        FileUtil.createFile(conf.getSavePath() + "/" + table.getName(), "Mapper.java", rootString.getBytes());

    }

}
