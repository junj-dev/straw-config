package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.model.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  评论业务类
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-04-25
 */
public interface ICommentService extends IService<Comment> {
   boolean create(Integer answerId, String content, Integer questionId);

   boolean update(Integer commentId, String content);
}
