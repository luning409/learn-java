package org.cyg.thinking.in.curator;

import java.util.List;

/**
 * {@link ZookeeperCoordinator} 使用示例
 */
public class ZookeeperCoordinatorDemo {

    public static void main(String[] args) {
        Config config = new Config("/myData", "127.0.0.1:2181");

        try {
            ZookeeperCoordinator coordinator = new ZookeeperCoordinator(config);
            ServerInfo serverInfo = new ServerInfo("127.0.0.1", 2181);
            serverInfo.setName("myData:zhangsan");
            coordinator.registerRemote(serverInfo);
            serverInfo = new ServerInfo("127.0.0.1", 2181);
            serverInfo.setName("myData:lisi");
            coordinator.registerRemote(serverInfo);
            List<ServerInfo> servers = coordinator.queryRemoteNodes();
            servers.forEach(System.out::println);
            System.out.println("----------------------------------------------");
            servers = coordinator.queryAllInstances();
            servers.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
