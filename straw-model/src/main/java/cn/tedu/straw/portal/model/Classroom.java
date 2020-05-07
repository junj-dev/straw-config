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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-03-31
 */
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

//获取邀请码的截至日期
    public String getDeadline(){
        if(modifytime!=null){
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(modifytime);
            calendar.add(Calendar.DATE,7);
            Date deadline = calendar.getTime();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(deadline);
        }

        return "";
    }
}
