package org.cyg.thinking.in.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * Curator 基础 API demo
 */
public class BasicAPIDemo {
    public static void main(String[] args) throws Exception {
        CuratorFramework client = ClientUtil.getCuratorFrameworkAndStart();
        String path = "/user";
        // 删除子节点
        client.delete().deletingChildrenIfNeeded().forPath(path);
        // 下面简单说明 Curator 中常用 API
        // create() 方法创建 ZNode, 可以调用额外方法设置节点类型、添加 Watcher
        // 下面是创建一个名为"user"的持久节点, 其中会存储一个 test 字符串
        path = client.create().withMode(CreateMode.PERSISTENT)
                .forPath("/user", "test".getBytes());
        System.out.println(path);
        // 输出: /user

        // checkExists() 方法可以检查一个节点是否存在
        Stat stat = client.checkExists().forPath(path);
        System.out.println(stat);
        // 输出: 节点信息
        // getData() 方法可以获取一个节点中的数据
        byte[] data = client.getData().forPath(path);
        System.out.println(new String(data));
        // 输出: test
        // setData() 方法可以设置一个节点中的数据
        stat = client.setData().forPath(path, "data".getBytes());
        System.out.println(stat);
        data = client.getData().forPath(path);
        System.out.println(new String(data));
        // 输出: data
        // 在 /user 节点下, 创建多个临时顺序节点
        for (int i = 0; i < 3; i++) {
            client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath("/user/child", String.valueOf(i).getBytes());
        }
        // getChildren() 方法可以获取所有子节点
        List<String> children = client.getChildren().forPath("/user");
        System.out.println(children);
        // 会输出: [child0000000002, child0000000000, child0000000001]
        // delete() 方法可以删除指定节点,
        // deletingChildrenIfNeeded() 方法会级联删除子节点
        client.delete().deletingChildrenIfNeeded();
    }

}
