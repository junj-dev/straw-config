package cn.tedu.straw.portal.service;

import cn.tedu.straw.commom.StrawResult;
import cn.tedu.straw.portal.domian.param.QuestionParam;
import cn.tedu.straw.portal.model.Question;
import com.baomidou.mybatisplus.core.metadata.IPage;
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

    StrawResult uploadImg(MultipartFile[] files, HttpServletRequest request);

    boolean create(QuestionParam param);

    Question getQuestionDetailById(Long id);

    Boolean answer(Long id, String content);
}
