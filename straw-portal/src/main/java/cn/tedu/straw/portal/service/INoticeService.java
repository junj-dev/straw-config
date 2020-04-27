package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.model.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-04-27
 */
public interface INoticeService extends IService<Notice> {

    List<Notice> getMyNoticeList();
}
