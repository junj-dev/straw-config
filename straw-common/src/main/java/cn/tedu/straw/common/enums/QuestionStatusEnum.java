package cn.tedu.straw.common.enums;

/**
 * 问题状态枚举类
 */
public enum QuestionStatusEnum {

    /**
     * "状态，0-》未回答，1-》待解决，2-》已解决"
     */
    UN_ANSWER(0),//未回答
    UN_SOLVE(1),//待解决
    SOLVED(2);//已解决


    public Integer getStatus() {
        return status;
    }

    private  Integer status;
    private  QuestionStatusEnum(Integer status){
        this.status=status;
    }

}
