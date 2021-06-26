package top.qiuzw.doc.util;

import cn.hutool.core.util.StrUtil;
import cn.smallbun.screw.core.engine.EngineFileType;

/**
 * @author: Allen.qiu
 * @Date: 2021/6/26 4:34 下午
 * @description:
 **/
public class FileTypeUtil {

    public static EngineFileType getFileType(String type){
        switch (StrUtil.trim(type)){
            case "HTML文件":
                return EngineFileType.HTML;
            case "WORD文件":
                return EngineFileType.WORD;
            case "Markdown文件":
                return EngineFileType.MD;
            default:
                return EngineFileType.HTML;
        }
    }
}
