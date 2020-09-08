package org.cyg.thinking.in.curator;

import java.io.Serializable;

public class ServerInfo implements Serializable {

    private String host;

    private int port;

    private String name;

    public ServerInfo() {
    }

    public ServerInfo(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", name='" + name + '\'' +
                '}';
    }
}
