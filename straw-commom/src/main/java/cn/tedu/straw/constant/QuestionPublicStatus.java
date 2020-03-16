package cn.tedu.straw.constant;

/**
 * 问题是否开放枚举类
 */
public enum QuestionPublicStatus {
    /**
     * 公开
     */
    PUBLIC("1"),
    /**
     * 私密
     */
    PRIVATE("0");
    private String status;

    private QuestionPublicStatus(String status){
        this.status=status;
    }

    public String getStatus() {
        return status;
    }
}
