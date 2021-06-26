package top.qiuzw.doc.model;

import lombok.Data;

import java.util.List;

/**
 * @author: Allen.qiu
 * @Date: 2021/6/25 10:45 下午
 * @description:
 **/
@Data
public class SourceInfoModel {

    /**
     * 数据源id
     */
    private String id;

    /**
     * url
     */
    private String url;

    /**
     * 数据库用户名
     */
    private String username;

    /**
     * 数据库密码
     */
    private String password;

    /**
     * 生成文件类型
     */
    private String fileType;

    /**
     * 生成文件名
     */
    private String fileName;

    /**
     * 数据源驱动
     */
    private String driverClass;

    /**
     * 忽略表名前缀
     */
    private String prefixTable;

    /**
     * 忽略表名后缀
     */
    private String suffixTable;

    /**
     * 忽略表名集
     */
    private List<String> ignoreTables;


}
