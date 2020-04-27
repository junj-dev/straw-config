package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.model.Question;

import java.util.List;

/**
 * @Description: 问题推荐业务接口
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/21$ 11:29$
 * @Version: 1.0
 */
public interface IRecommendQuestionService {
    List<Question> getHotspotQuestion();

    List<Question> getSimilarQuestion(Integer questionId);
}
