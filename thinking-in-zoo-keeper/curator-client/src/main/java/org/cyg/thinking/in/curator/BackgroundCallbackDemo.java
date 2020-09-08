package org.cyg.thinking.in.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.zookeeper.CreateMode;

import java.util.Arrays;

/**
 * Curator 异步接口 {@link BackgroundCallback } 、 {@link CuratorListener } 、
 * {@link CuratorEvent } 使用示例
 */
public class BackgroundCallbackDemo {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = ClientUtil.getCuratorFrameworkAndStart();
        // 添加 CuratorListener 监听器, 针对不同的事件进行处理
        client.getCuratorListenable().addListener((client1, event) -> {
            System.out.println(event.getType() + "," + event.getPath());
            if (event.getType() == CuratorEventType.DELETE) {
                System.out.println("delete path=" + event.getPath());
            }
        });
        String path = "/user";
        // 先删除
        if (client.checkExists().forPath(path) != null) {
            client.delete().forPath(path);
        }
        // 注意: 下面所有的操作都添加了 inBackground() 方法, 转换为后台操作
        client.create().withMode(CreateMode.PERSISTENT)
                .inBackground().forPath(path, "test".getBytes());
        client.checkExists().inBackground().forPath(path);
        client.setData().inBackground()
                .forPath(path, "setData-Test".getBytes());
        // 由于是异步操作, 所以 直接返回 byte[] 会为 null
        byte[] data = client.getData().inBackground().forPath(path);
        System.out.println(Arrays.toString(data));
        for (int i = 0; i < 3; i++) {
            client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .inBackground().forPath(path + "child-");
        }
        // 添加 BackgroundCallback
        client.getChildren().inBackground((client12, event) ->
                System.out.println("in background:" + event.getType() + "," + event.getPath())).forPath(path);
        client.delete().deletingChildrenIfNeeded().inBackground().forPath(path);
        System.out.println(System.in.read());
    }
}
