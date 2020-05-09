package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.base.BaseService;
import cn.tedu.straw.portal.domian.vo.MyInfo;
import cn.tedu.straw.portal.mapper.AnswerMapper;
import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.mapper.UserCollectMapper;
import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.model.UserInfoVO;
import cn.tedu.straw.portal.service.IPersonalService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Description: 用户业务类
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/10$ 22:38$
 * @Version: 1.0
 */
@Service
public class PersonalServiceImpl extends BaseService implements IPersonalService {
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private AnswerMapper answerMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserCollectMapper userCollectMapper;
    @Autowired
    private RedisTemplate<String, String> strRedisTemplate;
    private   BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    @Override


    public MyInfo getMyInfo() {
//        //先从redis中取，如果没有再到数据库查询
//        String key="personal:myInfo:"+getUseId();
//        if(strRedisTemplate.hasKey(key)){
//          MyInfo info = strRedisTemplate.opsForValue().get(key);
//
//        }

        MyInfo myInfo=new MyInfo();
        //TODO 金币统计功能暂时未开发,统一设置为0
        myInfo.setGoldCount(0);
        //收藏数量
        QueryWrapper collectCountQuery=new QueryWrapper();
        collectCountQuery.eq("user_id",getUseId());
        Integer collectCount = userCollectMapper.selectCount(collectCountQuery);
        myInfo.setCollectCount(collectCount);
        //获取本用户的提问数量
        QueryWrapper qustionQuery=new QueryWrapper();
        qustionQuery.eq("user_id",getUseId());
        Integer qusetionCount = questionMapper.selectCount(qustionQuery);
        myInfo.setQuestionCount(qusetionCount);
        //获取回答问题的数量
        QueryWrapper answerQuery=new QueryWrapper();
        answerQuery.eq("user_id",getUseId());
        Integer answerCount = answerMapper.selectCount(answerQuery);
        myInfo.setAnswerCount(answerCount);
        //任务的数量(未回答和未解决的问题)
        Integer taskCount = questionMapper.countTaskByUserId(getUseId());
        myInfo.setTaskCount(taskCount);
        return myInfo;
    }

    @Override
    public UserInfoVO getUserInfo() {
        UserInfoVO user = userMapper.findById(getUseId());

        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetMyInfo(UserInfoVO userInfo) {
        User user = userMapper.selectById(getUseId());
        user.setNickname(userInfo.getNickname());
        user.setBirthday(userInfo.getBirthday());
        user.setSelfIntroduction(userInfo.getSelfIntroduction());
        user.setSex(userInfo.getSex());
        userMapper.updateById(user);
        //修改提问的nickName
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",getUseId());
        Question question=new Question();
        question.setUserNickName(userInfo.getNickname());
        questionMapper.update(question,queryWrapper);

        return true;
    }

    @Override
    public boolean resetpasswd(String newpasswd) {
        User user = userMapper.selectById(getUseId());
        user.setPassword(passwordEncoder.encode(newpasswd));
        return userMapper.updateById(user)==1;
    }

    @Override
    public boolean checkPasswd(String oldpasswd) {
        User user = userMapper.selectById(getUseId());
        if(passwordEncoder.matches(oldpasswd,user.getPassword())){
            return true;
        }
        return false;
    }
}
