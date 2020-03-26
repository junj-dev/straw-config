package cn.tedu.straw.common.util;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.BindingResult;

/**
 * 通用返回对象
 * Created by zscat on 2018/4/26.
 */
@Data
public class StrawResult<T> {
    //操作成功
    public static final int SUCCESS = 200;
    //操作失败
    public static final int FAILED = 500;
    //参数校验失败
    public static final int VALIDATE_FAILED = 404;
    //未认证
    public static final int UNAUTHORIZED = 401;
    //未授权
    public static final int FORBIDDEN = 403;
    private int code;
    private String msg;
    private T data;



    /**
     * 普通成功返回
     *
     * @param data 获取的数据
     */
    public StrawResult<T> success(T data) {

        this.code = SUCCESS;
        this.msg = "sucess";
        this.data = data;
        return this;
    }
    /**
     * 普通成功返回
     *
     */
    public StrawResult<T> success() {
        this.code = SUCCESS;
        this.msg = "success";
        return this;
    }
    /**
     * 普通成功返回
     */
    public StrawResult<T> success(String msg, T data) {
        this.code = SUCCESS;
        this.msg = msg;
        this.data = data;
        return this;
    }


    /**
     * 普通失败提示信息
     */
    public StrawResult<T> failed() {
        this.code = FAILED;
        this.msg = "failed";
        return this;
    }

    public StrawResult<T> failed(String msg) {
        this.code = FAILED;
        this.msg = msg;
        return this;
    }

    /**
     * 参数验证失败使用
     *
     * @param msg 错误信息
     */
    public StrawResult<T> validateFailed(String msg) {
        this.code = VALIDATE_FAILED;
        this.msg = msg;
        return this;
    }

    /**
     * 未登录时使用
     *
     * @param msg 错误信息
     */
    public StrawResult<T> unauthorized(T msg) {
        this.code = UNAUTHORIZED;
        this.msg = "暂未登录或token已经过期";
        this.data = msg;
        return this;
    }

    /**
     * 未授权时使用
     *
     */
    public StrawResult<T> forbidden() {
        this.code = FORBIDDEN;
        this.msg = "没有相关权限";
        this.data = null;
        return this;
    }

    /**
     * 参数验证失败使用
     *
     * @param result 错误信息
     */
    public StrawResult<T> validateFailed(BindingResult result) {
        validateFailed(result.getFieldError().getDefaultMessage());
        return this;
    }
    /**
     * 普通失败提示信息
     */
    public StrawResult<T> paramFailed() {
        this.code = FAILED;
        this.msg = "参数失败";
        return this;
    }

    public StrawResult<T> paramFailed(String msg) {
        this.code = FAILED;
        this.msg = msg;
        return this;
    }

	


}
