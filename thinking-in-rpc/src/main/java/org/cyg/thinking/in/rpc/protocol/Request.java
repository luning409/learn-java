package org.cyg.thinking.in.rpc.protocol;

import java.io.Serializable;

/**
 * 请求消息体
 */
public class Request implements Serializable {
    /**
     * 请求的 Service 类名
     */
    private String serviceName;
    /**
     * 请求的方法名称
     */
    private String methodName;
    /**
     * 请求方法的参数类型
     */
    private Class[] argTypes;
    /**
     * 请求方法的参数
     */
    private Object[] args;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getArgTypes() {
        return argTypes;
    }

    public void setArgTypes(Class[] argTypes) {
        this.argTypes = argTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
