package cn.tedu.straw.portal.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 查询问题的查询参数
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/20$ 11:29$
 * @Version: 1.0
 */
@Slf4j
public class QuestionQueryParam {

    private  Integer publicStatus;
    private  Integer tagId;
    private  Integer pageNum;
    private  Integer pageSize;
    private  String questUserName;
    private  String startDateStr;
    private  String endDateStr;
    private  Date   startDate;
    private  Date   endDate;
    private  Integer answerStatus;


    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public String getQuestUserName() {
        return questUserName;
    }

    public void setQuestUserName(String questUserName) {
        this.questUserName = questUserName;
    }

    public Date getStartDate() {
        return getDate(startDateStr);


    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return getDate(endDateStr);
    }

    private Date getDate(String endDateStr) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(!StringUtils.isEmpty(endDateStr)){
                return sdf.parse(endDateStr);
            }
        }catch (Exception e){
            log.error("传入的时间格式不正确,格式应该为yyyy-MM-dd");
            e.printStackTrace();
        }
        return null;
    }


    public Integer getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(Integer answerStatus) {
        this.answerStatus = answerStatus;
    }

    public Integer getPublicStatus() {
        return publicStatus;
    }

    public void setPublicStatus(Integer publicStatus) {
        this.publicStatus = publicStatus;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


    @Override
    public String toString() {
        return "QuestionQueryParam{" +
                "publicStatus=" + publicStatus +
                ", tagId=" + tagId +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", questUserName='" + questUserName + '\'' +
                ", startDateStr='" + startDateStr + '\'' +
                ", endDateStr='" + endDateStr + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", answerStatus=" + answerStatus +
                '}';
    }
}
