package cn.tedu.straw.portal.exception;

/**
 * @Description: 页面不存在异常
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/16$ 22:11$
 * @Version: 1.0
 */
public class PageNotExistException extends  RuntimeException{
    public PageNotExistException(){

    }
    public PageNotExistException(String message) {
        super(message);
    }
}
