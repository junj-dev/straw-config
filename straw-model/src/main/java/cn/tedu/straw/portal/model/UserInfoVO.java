package cn.tedu.straw.portal.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: java类作用描述
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/16$ 0:45$
 * @Version: 1.0
 */
@Slf4j
public class UserInfoVO implements Serializable {
    public static final  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    private static final long serialVersionUID = -1731096117313043719L;
    @NotEmpty(message = "用户名不能为空")
    private String nickname;
    @NotEmpty(message = "性别不能为空")
    private String sex;
    private Date  birthday;
    private String selfIntroduction;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setBirthdayStr(String birthdayStr) {
        try {
            if(!StringUtils.isEmpty(birthdayStr)){
                this.birthday=sdf.parse(birthdayStr);
            }

        }catch (Exception e){
            log.error("传入的生日格式不正确，应该为：yyyy-MM-dd");
            e.printStackTrace();
        }


    }

    public String getBirthdayStr() {

        if(birthday!=null){
            return sdf.format(birthday);
        }
        return "";
    }



    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }
}
