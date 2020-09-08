package org.cyg.thinking.in.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceCache;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.InstanceSerializer;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/***
 * curator-x-discovery 的使用 示例
 * @see ServiceDiscovery
 * @see ServiceCache
 * @see CuratorFramework
 */
public class ZookeeperCoordinator {

    private final ServiceDiscovery<ServerInfo> serviceDiscovery;

    private final ServiceCache<ServerInfo> serviceCache;

    private final CuratorFramework client;

    private final String root;

    // 这里的JsonInstanceSerializer是将ServerInfo序列化成Json
    private final InstanceSerializer<ServerInfo> serializer
            = new JsonInstanceSerializer<>(ServerInfo.class);

    public ZookeeperCoordinator(Config config) throws Exception {
        this.root = config.getPath();
        // 创建 Curator 客户端
        this.client = CuratorFrameworkFactory
                .newClient(config.getHostPort(),
                        new ExponentialBackoffRetry(100, 3));
        // 启动客户端
        this.client.start();
        // 阻塞当前线程, 等待连接成功
        this.client.blockUntilConnected();

        // 创建ServiceDiscovery
        this.serviceDiscovery = ServiceDiscoveryBuilder.builder(ServerInfo.class)
                .client(this.client)  // 依赖 Curator 客户端
                .basePath(this.root) // 管理的 Zk 路径
                .watchInstances(true) // 当 ServiceInstance 加载
                .serializer(this.serializer)
                .build();
        // 启动 ServiceDiscovery
        this.serviceDiscovery.start();

        // 创建ServiceCache，监Zookeeper相应节点的变化，也方便后续的读取
        this.serviceCache = this.serviceDiscovery
                .serviceCacheBuilder()
                .name(this.root)
                .build();
        // 启动ServiceCache
        this.serviceCache.start();
    }

    public void registerRemote(ServerInfo serverInfo) throws Exception {
        // 将ServerInfo对象转换成ServiceInstance对象
        ServiceInstance<ServerInfo> instance = ServiceInstance
                .<ServerInfo>builder()
                .name(this.root)
                .id(UUID.randomUUID().toString()) // 随机生成的UUID
                .address(serverInfo.getHost()) // host
                .port(serverInfo.getPort()) // port
                .payload(serverInfo) // payload
                .build();
        // 将ServiceInstance写入到Zookeeper中
        this.serviceDiscovery.registerService(instance);
    }

    public List<ServerInfo> queryRemoteNodes() {
        // 查询 ServiceCache 获取全部的 ServiceInstance 对象
        List<ServiceInstance<ServerInfo>> serviceInstances =
                this.serviceCache.getInstances();
        final List<ServerInfo> serverInfoDetails = new ArrayList<>(serviceInstances.size());

        serviceInstances.forEach(instance -> {
            // 从每个ServiceInstance对象的playload字段中反序列化得到ServerInfo实例
            ServerInfo serverInfo = instance.getPayload();
            serverInfoDetails.add(serverInfo);
        });

        return serverInfoDetails;
    }

    public List<ServerInfo> queryAllInstances() throws Exception {
        Collection<String> names = this.serviceDiscovery.queryForNames();
        final List<ServerInfo> serverInfoDetails = new ArrayList<>();
        for (String name : names) {
            Collection<ServiceInstance<ServerInfo>>
                    instances = this.serviceDiscovery.queryForInstances(name);
            for (ServiceInstance<ServerInfo> instance : instances) {
                // 从每个ServiceInstance对象的playload字段中反序列化得到ServerInfo实例
                ServerInfo serverInfo = instance.getPayload();
                serverInfoDetails.add(serverInfo);
            }
        }

        return serverInfoDetails;
    }

    public String queryDataContent() throws Exception {
        byte[] data = this.client.getData().forPath(this.root);
        return new String(data);
    }

    public List<String> queryChildren() throws Exception {
        return client.getChildren().forPath(this.root);
    }
}
