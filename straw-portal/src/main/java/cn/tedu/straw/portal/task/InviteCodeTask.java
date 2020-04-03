package cn.tedu.straw.portal.task;

import cn.tedu.straw.common.util.NumberUtils;
import cn.tedu.straw.portal.model.Classroom;
import cn.tedu.straw.portal.service.IClassroomService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description: 邀请码定时任务类
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/31$ 12:59$
 * @Version: 1.0
 */
@Component
public class InviteCodeTask {
    
    @Resource
    private IClassroomService classroomService;


    //每周更新一次邀请码
    @Scheduled(fixedRate = 1000*60*60*24*7)
    //@Scheduled(fixedRate = 1000*20)
    public void updateInviteCode(){

        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("enabled",true);
        List<Classroom> classrooms = classroomService.list(queryWrapper);
        for(Classroom classroom:classrooms){
            String inviteCode= classroom.getName()+"-"+NumberUtils.generateRandomNum(6);
            classroom.setInviteCode(inviteCode);
            classroom.setModifytime(new Date());
            classroomService.updateById(classroom);
        }


    }
}
