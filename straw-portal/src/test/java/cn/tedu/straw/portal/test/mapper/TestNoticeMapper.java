package cn.tedu.straw.portal.test.mapper;

import cn.tedu.straw.portal.StrawPortalApplication;
import cn.tedu.straw.portal.mapper.NoticeMapper;
import cn.tedu.straw.portal.model.Notice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/27$ 17:05$
 * @Version: 1.0
 */
@SpringBootTest(classes = StrawPortalApplication.class)
@RunWith(SpringRunner.class)
public class TestNoticeMapper {
    @Resource
    private NoticeMapper noticeMapper;

    /**
     * @see  cn.tedu.straw.portal.mapper.NoticeMapper#findNoReadNoticeByUserId(Integer)
     */
    @Test
    public void findNoticeByUserId(){
        List<Notice> notices = noticeMapper.findNoReadNoticeByUserId(9);
        notices.forEach(System.out::println);
    }
}
