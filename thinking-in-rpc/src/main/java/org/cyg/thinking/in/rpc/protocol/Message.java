package org.cyg.thinking.in.rpc.protocol;

/**
 *
 */
public class Message {

    private Header header;

    private Object request;

    public Message(Header header, Object request) {
        this.header = header;
        this.request = request;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }
}
