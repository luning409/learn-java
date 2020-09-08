package org.cyg.thinking.in.curator;

public class Config {

    public Config(String path, String hostPort) {
        this.path = path;
        this.hostPort = hostPort;
    }

    private String path;

    private String hostPort;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHostPort() {
        return hostPort;
    }

    public void setHostPort(String hostPort) {
        this.hostPort = hostPort;
    }
}
