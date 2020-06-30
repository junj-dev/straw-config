package cn.tedu.straw.common;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.BindingResult;

/**
 * 通用返回对象
 * Created by zscat on 2018/4/26.
 */
@Data
@Accessors(chain = true)
public class R<T> {
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
    public static R success(Object data) {
        return new R().setCode(SUCCESS).setMsg("success").setData(data);
    }
    /**
     * 普通成功返回
     *
     */
    public static R success() {
        return new R().setCode(SUCCESS).setMsg("success");
    }
    /**
     * 普通成功返回
     */
    public static R success(String msg, Object data) {
        return new R().setCode(SUCCESS).setMsg(msg).setData(data);
    }
    /**
     * 普通失败提示信息
     */
    public static R failed() {
        return new R().setCode(FAILED).setMsg("failed");
    }

    public static R failed(String msg) {
        return new R().setCode(FAILED).setMsg(msg);
    }

    /**
     * 参数验证失败使用
     *
     * @param msg 错误信息
     */
    public static R validateFailed(String msg) {
        return new R().setCode(VALIDATE_FAILED).setMsg(msg);
    }

    /**
     * 未登录时使用
     *
     * @param msg 错误信息
     */
    public static R unauthorized(String msg) {
        return new R().setCode(UNAUTHORIZED).setMsg(msg);
    }

    /**
     * 未授权时使用
     *
     */
    public static R forbidden() {
        return new R().setCode(FORBIDDEN).setMsg("没有相关权限");
    }
    /**
     * 未授权时使用
     *
     */
    public static R forbidden(String message) {
        return new R().setCode(FORBIDDEN).setMsg(message);
    }

    /**
     * 参数验证失败使用
     *
     * @param result 错误信息
     */
    public static R validateFailed(BindingResult result) {

        return validateFailed(result.getFieldError().getDefaultMessage());
    }
    /**
     * 普通失败提示信息
     */
    public R paramFailed() {
        return new R().setCode(FAILED).setMsg("参数失败");
    }

    public R paramFailed(String msg) {
        return new R().setCode(FAILED).setMsg(msg);
    }

	


}
