package com.xiao.codeGenerater.config;

import com.xiao.codeGenerater.convert.TypeConverter;

/**
 * @Author xiaoyuqing
 * @CreateDate 2019/9/11 17:00
 */
public class BuildConfig {

    private String driveHost;

    private String drivePort;

    private String driveUsername;

    private String drivePassword;

    private String driveDatabase;

    private String driveTable;

    private String entityName;

    private boolean entityGetter;

    private boolean entitySetter;

    private String entityBasePackage;

    private String entityParent;

    private String mapperBasePackage;

    private TypeConverter typeConverter;

    private String savePath;

    public String getDriveHost() {
        return driveHost;
    }

    public void setDriveHost(String driveHost) {
        this.driveHost = driveHost;
    }

    public String getDrivePort() {
        return drivePort;
    }

    public void setDrivePort(String drivePort) {
        this.drivePort = drivePort;
    }

    public String getDriveUsername() {
        return driveUsername;
    }

    public void setDriveUsername(String driveUsername) {
        this.driveUsername = driveUsername;
    }

    public String getDrivePassword() {
        return drivePassword;
    }

    public void setDrivePassword(String drivePassword) {
        this.drivePassword = drivePassword;
    }

    public String getDriveDatabase() {
        return driveDatabase;
    }

    public void setDriveDatabase(String driveDatabase) {
        this.driveDatabase = driveDatabase;
    }

    public String getDriveTable() {
        return driveTable;
    }

    public void setDriveTable(String driveTable) {
        this.driveTable = driveTable;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public boolean isEntityGetter() {
        return entityGetter;
    }

    public void setEntityGetter(boolean entityGetter) {
        this.entityGetter = entityGetter;
    }

    public boolean isEntitySetter() {
        return entitySetter;
    }

    public void setEntitySetter(boolean entitySetter) {
        this.entitySetter = entitySetter;
    }

    public String getEntityBasePackage() {
        return entityBasePackage;
    }

    public void setEntityBasePackage(String entityBasePackage) {
        this.entityBasePackage = entityBasePackage;
    }

    public String getEntityParent() {
        return entityParent;
    }

    public void setEntityParent(String entityParent) {
        this.entityParent = entityParent;
    }

    public String getMapperBasePackage() {
        return mapperBasePackage;
    }

    public void setMapperBasePackage(String mapperBasePackage) {
        this.mapperBasePackage = mapperBasePackage;
    }

    public TypeConverter getTypeConverter() {
        return typeConverter;
    }

    public void setTypeConverter(TypeConverter typeConverter) {
        this.typeConverter = typeConverter;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
