package cn.tedu.straw.portal.model;

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
    @Field(type = FieldType.Keyword)
    private Long id;


    @Field(analyzer = "ik_max_word",type = FieldType.Text,searchAnalyzer="ik_max_word")
    private String title;

    @Field(analyzer = "ik_max_word",type = FieldType.Text,searchAnalyzer="ik_max_word")
    private String content;

    @Field(type = FieldType.Keyword)
    private String userNickName;

    private Integer userId;

    private Date createtime;

    private  Integer status;

    private Integer pageViews;

    private Integer publicStatus;

    private String distanceTime;

    @Field(type = FieldType.Keyword)
    List<String> tags=new ArrayList<>();


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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Integer getPublicStatus() {
        return publicStatus;
    }

    public void setPublicStatus(Integer publicStatus) {
        this.publicStatus = publicStatus;
    }


}
