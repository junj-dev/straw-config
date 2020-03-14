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
     * 查询所有带有标签的问题
     * @return
     */
    List<Question> selectQuestionWithTags();

}
