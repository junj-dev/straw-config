package cn.tedu.straw.common.constant;

/**
 * @Description: redis的key的前缀
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/31$ 9:41$
 * @Version: 1.0
 */
public class RedisKeyPrefix {

    //注册验证码的redis缓存key的前缀
    public static final String REGISTER_CODE_PREFIX="straw:register:phone:";
    //重置密码的redis的缓存key的前缀
    public static final String RESET_PASSWORD_CODE_PREFIX="straw:resetpassword:phone:";
    //按标签分类后的问题综合评分key名称
    public  static  final  String TAG_QUEATION_SCORE="straw:portal:question:tag:";
}
