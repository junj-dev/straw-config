package cn.tedu.straw.search.service;

import cn.tedu.straw.common.CommonPage;
import cn.tedu.straw.portal.model.EsQuestion;
import org.springframework.data.domain.Page;

/**
 * @Description: java类作用描述
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/17$ 14:50$
 * @Version: 1.0
 */
public interface IEsQuestionService {
    int importAllQuestionFromDB();

    CommonPage<EsQuestion> search(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 只查询本人的问题和公开的问题
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    CommonPage<EsQuestion> searchByUserIdAndPublicStatus(String keyword, Integer pageNum, Integer pageSize, Integer userId, Integer publicStatus);

    boolean insert(EsQuestion question);

    boolean deleteQuestionById(Integer id);
}
