package top.qiuzw.doc.Initialize;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import top.qiuzw.doc.model.DataSourceThreadLocal;
import top.qiuzw.doc.model.SourceInfoModel;
import top.qiuzw.doc.model.SourceInfos;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;

/**
 * @author: Allen.qiu
 * @Date: 2021/6/25 10:45 下午
 * @description:
 **/
@Slf4j
public abstract class SAXmlReadConfig {

    static {
        try {
            readXml();
        } catch (Exception e) {
            throw new RuntimeException("xml文件读取异常");
        }
    }
    public static void readXml() throws Exception {
        List<SourceInfoModel> infoModels = CollUtil.newArrayList();
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        // 创建解析器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory.newDocumentBuilder();
        // 创建一个Document对象
        Document doc = db.parse(path + "datasource.xml");
        NodeList sourceList = doc.getElementsByTagName(ConstantConfig.SOURCE);
        log.info("共配置" + sourceList.getLength() + "个数据源");

        for (int i = 0; i < sourceList.getLength(); i++) {
            Properties properties = new Properties();
            Node source = sourceList.item(i);
            NamedNodeMap attributes = source.getAttributes();
            Node idNode = attributes.getNamedItem(ConstantConfig.ID);
            if (idNode!=null){
                properties.setProperty(idNode.getNodeName(),idNode.getNodeValue());
            }
            // 获取source节点所有属性集合
            NodeList childNodes = source.getChildNodes();
            for (int k = 0; k < childNodes.getLength(); k++) {
                // 区分,去掉空格和换行符
                Node item = childNodes.item(k);
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    properties.setProperty(item.getNodeName(), item.getTextContent());
                }
            }
            SourceInfoModel sourceInfoModel = new SourceInfoModel();
            BeanUtil.copyProperties(properties, sourceInfoModel,ConstantConfig.IGNORE_TABLES);
            String ignoreStr = properties.getProperty(ConstantConfig.IGNORE_TABLES);
            ArrayList<String> igonoreTables = CollUtil.newArrayList(StrUtil.splitToArray(ignoreStr, ','));
            sourceInfoModel.setIgnoreTables(igonoreTables);
            infoModels.add(sourceInfoModel);
        }
        NodeList docNode = doc.getElementsByTagName(ConstantConfig.DOC);
        if (docNode.getLength()<1){
            throw new RuntimeException("未配置正确输出文件名或路径");
        }
//        docNode默认只取配置的第一个标签
        NodeList docChildNodes = docNode.item(0).getChildNodes();
        Properties filePro = new Properties();
        for (int i = 0; i < docChildNodes.getLength(); i++) {
            // 区分,去掉空格和换行符
            if (docChildNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Node item = docChildNodes.item(i);
                filePro.setProperty(item.getNodeName(), item.getTextContent());
            }
        }
        SourceInfos sourceInfos = new SourceInfos();
        BeanUtil.copyProperties(filePro, sourceInfos);
        sourceInfos.setInfoModelList(infoModels);

        DataSourceThreadLocal.set(sourceInfos);
    }

    //        ResourceBundle resource = ResourceBundle.getBundle("datasource");//test为属性文件名，放在包com.mmq下，如果是放在src下，直接用test即可
//        String key = resource.getString("username");
//        System.out.println(key);
}
