package cn.tedu.straw.search.api.fallback;

import cn.tedu.straw.common.CommonPage;
import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.portal.model.EsQuestion;
import cn.tedu.straw.search.api.EsQuestionServiceApi;

/**
 * @Description: java类作用描述
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/17$ 16:48$
 * @Version: 1.0
 */
public class EsQuestionServiceClientFallBack  implements EsQuestionServiceApi {
    @Override
    public StrawResult importAllQuestionFromDB() {
        return new StrawResult().failed("服务繁忙，请稍后重试！");
    }

    @Override
    public StrawResult<CommonPage<EsQuestion>> search(String keyword, Integer pageNum, Integer pageSize) {
        return new StrawResult().failed("服务繁忙，请稍后重试！");
    }

    @Override
    public StrawResult<CommonPage<EsQuestion>> searchOpenQuestion(String keyword, Integer pageNum, Integer pageSize, Integer userId, Integer publicStatus) {
        return new StrawResult().failed("服务繁忙，请稍后重试！");
    }

    @Override
    public boolean saveQuestion(EsQuestion question) {
        return false;
    }
}
