package org.cyg.thinking.in.rpc.protocol;

import java.io.Serializable;

public class Response implements Serializable {
    /**
     * 响的应错误码, 正常响应为 0,  非0 表示异常响应
     */
    private int code = 0;
    /**
     * 异常信息
     */
    private String errMsg;
    /**
     * 响应结果
     */
    private Object result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
