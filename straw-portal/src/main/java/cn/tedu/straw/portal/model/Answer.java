package cn.tedu.straw.portal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("answer")
@ApiModel(value="Answer对象", description="")
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "回答内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "点赞数量")
    @TableField("like_count")
    private Integer likeCount;

    @ApiModelProperty(value = "回答问题的用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "对应的问题id")
    @TableField("quest_id")
    private Integer questId;

    @ApiModelProperty(value = "回答时间")
    @TableField("createtime")
    private Date createtime;


}
