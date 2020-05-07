package cn.tedu.straw.portal.model;


import cn.tedu.straw.common.util.DateUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-03-09
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("answer")
@ApiModel(value="Answer对象", description="")
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "回答内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "点赞数量")
    @TableField("like_count")
    private Integer likeCount;

    @ApiModelProperty(value = "回答问题的用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "回答者昵称")
    @TableField("user_nick_name")
    private  String userNickName;

    @ApiModelProperty(value = "对应的问题id")
    @TableField("quest_id")
    private Integer questId;

    @ApiModelProperty(value = "回答时间")
    @TableField("createtime")
    private Date createtime;

    @ApiModelProperty(value = "回答时间")
    @TableField(exist = false)
    private String distanceTime;

    @ApiModelProperty(value = "是否采纳该回答")
    @TableField("accept_status")
    private boolean acceptStatus;

    @ApiModelProperty(value = "评论列表")
    @TableField(exist = false)
    private List<Comment> commentList=new ArrayList<>();

    public String getDistanceTime() {
        String distanceTime="";
        try {
            distanceTime= DateUtils.getDistanceTime(createtime);
        }catch (Exception e){
            log.error(e.getMessage());

        }
        return distanceTime;
    }

    public Answer(String content, Integer likeCount, Integer userId, String userNickName, Integer questId, Date createtime) {
        this.content = content;
        this.likeCount = likeCount;
        this.userId = userId;
        this.userNickName = userNickName;
        this.questId = questId;
        this.createtime = createtime;
    }
}
