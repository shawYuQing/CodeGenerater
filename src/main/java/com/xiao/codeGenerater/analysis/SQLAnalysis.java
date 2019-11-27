package com.xiao.codeGenerater.analysis;

import com.xiao.codeGenerater.config.BuildConfig;
import com.xiao.codeGenerater.entity.DbField;
import com.xiao.codeGenerater.entity.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * sql解析器
 * Created by xiaoyq on 2019/2/20.
 */
public class SQLAnalysis {

    private BuildConfig conf;

    public SQLAnalysis(BuildConfig conf){
        this.conf = conf;
    }

    /**
     * 解析sql
     * @param conf
     * @param context
     * @return
     */
    public Table analysis(String context) throws Exception {
        //替换掉多空格为单空格
        while (context.indexOf("  ") >= 0){
            context = context.replaceAll("  ", " ");
        }

        int start = context.indexOf("\n") + 1;
        String line = context.substring(0, start);
        line = line.replaceAll(" ", "");
        if (line.indexOf("CREATETABLE") == -1 && line.indexOf("createtable") == -1) {
            throw new Exception("解析SQL失败：可能不是创建表语句。");
        }

        String tableName = line.substring(line.indexOf("`") + 1, line.lastIndexOf("`"));
        if (tableName == null || tableName.length() == 0){
            throw new Exception("解析SQL失败：找不到表名。");
        }

        String primaryKey = transPrimaryKey(context);

        Table table = new Table(tableName);

        String baseName = conf.getEntityName();
        if (baseName == null || baseName.length() == 0){
            baseName = transName(baseName, true);
        }
        table.setName(baseName);
        List<DbField> fields = new ArrayList<>();

        int end = 0;
        while (true){
            end = context.indexOf("\n", start) + 1;
            if (end == 0){
                line = context.substring(start);
                break;
            } else {
                line = context.substring(start, end);
                start = end;

                if (line.trim().startsWith("`")){
                    DbField field = transField(line);
                    if (primaryKey != null && field.getField().equals(primaryKey)){
                        table.setPrimaryKey(field);
                        field.setPrimaryKey(true);
                    }
                    fields.add(field);
                }
            }
        }
        if (fields.size() == 0){
            throw new Exception("解析SQL失败：字段不能少于1个。");
        }
        table.setFields(fields);

        // 解析表备注
        table.setComment(transComment(line));
        return table;
    }

    /**
     * 解析字段行
     * @param line
     * @return
     * @throws Exception
     */
    private DbField transField(String line) throws Exception {
        // 截取名称
        int start = line.indexOf("`") + 1;
        int end = line.lastIndexOf("`") + 1;
        if (start == 0 || end == 0){
            throw new Exception("解析SQL失败：字段解析失败");
        }

        String field = line.substring(start, end - 1);
        end++;

        // 截取类型
        int end2 = line.indexOf(" ", end);
        if (end2 == -1) {
            throw new Exception("解析SQL失败：字段类型解析失败");
        }
        String type = line.substring(end, end2);
        if (type.indexOf("(") >= 0){
            type = type.substring(0, type.indexOf("("));
        }

        DbField dbField = new DbField();
        dbField.setField(field);
        dbField.setName(transName(field, false));
        dbField.setType(conf.getTypeConverter().convert(type,"Object"));
        dbField.setImportText(conf.getTypeConverter().importType(dbField.getType(), null));
        dbField.setComment(transComment(line));

        return dbField;
    }

    /**
     * 解析备注
     * @param text
     * @return
     */
    private String transComment(String text){
        String comment = "";
        int start = text.indexOf("COMMENT");
        if (start < 0){
            start = text.indexOf("comment");
        }
        if (start >= 0){
            start = text.indexOf("'", start + 7) + 1;
            int end = text.indexOf("'", start);
            comment = text.substring(start, end);
        }
        return comment;
    }

    /**
     * 解析主键
     * @param text
     * @return
     */
    private String transPrimaryKey(String text){
        String key = null;
        int start = text.indexOf("PRIMARY KEY");
        if (start < 0){
            start = text.indexOf("primary key");
        }
        if (start >= 0){
            start = text.indexOf("`", start + 11) + 1;
            int end = text.indexOf("`", start);
            key = text.substring(start, end);
        }
        return key;
    }

    private String transName(String name, boolean transFirstWord){
        char[] chars = name.toCharArray();
        boolean trans = false;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (trans || (i == 0 && transFirstWord)){
                chars[i] = Character.toUpperCase(c);
                trans = false;
            }
            if (c == '_'){
                trans = true;
            }
        }
        name = new String(chars);
        name = name.replaceAll("_", "");
        return name;
    }

}
