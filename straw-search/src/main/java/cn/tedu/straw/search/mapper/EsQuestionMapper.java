package cn.tedu.straw.search.mapper;

import cn.tedu.straw.portal.model.EsQuestion;
import cn.tedu.straw.portal.model.Tag;

import java.util.List;

/**
 *
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/17$ 10:46$
 * @Version: 1.0
 */
public interface EsQuestionMapper {




    List<EsQuestion> selectQuestionWithTags();


}
