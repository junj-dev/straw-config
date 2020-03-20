package cn.tedu.straw.portal.model;

import cn.tedu.straw.utils.DateUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@TableName("question")
@Slf4j
@ApiModel(value="Question对象", description="")
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

    public  Question(){

    }
    public Question( String title, String content, String userNickName, Integer userId, Date createtime, Integer status, Integer pageViews, Integer publicStatus) {
        this.title = title;
        this.content = content;
        this.userNickName = userNickName;
        this.userId = userId;
        this.createtime = createtime;
        this.status = status;
        this.pageViews = pageViews;
        this.publicStatus = publicStatus;

    }

    public int getAnswersize(){
        return answers.size();
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Integer getPageViews() {
        return pageViews;
    }

    public void setPageViews(Integer pageViews) {
        this.pageViews = pageViews;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public void setDistanceTime(String distanceTime) {
        this.distanceTime = distanceTime;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Integer getPublicStatus() {
        return publicStatus;
    }

    public void setPublicStatus(Integer publicStatus) {
        this.publicStatus = publicStatus;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", userNickName='" + userNickName + '\'' +
                ", userId=" + userId +
                ", createtime=" + createtime +
                ", status=" + status +
                ", pageViews=" + pageViews +
                ", publicStatus=" + publicStatus +
                ", distanceTime='" + distanceTime + '\'' +
                ", tags=" + tags +
                ", answers=" + answers +
                '}';
    }
}
