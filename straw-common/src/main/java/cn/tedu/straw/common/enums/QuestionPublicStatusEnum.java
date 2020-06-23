package cn.tedu.straw.common.enums;

/**
 * 问题是否开放枚举类
 */
public enum QuestionPublicStatusEnum {
    /**
     * 公开
     */
    PUBLIC(1),
    /**
     * 私密
     */
    PRIVATE(0);
    private Integer status;

    private QuestionPublicStatusEnum(Integer status){
        this.status=status;
    }

    public Integer getStatus() {
        return status;
    }
}
