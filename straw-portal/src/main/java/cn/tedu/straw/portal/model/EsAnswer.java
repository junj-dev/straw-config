package cn.tedu.straw.portal.model;

import cn.tedu.straw.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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

@Slf4j
public class EsAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Field(type = FieldType.Keyword)
    private String answerContent;

    private Integer likeCount;

    private Integer userId;

    private  String userNickName;

    private Long questId;

    private Date createtime;

    private String distanceTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
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

    public Long getQuestId() {
        return questId;
    }

    public void setQuestId(Long questId) {
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

    public void setDistanceTime(String distanceTime) {
        this.distanceTime = distanceTime;
    }

    @Override
    public String toString() {
        return "EsAnswer{" +
                "id=" + id +
                ", answerContent='" + answerContent + '\'' +
                ", likeCount=" + likeCount +
                ", userId=" + userId +
                ", userNickName='" + userNickName + '\'' +
                ", questId=" + questId +
                ", createtime=" + createtime +
                ", distanceTime='" + distanceTime + '\'' +
                '}';
    }
}
