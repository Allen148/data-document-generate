package top.qiuzw.doc.model;

/**
 * @author: Allen.qiu
 * @Date: 2021/6/25 10:39 下午
 * @description: 当前线程数据储存临时空间
 **/
public class DataSourceThreadLocal {

    private DataSourceThreadLocal(){

    }

    private static final ThreadLocal<SourceInfos> LOCAL = new ThreadLocal<SourceInfos>();

    public static void set(SourceInfos infos){
        LOCAL.set(infos);
    }

    public static SourceInfos get(){
        return LOCAL.get();
    }
}
