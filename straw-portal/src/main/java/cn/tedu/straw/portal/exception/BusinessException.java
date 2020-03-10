package cn.tedu.straw.portal.exception;

import org.junit.runner.RunWith;

/**
 * @Description: 业务异常
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/10$ 10:50$
 * @Version: 1.0
 */
public class BusinessException extends RuntimeException {
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

