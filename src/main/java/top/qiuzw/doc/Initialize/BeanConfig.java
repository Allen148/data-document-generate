package top.qiuzw.doc.Initialize;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.omg.CORBA.PUBLIC_MEMBER;
import top.qiuzw.doc.model.DataSourceThreadLocal;
import top.qiuzw.doc.model.SourceInfoModel;
import top.qiuzw.doc.model.SourceInfos;
import top.qiuzw.doc.util.FileTypeUtil;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Allen.qiu
 * @Date: 2021/6/25 10:22 下午
 * @description:
 **/
public class BeanConfig {

    public static SourceInfos sourceInfos;
    public static List<SourceInfoModel> infoModelList;
    static {
        sourceInfos = DataSourceThreadLocal.get();
        infoModelList = sourceInfos.getInfoModelList();
    }

    public static DataSource generateDataSource(int index){
        SourceInfoModel source = infoModelList.get(index);
        //数据源
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(source.getDriverClass());
        hikariConfig.setJdbcUrl(source.getUrl());
        hikariConfig.setUsername(source.getUsername());
        hikariConfig.setPassword(source.getPassword());
        //设置可以获取tables remarks信息
        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        return new HikariDataSource(hikariConfig);
    }

    public static EngineConfig generateEngine(int index){
        //生成配置
        return EngineConfig.builder()
                //生成文件路径
                .fileOutputDir(sourceInfos.getFilePath())
                //打开目录
                .openOutputDir(true)
                //文件类型
                .fileType(FileTypeUtil.getFileType(infoModelList.get(index).getFileType()))
                //生成模板实现
                .produceType(EngineTemplateType.freemarker)
                //自定义文件名称
                .fileName(infoModelList.get(index).getFileName()).build();
    }

    public static ProcessConfig generateProcess(int index){
        return ProcessConfig.builder()
                //指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
                //根据名称指定表生成
                .designatedTableName(new ArrayList<>())
                //根据表前缀生成
                .designatedTablePrefix(new ArrayList<>())
                //根据表后缀生成
                .designatedTableSuffix(new ArrayList<>())
                //忽略表名
                .ignoreTableName(infoModelList.get(index).getIgnoreTables())
                //忽略表前缀
                .ignoreTablePrefix(new ArrayList<>())
                //忽略表后缀
                .ignoreTableSuffix(new ArrayList<>()).build();
    }

    public static Configuration generateConfiguration(int index){
        //配置
        return Configuration.builder()
                //版本
                .version(ConstantConfig.VERSION)
                //描述
                .description(ConstantConfig.DESCRIPTION)
                //数据源
                .dataSource(generateDataSource(index))
                //生成配置
                .engineConfig(generateEngine(index))
                //生成配置
                .produceConfig(generateProcess(index))
                .build();
    }


    //
//    //忽略表
//    ArrayList<String> ignoreTableName = new ArrayList<>();
//        ignoreTableName.add("test_user");
//        ignoreTableName.add("test_group");
//    //忽略表前缀
//    ArrayList<String> ignorePrefix = new ArrayList<>();
//        ignorePrefix.add("test_");
//    //忽略表后缀
//    ArrayList<String> ignoreSuffix = new ArrayList<>();
//        ignoreSuffix.add("_test");
}
