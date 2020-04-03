package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.common.constant.RedisKeyPrefix;
import cn.tedu.straw.common.util.NumberUtils;
import cn.tedu.straw.portal.config.AliyunMesageConfig;
import cn.tedu.straw.portal.mapper.ClassroomMapper;
import cn.tedu.straw.portal.model.Classroom;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.IClassroomService;
import cn.tedu.straw.portal.service.IMessageService;
import cn.tedu.straw.portal.service.IUserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 短信发送业务类
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/28$ 0:34$
 * @Version: 1.0
 */
@Service
@Slf4j
public class MessageServiceImpl implements IMessageService {



    @Resource
    private AliyunMesageConfig mesageConfig;
    @Autowired
    private RedisTemplate<String, String> strRedisTemplate;
    @Autowired
    private IUserService userService;
    @Resource
    private ClassroomMapper classroomMapper;

    @Override
    public StrawResult sendRegisterCode(String phone,String inviteCode) {

        //判断邀请码是否正确,直接通过邀请码查找班级，如果班级不存在说明邀请码错误
        QueryWrapper query=new QueryWrapper();
        query.eq("invite_code",inviteCode);
        Classroom classroom = classroomMapper.selectOne(query);
        if(classroom==null){
            return new StrawResult().failed("邀请码错误");
        }


        //先判断号码是否已被注册
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",phone);
        User user = userService.getOne(queryWrapper);
        if(user!=null){
            return new StrawResult().failed("该手机号已注册！请直接登录！");
        }

        return sendAliyunMesageIdentifyingCode(phone, RedisKeyPrefix.REGISTER_CODE_PREFIX,mesageConfig.getTemplateCode_register());
    }

    /**
     * 发送密码重置验证码
     * @param phone
     * @return
     */
    @Override
    public StrawResult sendResetPasswordCode(String phone) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",phone);
        User user = userService.getOne(queryWrapper);
        if(user==null){
            return new StrawResult().failed("该手机号未注册，请先注册！");
        }
        return sendAliyunMesageIdentifyingCode(phone, RedisKeyPrefix.RESET_PASSWORD_CODE_PREFIX,mesageConfig.getTemplateCode_resetPassword());
    }

    /**
     * 发送各种4位数的短信验证码
     * @param phone 电话号码
     * @param templateCode 短息模板
     * @param redisKeyPrefix redis的key的前缀
     * @return
     * @throws ClientException
     */
    private StrawResult sendAliyunMesageIdentifyingCode(String phone,String redisKeyPrefix,String templateCode){

        try {
            String verificationCode= NumberUtils.generateRandomNum(4);
            DefaultProfile profile = DefaultProfile.getProfile(mesageConfig.getRegionId(), mesageConfig.getAccessKeyId(), mesageConfig.getSecret());
            IAcsClient client = new DefaultAcsClient(profile);
            CommonRequest request = new CommonRequest();
            request.setSysMethod(MethodType.POST);
            request.setSysDomain("dysmsapi.aliyuncs.com");
            request.setSysVersion("2017-05-25");
            request.setSysAction("SendSms");
            request.putQueryParameter("RegionId", mesageConfig.getRegionId());
            request.putQueryParameter("PhoneNumbers", phone);
            request.putQueryParameter("SignName", mesageConfig.getSignName());
            request.putQueryParameter("TemplateCode", templateCode);
            request.putQueryParameter("TemplateParam", "{\"code\":\""+verificationCode+"\"}");
            CommonResponse response = client.getCommonResponse(request);
            //把验证码保存到redis,有效时间为5分钟
            strRedisTemplate.opsForValue().set(redisKeyPrefix+phone,verificationCode,5, TimeUnit.MINUTES);
            return getAliyunMessageStrawResult(phone, response);
        }catch (ServerException e){
            log.error("ErrorCode=" + e.getErrCode());
            log.error("ErrorMessage=" + e.getErrMsg());
            log.error("ResponseId=" + e.getRequestId());
        }catch (ClientException e){
            log.error("ErrorCode=" + e.getErrCode());
            log.error("ErrorMessage=" + e.getErrMsg());
        }
        return new StrawResult().failed("短信发送服务繁忙，请稍后再试！");
    }

    /**
     * 解析并封装返回发送短信的结果
     * @param phone
     * @param response
     * @return
     */
    private StrawResult getAliyunMessageStrawResult(String phone, CommonResponse response) {
        String data = response.getData();
        System.out.println("data:"+data);
        JSONObject jsonObject = JSONObject.parseObject(data);
        //短信发送状态
        String code = jsonObject.getString("Code");
        String message=jsonObject.getString("Message");
        if("OK".equals(code)){
            return new StrawResult().success("发送成功!");
        }else if("isv.MOBILE_NUMBER_ILLEGAL".equals(code)){
            return new StrawResult().failed(phone+"无效号码");
        }else if("isp.OUT_OF_SERVICE".equals(code)){
            return new StrawResult().failed(phone+"业务停机");
        }else{
            log.error("短信发送失败，原因："+message);
            return new StrawResult().failed("短信发送服务繁忙，请稍后再试！");
        }
    }
}
