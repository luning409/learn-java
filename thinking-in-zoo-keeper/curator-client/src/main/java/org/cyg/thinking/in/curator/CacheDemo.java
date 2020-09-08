package org.cyg.thinking.in.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Apache Curator 引入了 Cache 来实现对 ZooKeeper 服务端事件的监听。Cache 是 Curator 中对事件监听的包装，
 * 其对事件的监听其实可以近似看作是一个本地缓存视图和远程ZooKeeper 视图的对比过程。
 * 同时，Curator 能够自动为开发人员处理反复注册监听，从而大大简化了代码的复杂程度。
 *
 * @see NodeCache
 */
public class CacheDemo {

    public static void main(String[] args) throws Exception {
        final CuratorFramework client = ClientUtil.getCuratorFrameworkAndStart();

        String path = "/user";

        // 创建 NodeCache , 监听的是 "/user" 这个节点
        NodeCache nodeCache = new NodeCache(client, path);

        // start()方法有个boolean类型的参数，默认是false。如果设置为true，
        // 那么NodeCache在第一次启动的时候就会立刻从ZooKeeper上读取对应节点的
        // 数据内容，并保存在Cache中。
        nodeCache.start(true);
        if (null != nodeCache.getCurrentData()) {
            System.out.println("NodeCache节点初始化数据为："
                    + new String(nodeCache.getCurrentData().getData()));
        } else {
            System.out.println("NodeCache节点数据为空");
        }

        // 添加监听器
        nodeCache.getListenable().addListener(() -> {
            String data = new String(nodeCache.getCurrentData().getData());

            System.out.println("NodeCache 节点路径:" + nodeCache.getPath()
                    + ", 节点数据:" + data);
        });

        // 创建PathChildrenCache实例，监听的是"user"这个节点
        PathChildrenCache childrenCache = new PathChildrenCache(client, path, true);
        // StartMode指定的初始化的模式
        // NORMAL: 普通异步初始化
        // BUILD_INITIAL_CACHE: 同步初始化
        // POST_INITIALIZED_EVENT: 异步初始化，初始化之后会触发事件
        childrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        List<ChildData> children = childrenCache.getCurrentData();
        System.out.println("获取子节点列表:");
        // 如果是BUILD_INITIAL_CACHE可以获取这个数据，如果不是就不行
        children.forEach(childData -> System.out.println(new String(childData.getData())));

        childrenCache.getListenable().addListener(((client1, event) -> {
            System.out.println(LocalDateTime.now() + "  " + event.getType());
            String path1 = event.getData().getPath();
            if (PathChildrenCacheEvent.Type.INITIALIZED.equals(event.getType())) {
                System.out.println("PathChildrenCache: 子节点初始化成功...");
            } else if (PathChildrenCacheEvent.Type.CHILD_ADDED.equals(event.getType())) {
                System.out.println("PathChildrenCache:添加子节点:" + path1);
                System.out.println("PathChildrenCache:子节点数据:" + new String(event.getData().getData()));
            } else if (PathChildrenCacheEvent.Type.CHILD_REMOVED.equals(event.getType())) {
                System.out.println("PathChildrenCache:删除子节点:" + path1);
            } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)) {
                System.out.println("PathChildrenCache:修改子节点路径:" + path1);
                System.out.println("PathChildrenCache:修改子节点数据:" + new String(event.getData().getData()));
            }
        }));
        // 创建TreeCache实例监听"user"节点
        TreeCache treeCache = TreeCache.newBuilder(client, path).setCacheData(false).build();
        treeCache.getListenable().addListener((client1, event) -> {
            if (event.getData() == null) {
                System.out.println("TreeCache,type=" + event.getType());
            }
            else {
                System.out.println("TreeCache,type=" + event.getType() + " path = " + event.getData().getPath());
            }
        });
        treeCache.start();
        System.out.println(System.in.read());
    }
}
