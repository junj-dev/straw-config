package cn.tedu.straw.portal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
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
 * @since 2020-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_question")
@ApiModel(value="UserQuestion对象", description="")
public class UserQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer UserId;

    @TableField("question_id")
    private Integer questionId;

    @TableField("createtime")
    private Date createtime;

    public UserQuestion() {

    }

    public UserQuestion(Integer UserId, Integer questionId, Date createtime) {
        this.UserId = UserId;
        this.questionId = questionId;
        this.createtime = createtime;
    }
}
