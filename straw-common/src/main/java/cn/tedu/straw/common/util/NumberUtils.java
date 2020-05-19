package cn.tedu.straw.common.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

//    public static Integer[]  generateRandomNumArray(Integer size){
//        Random random = new Random();
//        Integer array[]=new Integer[size];
//        for (int i=0;i<size;i++)
//        {
//
//           array[i]=random.nextInt(size);
//        }
//        return array;
//    }

//    public static void main(String[] args) {
//        System.out.println((11+3*8)/4);
//    }


}
