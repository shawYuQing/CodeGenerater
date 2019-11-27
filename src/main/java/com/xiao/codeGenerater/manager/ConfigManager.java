package com.xiao.codeGenerater.manager;

import com.xiao.codeGenerater.config.BuildConfig;
import com.xiao.codeGenerater.convert.DefalutTypeConverter;
import com.xiao.codeGenerater.convert.TypeConverter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * @Author xiaoyuqing
 * @CreateDate 2019/9/11 17:02
 */
public class ConfigManager {

    public static BuildConfig analysis(String path) throws Exception{
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        if (url == null){
            throw new FileNotFoundException("找不到文件：" + path);
        }
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(url.getPath());

        BuildConfig config = new BuildConfig();

        //解析数据源信息
        analysisDirve(document, config);

        //解析实体类配置信息
        analysisEntity(document, config);

        //解析mapper配置信息
        analysisMapper(document, config);

        //解析类型转换类
        analysisTypeConverter(document, config);

        return config;
    }

    /**
     * 从xml文件中解析mysql数据库链接信息
     * @param document
     * @param config
     */
    private static void analysisDirve(Document document, BuildConfig config){
        Node drive = findNode(document, "drive", true);
        NodeList driveChild = drive.getChildNodes();
        if (driveChild == null || driveChild.getLength() == 0){
            throw new RuntimeException("缺失数据源信息：host!");
        }

        //host
        Node driveNode = findChildNode(driveChild, "host");
        if (driveNode == null){
            throw new RuntimeException("缺失数据源信息：host!");
        }
        config.setDriveHost(driveNode.getTextContent());
        //port
        driveNode = findChildNode(driveChild, "port");
        if (driveNode == null){
            throw new RuntimeException("缺失数据源信息：port!");
        }
        config.setDrivePort(driveNode.getTextContent());
        //username
        driveNode = findChildNode(driveChild, "username");
        if (driveNode == null){
            throw new RuntimeException("缺失数据源信息：username!");
        }
        config.setDriveUsername(driveNode.getTextContent());
        //password
        driveNode = findChildNode(driveChild, "password");
        if (driveNode == null){
            throw new RuntimeException("缺失数据源信息：password!");
        }
        config.setDrivePassword(driveNode.getTextContent());
        //database
        driveNode = findChildNode(driveChild, "database");
        if (driveNode == null){
            throw new RuntimeException("缺失数据源信息：database!");
        }
        config.setDriveDatabase(driveNode.getTextContent());
        //table
        driveNode = findChildNode(driveChild, "table");
        if (driveNode == null){
            throw new RuntimeException("缺失数据源信息：table!");
        }
        config.setDriveTable(driveNode.getTextContent());
    }

    /**
     * 从xml文件中解析实体类信息
     * @param document
     * @param config
     */
    private static void analysisEntity(Document document, BuildConfig config){
        Node entity = findNode(document, "entity", true);
        //解析name属性
        Node q = entity.getAttributes().getNamedItem("name");
        if (q == null){
            throw new RuntimeException("缺失实体类属性：name!");
        }
        config.setEntityName(q.getTextContent());
        //解析getter属性
        q = entity.getAttributes().getNamedItem("getter");
        if (q == null){
            throw new RuntimeException("缺失实体类属性：getter!");
        }
        config.setEntityGetter(Boolean.valueOf(q.getNodeValue()).booleanValue());
        //解析setter属性
        q = entity.getAttributes().getNamedItem("setter");
        if (q == null){
            throw new RuntimeException("缺失实体类属性：setter!");
        }
        config.setEntitySetter(Boolean.valueOf(q.getNodeValue()).booleanValue());

        NodeList entityChild = entity.getChildNodes();
        if (entityChild == null || entityChild.getLength() == 0){
            throw new RuntimeException("缺失实体类信息：basePackage!");
        }
        //解析basePackage
        Node entityNode = findChildNode(entityChild, "basePackage");
        if (entityNode == null){
            throw new RuntimeException("缺失实体类信息：basePackage!");
        }
        config.setEntityBasePackage(entityNode.getTextContent());
    }

    /**
     * 从xml文件中解析实体类信息
     * @param document
     * @param config
     */
    private static void analysisMapper(Document document, BuildConfig config){
        Node mapper = findNode(document, "mapper", true);

        NodeList mapperChild = mapper.getChildNodes();
        if (mapperChild == null || mapperChild.getLength() == 0){
            throw new RuntimeException("缺失mapper信息：basePackage!");
        }
        //解析basePackage
        Node mapperNode = findChildNode(mapperChild, "basePackage");
        if (mapperNode == null){
            throw new RuntimeException("缺失mapper信息：basePackage!");
        }
        config.setMapperBasePackage(mapperNode.getTextContent());
    }

    /**
     * 从xml文件中解析类型转换类
     * @param document
     * @param config
     */
    private static void analysisTypeConverter(Document document, BuildConfig config) throws Exception {
        Node typec = findNode(document, "typeConverter", false);
        if (typec == null){
            config.setTypeConverter(new DefalutTypeConverter());
        } else {
            Node q = typec.getAttributes().getNamedItem("value");
            if (q == null){
                throw new RuntimeException("缺失类型转换属性：value!");
            }
            config.setTypeConverter((TypeConverter) Class.forName(q.getTextContent()).newInstance());
        }
    }

    /**
     * xml文件中找到单个节点元素
     * @param document
     * @param nodeNmae
     * @return
     */
    private static Node findNode(Document document, String nodeNmae, boolean notNull){
        NodeList list = document.getElementsByTagName(nodeNmae);
        //如果节点不是必须存在，那么直接返回空
        if (notNull == false && (list == null || list.getLength() == 0)){
            return null;
        }

        if (list == null || list.getLength() == 0){
            throw new NullPointerException("找不到 <" + nodeNmae + "> 节点!");
        }

        if (list.getLength() > 1){
            throw new RuntimeException("存在多个 <" + nodeNmae + "> 节点!");
        }

        return list.item(0);
    }

    /**
     * 从节点列表中找某个节点
     * @param list
     * @param childName
     * @return
     */
    private static Node findChildNode(NodeList list, String nodeName){
        int len = list.getLength();
        if (len > 0){
            for (int i = 0; i < len; i++) {
                Node child = list.item(i);
                if (nodeName.equals(child.getNodeName())){
                    return child;
                }
            }
        }
        return null;
    }

}
