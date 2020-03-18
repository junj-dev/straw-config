package cn.tedu.straw.search.mapper;

import cn.tedu.straw.search.model.EsAnswer;
import cn.tedu.straw.search.model.EsQuestion;
import cn.tedu.straw.search.model.EsTag;

import java.util.List;

/**
 *
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/17$ 10:46$
 * @Version: 1.0
 */
public interface EsQuestionMapper {


    List<EsTag> selectTagsByQuestionId(Long questionId);

    List<EsAnswer> selectAnswersByQuestionId(Long questionId);

    List<EsQuestion> selectQuestionWithTagsWithAnswer();


}
