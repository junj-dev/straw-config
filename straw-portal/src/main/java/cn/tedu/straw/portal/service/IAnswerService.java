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
     * @return 执行成功返回true,执行失败返回false
     */
    void deleteAnswerById(Integer answerId);

    /**
     * 采纳答案
     * @param answerId
     * @param questionId
     * @return 执行成功返回true,执行失败返回false
     */
    void acceptAnswer(Integer answerId, Integer questionId);
}
