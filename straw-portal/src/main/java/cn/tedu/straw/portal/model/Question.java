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
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("question")
@ApiModel(value="Question对象", description="")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "问题的标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "提问内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "提问者用户名")
    @TableField("qust_username")
    private String qustUsername;

    @ApiModelProperty(value = "提问者id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "创建时间")
    @TableField("createtime")
    private Date createtime;

    @ApiModelProperty(value = "标签id")
    @TableField("tag_id")
    private Integer tagId;

    @ApiModelProperty(value = "状态，0-》未回答，1-》待解决，2-》已解决")
    @TableField("status")
    private  String status;


    @ApiModelProperty(value = "提问时间")
    @TableField(exist = false)
    private String distanceTime;

    @ApiModelProperty(value = "标签列表")
    @TableField(exist = false)
    List<Tag> tags=new ArrayList<>();

}
