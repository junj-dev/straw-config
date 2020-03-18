package cn.tedu.straw.search.service;

import cn.tedu.straw.search.model.EsQuestion;
import org.springframework.data.domain.Page;

/**
 * @Description: java类作用描述
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/17$ 14:50$
 * @Version: 1.0
 */
public interface IEsQuestionService {
    int importAllQuestionFromDB();

    Page<EsQuestion> search(String keyword, Integer pageNum, Integer pageSize);
}
