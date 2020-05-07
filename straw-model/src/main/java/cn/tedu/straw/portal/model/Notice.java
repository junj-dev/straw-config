package cn.tedu.straw.portal.model;

import cn.tedu.straw.common.util.DateUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-04-27
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("notice")
@Slf4j
@ApiModel(value="Notice对象", description="")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "消息类型，0-》评论问题的回答，1-》回答某人的提问，2-》评论某人的提问，3-》向某人提问")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "问题id")
    @TableField("question_id")
    private Integer questionId;

    @ApiModelProperty(value = "问题的标题")
    @TableField(exist = false)
    private  String questionTitle;

    @ApiModelProperty(value = "创建时间")
    @TableField("createtime")
    private Date createtime;



    @ApiModelProperty(value = "收到消息的用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "昵称")
    @TableField(exist = false)
    private  String nickName;

    @ApiModelProperty("回复者id")
    @TableField("reply_user_id")
    private Integer replyUserId;

    @ApiModelProperty("消息是否已查看")
    @TableField("read_status")
    private Boolean readStatus;

//     notice.setReadStatus(false);
//            notice.setReplyUserId(getUseId());
//            notice.setUserId(teacher.getId());
//            notice.setQuestionId(question.getId());
//            notice.setCreatetime(new Date());
//            notice.setType(3);


    public Notice(Integer type, Integer questionId, Date createtime, Integer userId, Integer replyUserId, Boolean readStatus) {
        this.type = type;
        this.questionId = questionId;
        this.createtime = createtime;
        this.userId = userId;
        this.replyUserId = replyUserId;
        this.readStatus = readStatus;
    }

    public String getDistanceTime() {
        String distanceTime="";
        try {
            distanceTime= DateUtils.getDistanceTime(createtime);
        }catch (Exception e){
            log.error(e.getMessage());

        }
        return distanceTime;

    }

}
