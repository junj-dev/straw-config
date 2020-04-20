package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.QuestionQueryParam;
import cn.tedu.straw.portal.model.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-03-09
 */
public interface QuestionMapper extends BaseMapper<Question> {



    /**
     * 查找本人发表的所有问题
     * @param userId
     * @return
     */
    List<Question> findQuestionByUserId(Integer userId);

    /**
     * 查找本人提出的或者公开的，并且为某个标签的问题
     * 例如：查找本人或者公开的且标签为‘java基础’的问题
     * @param userId
     * @param tagId
     * @return
     */
    List<Question> findQuestionByUserIdAndTagId(Integer userId,Integer tagId);

    /**
     * 通过标签查找问题，该方法只能拥有老师的角色使用，可以查看所有学生提出的问题
     * @param tagId
     * @return
     */
    List<Question> findQuestionByTagId(Integer tagId);

    /**
     * 老师或者管理员角色，查看所有人提出的问题
     * @return
     */
    List<Question> findAllQuestion();

    /**
     * 学生角色在首页点击‘所有’标签的时候，查询该用户的所有问题或公开的问题
     * @param userId
     * @param publicStatus
     * @return
     */
    List<Question> findQuestionByUserIdOrPublicStatus(Integer userId,Integer publicStatus);


    /**
     * 条件查询
     * @param condition
     * @return
     */
    List<Question> findQuestionByCondition(QuestionQueryParam condition);

    /**
     * 根据多个tag和提问的回答状态查询出提问列表
     * @param tagIds
     * @param status
     * @return
     */
    List<Question> findQuestionByTagIdsAndStatus(List<Integer> tagIds,Integer status);


    List<Question> findQuestionByUserIdAndStatus(Integer userId,Integer status);

    /**
     * 计算老师回答问题的任务数量
     * @param userId
     * @return
     */
    Integer countTaskByUserId(Integer userId);

}
