package cn.tedu.straw.portal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-04-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("notice")
@ApiModel(value="Notice对象", description="")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "消息类型，0-》回复评论，1-》回复问题")
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


}
