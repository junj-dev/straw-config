package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.model.Answer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-03-09
 */
public interface IAnswerService extends IService<Answer> {

    /**
     * 删除回答
     * @param answerId
     */
    void deleteAnswerById(Integer answerId);

    /**
     * 采纳答案
     * @param answerId
     * @param questionId
     */
    void acceptAnswer(Integer answerId, Integer questionId);
}
