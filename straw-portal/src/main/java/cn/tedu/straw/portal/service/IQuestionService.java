package cn.tedu.straw.portal.service;

import cn.tedu.straw.common.CommonPage;
import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.portal.domian.param.QuestionParam;
import cn.tedu.straw.portal.model.EsQuestion;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.QuestionQueryParam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-03-09
 */
public interface IQuestionService extends IService<Question> {

    PageInfo<Question> selectPage(Integer pageNum, Integer pageSize);

    PageInfo<Question> selectPersonalQuestion(Integer pageNum, Integer pageSize);

    PageInfo<Question> selectPage(Integer tagId,Integer pageNum, Integer pageSize);

    StrawResult uploadImg(MultipartFile[] files, HttpServletRequest request);

    boolean create(QuestionParam param);

    Question getQuestionDetailById(Integer id);

    Boolean answer(Integer id, String content);

    StrawResult<CommonPage<EsQuestion>> search(String keyword, Integer pageNum, Integer pageSize);

    PageInfo<Question> findAllQuestion(Integer pageNum, Integer pageSize);

    PageInfo<Question> findQuestionByCondition(QuestionQueryParam queryParam);

    boolean setQuestionPublic(Integer id);
    boolean setQuestionPublic(Integer[] ids);

    boolean cancelQuestionPublic(Integer id);

    PageInfo<Question> findMyUnAnwerQuestion(Integer pageNum, Integer pageSize);

    PageInfo<Question> findMyUnSolveQuestion(Integer pageNum, Integer pageSize);

    PageInfo<Question> findMySolvedQuestion(Integer pageNum, Integer pageSize);

    boolean setQuestionSolved(Integer id);
    boolean setQuestionSolved(Integer[] id);

    boolean transferToTeacher(Integer[] teacherIds,Integer[] questionIds);
}
