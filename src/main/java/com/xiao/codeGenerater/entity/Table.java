package com.xiao.codeGenerater.entity;

import java.util.List;

/**
 * Created by xiaoyq on 2019/2/20.
 */
public class Table {

    private String tableName;//表名

    private String name;//类名

    private String comment;//表备注

    private DbField primaryKey;//主键

    private List<DbField> fields;

    public Table() {}

    public Table(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public DbField getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(DbField primaryKey) {
        this.primaryKey = primaryKey;
    }

    public List<DbField> getFields() {
        return fields;
    }

    public void setFields(List<DbField> fields) {
        this.fields = fields;
    }
}
