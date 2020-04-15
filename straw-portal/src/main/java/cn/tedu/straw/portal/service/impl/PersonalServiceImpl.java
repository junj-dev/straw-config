package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.base.BaseService;
import cn.tedu.straw.portal.domian.vo.MyInfo;
import cn.tedu.straw.portal.mapper.AnswerMapper;
import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.mapper.TeacherQuestionMapper;
import cn.tedu.straw.portal.service.IPersonalService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
    @Autowired
    private RedisTemplate<String, String> strRedisTemplate;
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
}
