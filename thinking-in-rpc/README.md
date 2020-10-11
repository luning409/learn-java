# learn-rpc
学习 RPC 的简易实现

各个包的功能:

* protocol: 简易版 RPC 框架的自定义协议
* serialization: 提供了自定义协议对应的序列化、反序列化的相关工具
* codec: 提供了自定义协议对应的编码器和解码器
* transport: 基于 Netty 提供了底层网络通信的功能, 其中会使用到 codec 包中定义编码器和解码器, 以及 serialization 包中的序列化器和反序列化
* registry: 基于 ZooKeeper 和 Curator 实现了简易版本的注册中心功能
* proxy: 使用 JDK 动态代理实现了一层代理

定义简易版 RPC 协议

| short | byte |      |      |     byte |    | long | int | n byte |
| :----: | :----: | :----: | :----: |    :----: | :---: | :---:  | :---: | :---: |
| magic | version |      |      | extraInfo | | messageId | size | message body |
| 魔数 | 协议版本 |   0 <br> 消息类型 <br> (请求还是响应)   |  1-2 <br> 序列化方式    | 3-4 <br> 压缩方式 | 5-6 <br> 请求类型 <br> (正常请求、心跳请求) | 消息ID | 消息体长度 | 消息体 | 
