package top.qiuzw.doc.model;

import lombok.Data;

import java.util.List;

/**
 * @author: Allen.qiu
 * @Date: 2021/6/25 10:45 下午
 * @description:
 **/
@Data
public class SourceInfos {



    private String filePath;

    private List<SourceInfoModel> infoModelList;

}
