package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.UserCollect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-05-07
 */
public interface IUserCollectService extends IService<UserCollect> {

    PageInfo<Question> selectPage(Integer pageNum, Integer pageSize);
}
