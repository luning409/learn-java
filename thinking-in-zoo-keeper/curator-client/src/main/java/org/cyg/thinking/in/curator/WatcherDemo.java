package org.cyg.thinking.in.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;

import java.util.List;

/**
 * 通过 {@link Watcher} 机制，就可以使用 ZooKeeper 实现分布式锁、集群管理等功能。 使用 示例
 */
public class WatcherDemo {

    public static void main(String[] args) throws Exception {
        final CuratorFramework client = ClientUtil.getCuratorFrameworkAndStart();
        String path = "/user";
        client.create().withMode(CreateMode.PERSISTENT)
                .forPath(path, "test".getBytes());

        // 这里通过 usingWatcher() 方法添加一个 Watcher
        List<String> children = client.getChildren().usingWatcher(
                (CuratorWatcher) event ->
                        System.out.println(event.getType() + "," + event.getPath()))
                .forPath(path);
        System.out.println(children);
        System.out.println(System.in.read());
    }
}
