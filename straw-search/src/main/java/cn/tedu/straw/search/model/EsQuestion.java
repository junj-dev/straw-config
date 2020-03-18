package cn.tedu.straw.search.model;

import cn.tedu.straw.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
@Document(indexName = "straw",type = "question")
@Slf4j
public class EsQuestion implements Serializable {


    private static final long serialVersionUID = -2551847857874479192L;
    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String title;

    @Field(type = FieldType.Keyword)
    private String content;

    private String userNickName;

    private Integer userId;

    private Date createtime;

    private  String status;

    private Integer pageViews;

    private String publicStatus;

    private String distanceTime;

    @Field(type = FieldType.Nested)
    List<EsTag> tags=new ArrayList<>();

    @Field(type = FieldType.Nested)
    List<EsAnswer> answers=new ArrayList<>();



    public int getAnswersize(){
        return answers.size();
    }

    public List<EsAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<EsAnswer> answers) {
        this.answers = answers;
    }

    public Integer getPageViews() {
        return pageViews;
    }

    public void setPageViews(Integer pageViews) {
        this.pageViews = pageViews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public List<EsTag> getTags() {
        return tags;
    }

    public void setTags(List<EsTag> tags) {
        this.tags = tags;
    }

    public String getPublicStatus() {
        return publicStatus;
    }

    public void setPublicStatus(String publicStatus) {
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
                ", status='" + status + '\'' +
                ", pageViews=" + pageViews +
                ", publicStatus='" + publicStatus + '\'' +
                ", distanceTime='" + distanceTime + '\'' +
                ", tags=" + tags +
                ", answers=" + answers +
                '}';
    }
}
