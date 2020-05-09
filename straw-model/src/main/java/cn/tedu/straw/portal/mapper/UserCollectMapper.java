package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.UserCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-05-07
 */
public interface UserCollectMapper extends BaseMapper<UserCollect> {

    List<Question> findUserCollectQuestionByUserId(Integer userId);

}
