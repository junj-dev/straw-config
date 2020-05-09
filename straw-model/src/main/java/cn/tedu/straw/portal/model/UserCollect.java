package cn.tedu.straw.portal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_collect")
@ApiModel(value="UserCollect对象", description="")
@AllArgsConstructor
@NoArgsConstructor
public class UserCollect implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "问题id")
    @TableField("question_id")
    private Integer questionId;

    @ApiModelProperty(value = "收藏时间")
    @TableField("createtime")
    private Date createtime;

    public UserCollect(Integer userId, Integer questionId, Date createtime) {
        this.userId = userId;
        this.questionId = questionId;
        this.createtime = createtime;
    }
}
