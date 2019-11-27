package com.xiao.codeGenerater.convert;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xiaoyuqing
 * @CreateDate 2019/9/12 15:21
 */
public class DefalutTypeConverter implements TypeConverter {

    private Map<String, String> typeMap;
    private void initTypeMap(){
        synchronized (this){
            typeMap = new HashMap<>();
            typeMap.put("int", "Integer");
            typeMap.put("tinyint", "Integer");
            typeMap.put("bigint", "Long");
            typeMap.put("varchar", "String");
            typeMap.put("datetime", "Date");
            typeMap.put("date", "Date");
            typeMap.put("double", "Double");
            typeMap.put("text", "String");
            typeMap.put("char", "String");
            typeMap.put("decimal", "BigDecimal");
        }
    }

    private Map<String, String> importMap;
    private void initImportMap(){
        synchronized (this){
            importMap = new HashMap<>();
            importMap.put("Date", "java.util.Date");
            importMap.put("BigDecimal", "java.math.BigDecimal");
        }
    }

    @Override
    public String convert(String type) {
        return convert(type, null);
    }

    @Override
    public String convert(String type, String defalutType) {
        String obj = findConvertType(type);
        if (obj == null){
            return defalutType;
        }
        return obj;
    }

    @Override
    public String importType(String type) {
        return importType(type, null);
    }

    @Override
    public String importType(String type, String defalutValue) {
        String obj = findImport(type);
        if (obj == null){
            return defalutValue;
        }
        return obj;
    }

    private String findConvertType(String type){
        if (typeMap == null){
            initTypeMap();
        }
        return typeMap.get(type);
    }

    private String findImport(String type){
        if (importMap == null){
            initImportMap();
        }
        return importMap.get(type);
    }

}
