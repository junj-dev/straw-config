package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-04-25
 */
public interface CommentMapper extends BaseMapper<Comment> {

    List<Comment> findByAnswerId(Integer answerId);

}
