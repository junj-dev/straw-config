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
   void create(Integer answerId, String content, Integer questionId);

   void update(Integer commentId, String content);
}
