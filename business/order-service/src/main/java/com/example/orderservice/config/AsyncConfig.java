package com.example.orderservice.config;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Created by chenweida on 2016.10.18.
 * 启用多綫程
 */
@Configuration
@EnableAsync
public class AsyncConfig {
    /**
     * 核心线程数量
     * 如果池中的实际线程数小于corePoolSize,无论是否其中有空闲的线程，都会给新的任务产生新的线程
     * 线程池初始化时创建的线程数量
     */
    private int corePoolSize = 5;
    /**
     * 如果池中的线程数＝maximumPoolSize，则有空闲线程使用空闲线程，否则新任务放入queueCapacity.
     * 设定 比 系统native thread个数要大的话，会优先抛出Java.lang.OutOfMemoryError: unable to create new native thread
     */
    private int maxPoolSize = 10;
    /**
     * 缓冲队列大小
     * 当线程数量达到核心线程数量时，新的任务进来会优先进去任务队列中，只有任务队列满了后，才会创建新的一个线程（不会超过最大线程数量）
     * 
     */
    private int queueCapacity = 100;

    /**
     * 线程池维护线程所允许的空闲时间  秒
     */
    private int keepAliveSeconds = 300;

    @Bean
    public Executor projectExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        /** Reject策略预定义有四种：
         (1)ThreadPoolExecutor.AbortPolicy 中止策略，是默认的策略,处理程序遭到拒绝将抛出运行时 RejectedExecutionException。
         (2)ThreadPoolExecutor.CallerRunsPolicy策略 ,调用者的线程会执行该任务,如果执行器已关闭,则丢弃.
         (3)ThreadPoolExecutor.DiscardPolicy策略，不能执行的任务将被丢弃.
         (4)ThreadPoolExecutor.DiscardOldestPolicy策略，如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，然后重试执行程序（如果再次失败，则重复此过程）.
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
