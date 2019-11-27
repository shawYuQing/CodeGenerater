package com.xiao.codeGenerater.convert;

/**
 * @Author xiaoyuqing
 * @CreateDate 2019/9/12 15:20
 */
public interface TypeConverter {

    String convert(String type);

    String convert(String type, String defalutType);

    String importType(String type);

    String importType(String type, String defalutValue);

}
