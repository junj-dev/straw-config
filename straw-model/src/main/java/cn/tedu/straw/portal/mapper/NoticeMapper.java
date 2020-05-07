package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-04-27
 */
public interface NoticeMapper extends BaseMapper<Notice> {

    List<Notice>  findNoReadNoticeByUserId(Integer userId);
    List<Notice>  findAllNoticeByUserId(Integer userId);
}
