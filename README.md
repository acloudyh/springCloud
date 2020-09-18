## 尚硅谷 springCloud 学习 持续更新

脑图链接:
链接: https://pan.baidu.com/s/1NqN2yP0FfsrozJ6bIANTNQ 提取码: k7v9

 ![Alt text](image/cloud组件图.png)

## consul

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
## 经典CAP图
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

## OpenFeign    
    Feign是一个声明式的web服务客户端，让编写web服务客户端变得非常容易，只需创建一个接口并在接口上添加注解即可
```java

//启用feign客户端
@EnableFeignClients

//定义feign客户端
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
```



## Hystrix      
#### 服务降级
- 概念: 服务降级，当服务器压力剧增的情况下，根据当前业务情况及流量对一些服务和页面有策略的降级，以此释放服务器资源以保证核心任务的正常运行; (简单来说: 服务器忙，请稍候再试，不让客户端等待并立刻返回一个友好提示，fallback)
- 哪些情况触发降级
    - 程序运行异常
    - 超时
    - 服务熔断触发服务降级
    - 线程池/信号量打满也会导致服务降级
- 注解
    ```java
      @HystrixCommand(fallbackMethod = "PaymentTimeOutFallbackMethod", commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
        })
    ```
- 注意统一fallback

#### 服务熔断
    服务的降级->进而熔断->恢复调用链路
    
   [https://martinfowler.com/bliki/CircuitBreaker.html](https://martinfowler.com/bliki/CircuitBreaker.html)
   
   ![Alt text](image/CircuitBreaker.jpg)
    
- 概念: 如果某个目标服务调用慢或者有大量超时，此时，熔断该服务的调用，对于后续调用请求，不在继续调用目标服务，直接返回，快速释放资源; 如果目标服务情况好转则恢复调用。(简单来说: 类比保险丝达到最大服务访问后，直接拒绝访问，拉闸限电，然后调用服务降级的方法并返回友好提示)
- 熔断设计 

    三个模块：熔断请求判断算法、熔断恢复机制、熔断报警
    
    - 熔断请求判断机制算法：使用无锁循环队列计数，每个熔断器默认维护10个bucket，每1秒一个bucket，每个blucket记录请求的成功、失败、超时、拒绝的状态，默认错误超过50%且10秒内超过20个请求进行中断拦截。
    
    - 熔断恢复：对于被熔断的请求，每隔5s允许部分请求通过，若请求都是健康的（RT<250ms）则对请求健康恢复。
    
    - 熔断报警：对于熔断的请求打日志，异常请求超过某些设定则报警

- 注解 配置参数
    ```java
   @HystrixCommand(fallbackMethod = "xxx_method",
            groupKey = "strGroupCommand",
            commandKey = "strCommarld",
            threadPoolKey = "strThreadPool",
            commandProperties = {
                    //设置隔离策略，THREAD 表示线程她SEMAPHORE:信号他隔离
                    @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
                    //当隔离策略选择信号他隔离的时候，用来设置信号地的大小(最大并发数)
                    @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value = "10"),
                    //配置命令执行的超时时间
                    @HystrixProperty(name = "execution.isolation.thread.timeoutinMilliseconds", value = "10"),
                    //是否启用超时时间
                    @HystrixProperty(name = "execution.timeout.enabled", value = "true"),
                    //执行超时的时候是否中断
                    @HystrixProperty(name = "execution.isolation.thread.interruptOnTimeout", value = "true"),
                    //执行被取消的时候是否中断
                    @HystrixProperty(name = "execution.isolation.thread.interruptOnCancel", value = "true"),
                    //允许回调方法执行的最大并发数
                    @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "10"),
                    //服务降级是否启用，是否执行回调函数
                    @HystrixProperty(name = "fallback.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    //该属性用来设置在滚动时间窗中，断路器熔断的最小请求数。例如，默认该值为20的时候，
                    //如果滚动时间窗(默认10秒)内仅收到了19个请求，即使这19个请求都失败了， 断路器也不会打开。
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    // 该属性用来设置在熔动时间窗中表示在滚动时间窗中，在请求数量超过
                    // circuitBreaker.requestVolumeThreshold 的情况下,如果错误请求数的百分比超过50,
                    //就把断路器设置为“打开”状态，否则就设置为“关闭”状态。
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    // 该属性用来设置当断路器打开之后的休眠时间窗。休眠时间窗结束之后,
                    //会将断路器置为"半开”状态，尝试熔断的请求命令，如果低然失败就将断路器继续设置为"打开”状态，
                    //如果成功就设置为"关闭”状态。
                    @HystrixProperty(name = "circuitBreaker.sleepWindowinMilliseconds", value = "5009"),
                    //断路器强制打开
                    @HystrixProperty(name = "circuitBreaker.force0pen", value = "false"),
                    // 断路器强制关闭
                    @HystrixProperty(name = "circuitBreaker.forceClosed", value = "false"),
                    //滚动时间窗设置，该时间用于断路器判断健康度时需要收集信息的持续时间
                    @HystrixProperty(name = "metrics.rollingStats.timeinMilliseconds", value = "10000"),
                    //该属性用来设置滚动时间窗统计指标信息时划分”桶"的数量，断路器在收集指标信息的时候会根据设置的时间窗长度拆分成多个"相"来累计各度量值，每个”桶"记录了-段时间内的采集指标。
                    //比如10秒内拆分成10个”桶"收集这样，所以timeinMilliseconds 必须能被numBuckets 整除。否则会抛异常
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "10"),
                    //该属性用来设置对命令执行的延迟是否使用百分位数来跟踪和计算。如果设置为false,那么所有的概要统计都将返回-1.
                    @HystrixProperty(name = "metrics .rollingPercentile.enabled", value = "false"),
                    //该属性用来设置百分位统计的滚动窗口的持续时间， 单位为毫秒。
                    @HystrixProperty(name = "metrics.rollingPercentile.timeInMilliseconds", value = "60000"),
                    //该属性用来设置百分位统计演动窗口中使用“桶”的数量。
                    @HystrixProperty(name = "metrics.rollingPercentile.numBuckets", value = "60000"),
                    // 该属性用来设置在执行过程中每个 “桶”中保留的最大执行次数。如果在滚动时间窗内发生超过该设定值的执行次数，就从最初的位置开始重写。例如，将该值设置为100,燎动窗口为10秒， 若在10秒内一 一个“桶 ” 中发生7500次执行，
                    //那么该“桶”中只保留最后的100次执行的统计。另外,增加该值的大小将会增加内存量的消耗， 并增加排序百分位数所需的计算
                    @HystrixProperty(name = "metrics.rollingPercentile.bucketSize", value = "100"),
                    //该属性用来设置采集影响断路器状态的健康快照(请求的成功、错误百分比) 的间隔等待时间。
                    @HystrixProperty(name = "metrics.healthSnapshot.intervalinMilliseconds", value = "500"),
                    //是否开启请求缓存
                    @HystrixProperty(name = "requestCache.enabled", value = "true"),
                    // HystrixCommand的执行和时间是否打印日志到HystrixRequestLog中
                    @HystrixProperty(name = "requestLog.enabled", value = "true"),
            },
            threadPoolProperties = {
                    //该参数用来设置执行命令线程他的核心线程数，该值 也就是命令执行的最大并发量
                    @HystrixProperty(name = "coreSize", value = "10"),
                    //该参数用来设置线程她的最大队列大小。当设置为-1时，线程池将使用SynchronousQueue 实现的队列，
                    // 否则将使用LinkedBlocakingQueue实现队列
                    @HystrixProperty(name = "maxQueueSize", value = "-1"),
                    // 该参数用来为队列设置拒绝阀值。 通过该参数， 即使队列没有达到最大值也能拒绝请求。
                    //該参数主要是対linkedBlockingQueue 队列的朴充,因为linkedBlockingQueue
                    //队列不能动态修改它的对象大小，而通过该属性就可以调整拒绝请求的队列大小了。
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "5"),
            }
    )
    ```
#### 服务限流
- 概念: 限流模式主要是提前对各个类型的请求设置最高的QPS阈值，若高于设置的阈值则对该请求直接返回，不再调用后续资源(简单来说: 秒杀高并发等操作，严禁一窝蜂的过来拥挤，大家排队，一秒钟N个，有序进行)

***参考alibaba的Sentinel***

#### Hystrix工作流程
    
   [b站视频P62](https://www.bilibili.com/video/BV18E411x7eT?p=62)
    
   ![Alt text](image/hystrix-command-flow-chart.png)