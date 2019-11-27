package com.xiao.codeGenerater.builder;

import com.xiao.codeGenerater.config.BuildConfig;
import com.xiao.codeGenerater.entity.DbField;
import com.xiao.codeGenerater.entity.Table;
import com.xiao.codeGenerater.utils.FileUtil;
import com.xiao.codeGenerater.utils.LogPrintUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * xml构建
 * Created by xiaoyq on 2019/2/22.
 */
public class XMLBuilder {

    public void build(BuildConfig conf, Table table){
        StringBuilder root = new StringBuilder();
        root.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
        root.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
        root.append("<mapper namespace=\"").append(conf.getMapperBasePackage()).append(".")
                .append(table.getName()).append("Mapper").append("\" >\n\n");

        //基础表字段
        StringBuilder baseColumn = new StringBuilder();
        //插入方法value值
        StringBuilder insertValues = new StringBuilder();
        //选择字段插入方法字段
        StringBuilder insertSelectiveColumn = new StringBuilder();
        //选择字段插入方法value值
        StringBuilder insertSelectiveValues = new StringBuilder();
        //选择字段修改方法字段
        StringBuilder updateSelectiveColumn = new StringBuilder();
        //选择字段修改方法判断条件
        StringBuilder updateSelectiveValues = new StringBuilder();
        //判断条件
        StringBuilder selectWhere = new StringBuilder();

        //选择字段前加上mybatis的trim标签，去除掉最后面的逗号
        insertSelectiveColumn.append("        <trim suffixOverrides=\",\" >\n");
        insertSelectiveValues.append("        <trim suffixOverrides=\",\" >\n");
        updateSelectiveColumn.append("        <trim suffixOverrides=\",\" >\n");
        updateSelectiveValues.append("        <trim suffixOverrides=\",\" >\n");

        int size = table.getFields().size();
        for (int i = 0; i < size; i++) {
            DbField field = table.getFields().get(i);

            //基础字段
            baseColumn.append("          ").append(field.getField());

            //全字段插入数据字段名和属性拼接
            insertValues.append("          #{").append(field.getName()).append("}");

            //选择字段插入字段拼接
            insertSelectiveColumn.append("          <if test=\"").append(field.getName()).append(" != null\">");
            insertSelectiveColumn.append(field.getField()).append(",");
            insertSelectiveColumn.append("</if>\n");
            insertSelectiveValues.append("          <if test=\"").append(field.getName()).append(" != null\">");
            insertSelectiveValues.append("#{").append(field.getName()).append("},");
            insertSelectiveValues.append("</if>\n");

            //选择字段更新
            updateSelectiveColumn.append("          <if test=\"model.").append(field.getName()).append(" != null\">");
            updateSelectiveColumn.append("").append(field.getField()).append(" = #{model.").append(field.getName()).append("},");
            updateSelectiveColumn.append("</if>\n");
            updateSelectiveValues.append("          <if test=\"selective.").append(field.getName()).append(" != null\">");
            updateSelectiveValues.append("AND ").append(field.getField()).append(" = #{selective.").append(field.getName()).append("}");
            updateSelectiveValues.append("</if>\n");

            //where筛选字段和属性拼接
            selectWhere.append("          <if test=\"").append(field.getName()).append(" != null\">");
            selectWhere.append("AND ").append(field.getField()).append(" = #{").append(field.getName()).append("}");
            selectWhere.append("</if>\n");

            //全字段插入数据字段名和属性拼接后，如果当前是最后一个就不拼接逗号
            if (i == (size - 1)){
                baseColumn.append("\n");
                insertValues.append("\n");
            } else {
                baseColumn.append(",\n");
                insertValues.append(",\n");
            }

        }

        insertSelectiveColumn.append("        </trim>\n");
        insertSelectiveValues.append("        </trim>\n");
        updateSelectiveColumn.append("        </trim>\n");
        updateSelectiveValues.append("        </trim>\n");

        // insert
        root.append("    <!-- 全字段插入 -->\n");
        root.append("    <insert id=\"insert\" >\n");
        root.append("        INSERT ").append(table.getTableName()).append("(\n");
        root.append(baseColumn);
        root.append("        ) VALUES (\n");
        root.append(insertValues);
        root.append("        )\n");
        root.append("    </insert>\n\n");

        // insertSelective
        root.append("    <!-- 选择字段插入 -->\n");
        root.append("    <insert id=\"insertSelective\" >\n");
        root.append("        INSERT ").append(table.getTableName()).append("(\n");
        root.append(insertSelectiveColumn);
        root.append("        ) VALUES (\n");
        root.append(insertSelectiveValues);
        root.append("        )\n");
        root.append("    </insert>\n\n");

        // updateSelective
        root.append("    <!-- 选择字段更新 -->\n");
        root.append("    <update id=\"updateSelective\" >\n");
        root.append("        UPDATE ").append(table.getTableName()).append(" SET\n");
        root.append(updateSelectiveColumn);
        root.append("        <where>\n");
        root.append(updateSelectiveValues);
        root.append("        </where>\n");
        root.append("    </update>\n\n");

        // selectListSelective
        root.append("    <!-- 选择字段查找列表 -->\n");
        root.append("    <select id=\"selectListSelective\" resultType=\"").append(conf.getEntityBasePackage())
                .append(".").append(table.getName()).append("\" >\n");
        root.append("        SELECT\n");
        root.append(baseColumn);
        root.append("        FROM ").append(table.getTableName()).append("\n");
        root.append("        <where>\n");
        root.append(selectWhere);
        root.append("        </where>\n");
        root.append("    </select>\n\n");

        // selectOneSelective
        root.append("    <!-- 选择字段查找单个信息 -->\n");
        root.append("    <select id=\"selectOneSelective\" resultType=\"").append(conf.getEntityBasePackage())
                .append(".").append(table.getName()).append("\" >\n");
        root.append("        SELECT\n");
        root.append(baseColumn);
        root.append("        FROM ").append(table.getTableName()).append("\n");
        root.append("        <where>\n");
        root.append(selectWhere);
        root.append("        </where>\n");
        root.append("        LIMIT 1\n");
        root.append("    </select>\n\n");

        // deleteSelective
        root.append("    <!-- 选择字段删除 -->\n");
        root.append("    <delete id=\"deleteSelective\" >\n");
        root.append("        DELETE FROM ").append(table.getTableName()).append("\n");
        root.append("        <where>\n");
        root.append(selectWhere);
        root.append("        </where>\n");
        root.append("    </delete>\n\n");

        root.append("</mapper>");

        String rootString = root.toString();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date());
        LogPrintUtil.print();
        LogPrintUtil.print("/* ");
        LogPrintUtil.print(" 生成XML " + table.getName() + "Mapper.xml");
//        LogPrintUtil.print(" 时间：" + date);
        LogPrintUtil.print("*/");
//        LogPrintUtil.print();
//        LogPrintUtil.print(rootString);
//        LogPrintUtil.print();

        LogPrintUtil.print();
        FileUtil.createFile(conf.getSavePath() + "/" + table.getName(), "Mapper.xml", rootString.getBytes());
    }

}
