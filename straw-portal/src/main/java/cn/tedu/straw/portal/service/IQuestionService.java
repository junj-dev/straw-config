package cn.tedu.straw.portal.service;

import cn.tedu.straw.common.CommonPage;
import cn.tedu.straw.common.R;
import cn.tedu.straw.portal.domian.param.QuestionParam;
import cn.tedu.straw.portal.domian.param.QuestionUpdateParam;
import cn.tedu.straw.portal.domian.vo.QuestionVO;
import cn.tedu.straw.portal.model.Answer;
import cn.tedu.straw.portal.model.EsQuestion;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.QuestionQueryParam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  问题服务实现类
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-03-09
 */
public interface IQuestionService extends IService<Question> {

    PageInfo<Question> listQuestions(Integer pageNum, Integer pageSize);

    PageInfo<Question> listPersonalQuestions(Integer pageNum, Integer pageSize);

    PageInfo<Question> listQuestions(Integer tagId,Integer pageNum, Integer pageSize);

    List<String> uploadImg(MultipartFile[] files, HttpServletRequest request);

    void saveQuestion(QuestionParam param);

    Question getQuestionDetailById(Integer id);

    void saveAnswer(Integer id, String content);

    R<CommonPage<EsQuestion>> search(String keyword, Integer pageNum, Integer pageSize);

    PageInfo<Question> getQuestionByCondition(QuestionQueryParam queryParam);

    PageInfo<Question> getMyUnAnwerQuestion(Integer pageNum, Integer pageSize);

    PageInfo<Question> getMyUnSolveQuestion(Integer pageNum, Integer pageSize);

    PageInfo<Question> getMySolvedQuestion(Integer pageNum, Integer pageSize);

    void transferToTeacher(Integer[] teacherIds,Integer[] questionIds);

    QuestionVO getQuestionParamById(Integer id);

    void updateQuestion(QuestionUpdateParam question);

    Boolean checkCollectStatus(Integer id);

    List<Answer> getQuestionAnswerById(Integer questionId);

    void updateQuestion(Integer[] ids, Question question);
}
