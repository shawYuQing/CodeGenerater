# CodeGenerater
基于mysql表，自动生成java实体类，mapper和mybatis xml文件

## 使用方法
这是一个maven项目，导入到编辑器中，编译运行即可。  
执行的方法类：`com.xiao.codeGenerater.CodeBuilder`

### 1、新建并修改配置文件
在项目resource文件下新建并修改自己的配置文件。  
demo.xml里面已经有模板和说明

### 2、指定配置文件执行
修改CodeBuilder中main方法的配置文件路径并执行，如：  
`build("localhost/demo/demo.xml")`

如果顺利执行，会在控制台打印一些执行日志。