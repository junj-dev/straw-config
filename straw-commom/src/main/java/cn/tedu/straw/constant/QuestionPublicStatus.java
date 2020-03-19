package cn.tedu.straw.constant;

/**
 * 问题是否开放枚举类
 */
public enum QuestionPublicStatus {
    /**
     * 公开
     */
    PUBLIC(1),
    /**
     * 私密
     */
    PRIVATE(0);
    private Integer status;

    private QuestionPublicStatus(Integer status){
        this.status=status;
    }

    public Integer getStatus() {
        return status;
    }
}
