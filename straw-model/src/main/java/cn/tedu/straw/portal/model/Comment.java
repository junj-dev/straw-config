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
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("comment")
@ApiModel(value="Comment对象", description="")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Slf4j
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "用户昵称")
    @TableField(exist = false)
    private String nickName;

    @ApiModelProperty(value = "回答id")
    @TableField("answer_id")
    private Integer answerId;

    @ApiModelProperty(value = "评论内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "创建时间")
    @TableField("createtime")
    private Date createtime;


    public String getDistanceTime() {
        String distanceTime="";
        try {
            distanceTime= DateUtils.getDistanceTime(createtime);
        }catch (Exception e){
            log.error(e.getMessage());

        }
        return distanceTime;

    }

    public Comment(Integer userId, Integer answerId, String content, Date createtime) {
        this.userId = userId;
        this.answerId = answerId;
        this.content = content;
        this.createtime = createtime;
    }

    public Comment(Integer id, String content) {
        this.id = id;
        this.content = content;
    }
}

