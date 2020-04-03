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
 * @since 2020-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("classroom")
@ApiModel(value="Classroom对象", description="")
public class Classroom implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "班级名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "邀请码")
    @TableField("invite_code")
    private String inviteCode;

    @ApiModelProperty(value = "班级是否可用")
    @TableField("enabled")
    private  Boolean enabled;

    @TableField("createtime")
    private Date createtime;

    @TableField("modifytime")
    private Date modifytime;


}
