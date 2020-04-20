package cn.tedu.straw.portal.model;


import cn.tedu.straw.common.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
@Document(indexName = "straw",type = "question")
@Slf4j
public class EsQuestion implements Serializable {


    private static final long serialVersionUID = -2551847857874479192L;
    @Id
    @Field(type = FieldType.Keyword)
    private Integer id;


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

    private String createtimeStr;

    @Field(type = FieldType.Keyword)
    List<String> tagNames=new ArrayList<>();
    /**
     * 问题的标签，为了搜索的时候页面展示用
     */
    List<Tag> tags=new ArrayList<>();

    public  EsQuestion(){

    }
    public EsQuestion(Integer id, String title, String content, String userNickName, Integer userId, Date createtime, Integer status, Integer pageViews, Integer publicStatus, List<String> tagNames,List<Tag> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userNickName = userNickName;
        this.userId = userId;
        this.createtime = createtime;
        this.status = status;
        this.pageViews = pageViews;
        this.publicStatus = publicStatus;
        this.tagNames = tagNames;
        this.tags=tags;
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

    public String getCreatetimeStr() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
        if(createtime!=null){
            return  sdf.format(createtime);
        }
        return "";
    }



    public Integer getPublicStatus() {
        return publicStatus;
    }

    public void setPublicStatus(Integer publicStatus) {
        this.publicStatus = publicStatus;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}
