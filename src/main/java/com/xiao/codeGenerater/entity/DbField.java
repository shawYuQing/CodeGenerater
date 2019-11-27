package com.xiao.codeGenerater.entity;

/**
 * Created by xiaoyq on 2019/2/20.
 */
public class DbField {

    private String field;//字段名称

    private String name;//字段解析后类属性名称

    private String type;//字段类型

    private boolean needImport;//是否需要导入类型依赖

    private String importText;//需要导入依赖信息

    private String comment;//字段备注

    private boolean primaryKey;//是否为主键

    public DbField() {}

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isNeedImport() {
        return needImport;
    }

    public String getImportText() {
        return importText;
    }

    public void setImportText(String importText) {
        this.needImport = importText == null ? false : true;
        this.importText = "import " + importText;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }
}
