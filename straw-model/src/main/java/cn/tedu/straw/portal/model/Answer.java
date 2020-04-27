package cn.tedu.straw.portal.model;


import cn.tedu.straw.common.util.DateUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("answer")
@ApiModel(value="Answer对象", description="")
@Slf4j
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "回答内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "点赞数量")
    @TableField("like_count")
    private Integer likeCount;

    @ApiModelProperty(value = "回答问题的用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "回答者昵称")
    @TableField("user_nick_name")
    private  String userNickName;

    @ApiModelProperty(value = "对应的问题id")
    @TableField("quest_id")
    private Integer questId;

    @ApiModelProperty(value = "回答时间")
    @TableField("createtime")
    private Date createtime;

    @ApiModelProperty(value = "回答时间")
    @TableField(exist = false)
    private String distanceTime;

    @ApiModelProperty(value = "是否采纳该回答")
    @TableField("accept_status")
    private boolean acceptStatus;

    @ApiModelProperty(value = "评论列表")
    @TableField(exist = false)
    private List<Comment> commentList=new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public Integer getQuestId() {
        return questId;
    }

    public void setQuestId(Integer questId) {
        this.questId = questId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public void setDistanceTime(String distanceTime) {
        this.distanceTime = distanceTime;
    }

    public boolean isAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(boolean acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", likeCount=" + likeCount +
                ", userId=" + userId +
                ", userNickName='" + userNickName + '\'' +
                ", questId=" + questId +
                ", createtime=" + createtime +
                ", distanceTime='" + distanceTime + '\'' +
                ", acceptStatus=" + acceptStatus +
                ", commentList=" + commentList +
                '}';
    }
}
