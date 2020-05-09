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
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@TableName("question")
@Slf4j
@ApiModel(value="Question对象", description="")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "问题的标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "提问内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "提问者昵称")
    @TableField("user_nick_name")
    private String userNickName;

    @ApiModelProperty(value = "提问者id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "创建时间")
    @TableField("createtime")
    private Date createtime;

    @ApiModelProperty(value = "修改时间")
    @TableField("modifytime")
    private Date modifytime;

    @ApiModelProperty(value = "删除状态，true删除，false不删除")
    @TableField("delete_status")
    private Boolean deleteStatus;

    @ApiModelProperty(value = "状态，0-》未回答，1-》待解决，2-》已解决")
    @TableField("status")
    private  Integer status;

    @ApiModelProperty(value = "浏览量")
    @TableField("page_views")
    private Integer pageViews;

    @ApiModelProperty(value = "该问题是否公开，所有学生可见，0-》否，1-》是")
    @TableField("public_status")
    private Integer publicStatus;

    @ApiModelProperty(value = "提问时间")
    @TableField(exist = false)
    private String distanceTime;

    @ApiModelProperty(value = "标签列表")
    @TableField(exist = false)
    List<Tag> tags=new ArrayList<>();

    @ApiModelProperty(value = "答案")
    @TableField(exist =false )
    List<Answer> answers=new ArrayList<>();

    @ApiModelProperty(value = "回答数量")
    @TableField(exist = false)
    private  Integer answerCount;

    public Question(Integer id, String title, String content, Date modifytime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.modifytime = modifytime;
    }

    public Question(String title, String content, String userNickName, Integer userId, Date createtime, Integer status, Integer pageViews, Integer publicStatus) {
        this.title = title;
        this.content = content;
        this.userNickName = userNickName;
        this.userId = userId;
        this.createtime = createtime;
        this.status = status;
        this.pageViews = pageViews;
        this.publicStatus = publicStatus;

    }

    public  String getCreatetimestr(){
        if(createtime!=null){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(createtime);
        }
        return "未知时间";
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

    public int getAnswersize(){
        return answers.size();
    }
}
