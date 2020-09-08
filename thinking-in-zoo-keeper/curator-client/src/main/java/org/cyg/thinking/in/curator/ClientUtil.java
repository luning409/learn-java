package org.cyg.thinking.in.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ClientUtil {

    /**
     * 创建 Curator Client 并启动, 启动成功之后, 就可以与 ZooKeeper 进行交互了
     * @return Curator Client
     */
    public static CuratorFramework getCuratorFrameworkAndStart() {
        // ZooKeeper 集群地址， 多个节点地址可以使用逗号分隔
        String zkAddress = "127.0.0.1:2181";
        // 重试策略， 如果连接不上 ZooKeeper 集群，会重试三次， 重试间隔会递增
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(100, 3);
        // 创建 Curator Client 并启动, 启动成功之后, 就可以与 ZooKeeper 进行交互了
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkAddress, retryPolicy);
        client.start();
        return client;
    }
}
