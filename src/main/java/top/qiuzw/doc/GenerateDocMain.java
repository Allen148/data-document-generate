package top.qiuzw.doc;

import cn.smallbun.screw.core.execute.DocumentationExecute;
import top.qiuzw.doc.Initialize.BeanConfig;
import top.qiuzw.doc.Initialize.SAXmlReadConfig;
import top.qiuzw.doc.model.DataSourceThreadLocal;
import top.qiuzw.doc.model.SourceInfos;

/**
 * author: Allen.qiu
 * Date: 2021/6/25 10:45
 * description: 主函数
 **/
public class GenerateDocMain extends SAXmlReadConfig{


    public static void main(String[] args){
        SourceInfos sourceInfos = DataSourceThreadLocal.get();
        for (int i = 0; i < sourceInfos.getInfoModelList().size(); i++) {
            //执行生成ff
            new DocumentationExecute(BeanConfig.generateConfiguration(i)).execute();
        }
    }


}
