package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.base.BaseServiceImpl;
import cn.tedu.straw.portal.mapper.UserCollectMapper;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.UserCollect;
import cn.tedu.straw.portal.service.IUserCollectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-05-07
 */
@Service
public class UserCollectServiceImpl extends BaseServiceImpl<UserCollectMapper, UserCollect> implements IUserCollectService {

    @Resource
    private UserCollectMapper userCollectMapper;

    /**
     * 分页查询收藏的问题
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Question> selectPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Question> questions = userCollectMapper.findUserCollectQuestionByUserId(getUseId());
        PageInfo<Question> pageInfo=new PageInfo<>(questions);
        return pageInfo;
    }
}
