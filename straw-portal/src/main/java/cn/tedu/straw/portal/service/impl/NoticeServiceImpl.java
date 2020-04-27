package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.base.BaseServiceImpl;
import cn.tedu.straw.portal.mapper.NoticeMapper;
import cn.tedu.straw.portal.model.Notice;
import cn.tedu.straw.portal.service.INoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  消息服务实现类
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-04-27
 */
@Service
public class NoticeServiceImpl extends BaseServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> getMyNoticeList() {
        List<Notice> noticeList = noticeMapper.findNoticeByUserId(getUseId());
        return noticeList;
    }
}
