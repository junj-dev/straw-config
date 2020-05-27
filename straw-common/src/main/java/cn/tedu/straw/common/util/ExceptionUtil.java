package cn.tedu.straw.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Description: 异常工具类
 * @Author: ChenHaiBao
 * @CreateDate: 2020/5/22$ 16:00$
 * @Version: 1.0
 */
public class ExceptionUtil {
    public static String getStackTraceInfo(Exception e){
        StringWriter stringWriter=null;
        PrintWriter printWriter=null;
        try{
            stringWriter=new StringWriter();
            printWriter=new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            printWriter.flush();
            stringWriter.flush();
            return  stringWriter.toString();
        }catch (Exception ex){
            return "发生错误";
        }finally {
            if(stringWriter!=null){
                try {
                    stringWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if(printWriter!=null){
                printWriter.close();
            }
        }

    }
}
