package org.cyg.thinking.in.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionStateListener;

import java.io.IOException;

/**
 * 连接状态监听  {@link ConnectionStateListener }, 它主要是处理 Curator 客户端 和 ZooKeeper 服务器间连接的异常情况,
 * 例如: 短暂或者长时间断开链接. 使用示例
 *
 */
public class ConnectionStateListenerDemo {

    public static void main(String[] args) throws IOException {
        final CuratorFramework client = ClientUtil.getCuratorFrameworkAndStart();
        // 添加 ConnectionStateListener 监听器
        client.getConnectionStateListenable().addListener((client1, newState) -> {
            System.out.println(client == client1);
            // 这里我们可以针对不同的连接状态进行特殊处理
            switch (newState) {
                case CONNECTED:
                    // 第一次成功连接到 ZooKeeper 之后,会进入该状态
                    // 对于每个 CuratorFramework 对象, 此状态, 仅仅出现一次
                    System.out.println("首次连接:" + client1);
                    break;
                case SUSPENDED:
                    // ZooKeeper 连接丢失
                    System.out.println("连接丢失");
                    break;
                case RECONNECTED:
                    // 丢失的连接被重新建立
                    System.out.println("重新建立连接");
                    break;
                case LOST:
                    // 当 Curator 认为会话已经过期时, 则进入此状态
                    System.out.println("会话过去");
                    break;
                case READ_ONLY:
                    // 当 Curator 连接进入只读模式
                    System.out.println("进入只读模式");
                default:
                    System.out.println("未知状态");
                    break;
            }
        });

        System.out.println(System.in.read());;
    }
}
