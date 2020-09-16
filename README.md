### 尚硅谷 springCloud 学习

脑图链接:
链接: https://pan.baidu.com/s/1NqN2yP0FfsrozJ6bIANTNQ 提取码: k7v9

 ![Alt text](image/cloud组件图.png)

### consul

#### Mac安装consul
- 启动
    ```
    brew install consul
    ```
- 运行
    ```shell script
    consul agent -dev
    ```
- 页面查看
    ```html
    http://localhost:8500/
    ```
### 经典CAP图
```text
    C: Consistency(强一致性)
    A: Availability(可用性)
    P: Partition tolerance(分区容错)
    CAP理论关注粒度是数据，而不是整体系统设计的策略
```
    AP(Eureka)
    
    CP(Zookeeper/Consul)

  - CAP理论的核心是: -个分布式系统不可能同时很好的满足一致性，可用性和分区容错性这三个需求,
  - 最多只能同时较好的满足两个
  
  因此，根据CAP原理将NoSQL数据库分成了满足CA原则、满足CP原则和满足AP原则三大类:
  - CA-单点集群，满足一致性，可用性的系统，通常在可扩展性上不太强大。
  - CP-满足一 致性,分区容忍必的系统，通常性能不是特别高。
  - AP -满足可用性,分区容忍性的系统，通常可能对一 致性要求低一 些。
 
    ![Alt text](image/CAP.png)

### OpenFeign    
    Feign是一个声明式的web服务客户端，让编写web服务客户端变得非常容易，只需创建一个接口并在接口上添加注解即可
```java

//启用feign客户端
@EnableFeignClients

//定义feign客户端
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
```



### 服务降级      
1. *Hystrix* 