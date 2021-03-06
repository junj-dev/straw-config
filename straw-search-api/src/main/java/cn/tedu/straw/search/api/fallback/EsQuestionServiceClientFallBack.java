package cn.tedu.straw.search.api.fallback;

import cn.tedu.straw.common.CommonPage;
import cn.tedu.straw.common.R;
import cn.tedu.straw.portal.model.EsQuestion;
import cn.tedu.straw.search.api.EsQuestionServiceApi;

/**
 * @Description: 服务降级类
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/17$ 16:48$
 * @Version: 1.0
 */
public class EsQuestionServiceClientFallBack  implements EsQuestionServiceApi {
    @Override
    public R importAllQuestionFromDB() {
        return new R().failed("服务繁忙，请稍后重试！");
    }

    @Override
    public R<CommonPage<EsQuestion>> search(String keyword, Integer pageNum, Integer pageSize) {
        return new R().failed("服务繁忙，请稍后重试！");
    }

    @Override
    public R<CommonPage<EsQuestion>> searchOpenQuestion(String keyword, Integer pageNum, Integer pageSize, Integer userId, Integer publicStatus) {
        return new R().failed("服务繁忙，请稍后重试！");
    }

    @Override
    public boolean saveQuestion(EsQuestion question) {
        return false;
    }

    @Override
    public boolean deleteQuestion(Integer id) {
        return false;
    }
}
