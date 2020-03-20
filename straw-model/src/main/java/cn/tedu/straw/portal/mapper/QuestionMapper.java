package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
     * 查询问题详情
     * @param userId 用户id
     * @param publicStatus 是否公开,0->否，1-》是
     * @return
     */
    List<Question> selectQuestionWithTags(Integer userId,Integer publicStatus);


}
