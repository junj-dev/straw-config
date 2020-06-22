package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.common.R;
import cn.tedu.straw.common.constant.RedisKeyPrefix;
import cn.tedu.straw.common.util.NumberUtils;
import cn.tedu.straw.portal.config.AliyunMesageConfig;
import cn.tedu.straw.portal.exception.BusinessException;
import cn.tedu.straw.portal.mapper.ClassroomMapper;
import cn.tedu.straw.portal.model.Classroom;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.IMessageService;
import cn.tedu.straw.portal.service.IUserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

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
    @Transactional
    public boolean sendRegisterCode(String phone, String inviteCode) {

        //判断邀请码是否正确,直接通过邀请码查找班级，如果班级不存在说明邀请码错误
        QueryWrapper query=new QueryWrapper();
        query.eq("invite_code",inviteCode);
        Classroom classroom = classroomMapper.selectOne(query);
        if(classroom==null){
            throw  new BusinessException("邀请码错误");
        }
        //先判断号码是否已被注册
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",phone);
        User user = userService.getOne(queryWrapper);
        if(user!=null){
            throw  new BusinessException("该手机号已注册！请直接登录！");
        }

      return  sendIdentifyingCode(phone, RedisKeyPrefix.REGISTER_CODE_PREFIX,mesageConfig.getTemplateCode_register(),4);
    }

    /**
     * 发送密码重置验证码
     * @param phone
     * @return
     */
    @Override
    @Transactional
    public boolean sendResetPasswordCode(String phone) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",phone);
        User user = userService.getOne(queryWrapper);
        if(user==null){
            throw  new BusinessException("该手机号未注册，请先注册！");
        }
        return sendIdentifyingCode(phone, RedisKeyPrefix.RESET_PASSWORD_CODE_PREFIX,mesageConfig.getTemplateCode_resetPassword(),4);
    }

    /**
     * 发送短信验证码
     * @param phone 电话号码
     * @param templateCode 短息模板
     * @param redisKeyPrefix redis的key的前缀
     * @return
     * @throws ClientException
     */
    private boolean sendIdentifyingCode(String phone, String redisKeyPrefix, String templateCode,int size){

        try {
            String verificationCode= NumberUtils.generateRandomNum(size);
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
            String key=redisKeyPrefix+phone;
            log.debug("====把验证码保存到redis,有效时间为5分钟====");
            log.debug("存储的键为：{}，值为：{}",key,verificationCode);
            strRedisTemplate.opsForValue().set(key,verificationCode,5, TimeUnit.MINUTES);
            String data = response.getData();
            JSONObject jsonObject = JSONObject.parseObject(data);
            //短信发送状态
            String code = jsonObject.getString("Code");
            String message=jsonObject.getString("Message");
            if("OK".equals(code)){
                //短信如果发送成功，程序到此结束，不会继续往下执行
                return true;
            }else if("isv.MOBILE_NUMBER_ILLEGAL".equals(code)){
                log.info(phone+"无效号码");
               throw new BusinessException(phone+"无效号码");
            }else if("isp.OUT_OF_SERVICE".equals(code)){
                log.info(phone+"业务停机");
                throw new BusinessException(phone+"业务停机");
            }else{
                log.info("短信发送失败，原因："+message);
                throw new BusinessException("短信发送服务繁忙，请稍后再试！");
            }
        }catch (ServerException e){
            log.error("ErrorCode=" + e.getErrCode());
            log.error("ErrorMessage=" + e.getErrMsg());
            log.error("ResponseId=" + e.getRequestId());
        }catch (ClientException e){
            log.error("ErrorCode=" + e.getErrCode());
            log.error("ErrorMessage=" + e.getErrMsg());
        }
        //运行到后面说明发送失败
        log.info("短信发送失败！");
        return false;
    }


}
