package cn.tedu.straw.common.util;

import java.util.Random;

/**
 * @Description: 数字工具类
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/28$ 1:27$
 * @Version: 1.0
 */
public class NumberUtils {

    /**
     * 获取随机数字
     * @param size 位数
     * @return
     */
    public static String  generateRandomNum(Integer size){
        Random random = new Random();
        StringBuffer result=new StringBuffer();
        for (int i=0;i<size;i++)
        {
            result.append(random.nextInt(10));
        }
       return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateRandomNum(6));
    }
}
