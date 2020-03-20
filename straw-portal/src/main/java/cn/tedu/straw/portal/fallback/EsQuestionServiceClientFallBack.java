package cn.tedu.straw.portal.fallback;

import cn.tedu.straw.commom.CommonPage;
import cn.tedu.straw.commom.StrawResult;
import cn.tedu.straw.portal.api.EsQuestionServiceApi;
import cn.tedu.straw.portal.model.EsQuestion;

/**
 * @Description: java类作用描述
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/17$ 16:48$
 * @Version: 1.0
 */
public class EsQuestionServiceClientFallBack  implements EsQuestionServiceApi {
    @Override
    public StrawResult<CommonPage<EsQuestion>> search(String keyword, Integer pageNum, Integer pageSize) {
        return StrawResult.<CommonPage<EsQuestion>>builder().build().failed("服务繁忙，请稍后重试！");
    }

    @Override
    public StrawResult<CommonPage<EsQuestion>> searchOpenQuestion(String keyword, Integer pageNum, Integer pageSize, Integer userId, Integer publicStatus) {
        return StrawResult.<CommonPage<EsQuestion>>builder().build().failed("服务繁忙，请稍后重试！");
    }

    @Override
    public boolean saveQuestion(EsQuestion question) {
        return false;
    }
}
