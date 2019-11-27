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
public class EntityBuilder {

    /**
     * 实体类构建
     * @param conf
     * @param table
     */
    public void build(BuildConfig conf, Table table){
        StringBuilder root = new StringBuilder();
        root.append("package ").append(conf.getEntityBasePackage()).append(";\n\n");

        StringBuilder fields = new StringBuilder();
        StringBuffer getset = new StringBuffer();

        Set<String> importText = new HashSet<>();

        for (DbField dbField : table.getFields()){
            if (dbField.isNeedImport()) {
                importText.add(dbField.getImportText());
            }

            fields.append("    private ").append(dbField.getType()).append(" ").append(dbField.getName())
                    .append(";//").append(dbField.getComment()).append("\n\n");

            String upName = dbField.getName();
            upName = upName.substring(0, 1).toUpperCase() + upName.substring(1);
            getset.append("    public ").append(dbField.getType()).append(" get").append(upName).append("(){\n");
            getset.append("        return ").append(dbField.getName()).append(";\n    }\n\n");
            getset.append("    public void set").append(upName).append("(").append(dbField.getType())
                    .append(" ").append(dbField.getName()).append("){\n");
            getset.append("        this.").append(dbField.getName()).append(" = ").append(dbField.getName()).append(";\n    }\n\n");

        }

        if (importText.size() > 0){
            for (String text : importText){
                root.append(text).append(";\n");
            }
        }

        root.append("\n");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date());
        root.append("/**\n");
        root.append(" * ").append(table.getComment()).append("\n");
        root.append(" * Created by Author on ").append(date).append(".\n");
        root.append(" */\n");

        root.append("public class ").append(table.getName()).append(" {\n\n");

        root.append(fields);

        root.append(getset);

        root.append("}");

        String rootString = root.toString();

        LogPrintUtil.print();
        LogPrintUtil.print("/* ");
        LogPrintUtil.print(" 生成实体类 " + table.getName() + ".java");
//        LogPrintUtil.print(" 时间：" + date);
        LogPrintUtil.print("*/");
//        LogPrintUtil.print();
//        LogPrintUtil.print(rootString);
//        LogPrintUtil.print();

        LogPrintUtil.print();
        FileUtil.createFile(conf.getSavePath() + "/" + table.getName(), ".java", rootString.getBytes());

    }

}
