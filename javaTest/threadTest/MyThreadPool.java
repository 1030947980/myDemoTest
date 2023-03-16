package threadTest;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 自定义线程池
 * Created by Bing on 2023/3/16.
 */
public class MyThreadPool {
    //任务队列 需要控制线程安全
    private List<Runnable> tasks = Collections.synchronizedList(new LinkedList<>());
    //当前线程数量
    private int num;
    //核心线程数量
    private int corePoolSize;
    //最大线程数量
    private int maxPoolSize;
    //任务队列长度
    private int queueCapacity;

    public MyThreadPool(int corePoolSize, int maxPoolSize, int queueCapacity) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.queueCapacity = queueCapacity;
    }

    //提交任务
    public void submit(Runnable r){
        if (tasks.size()>=queueCapacity){
            System.out.println("任务:"+r+"被丢弃了...");
        }else {
            tasks.add(r);
            execTask(r);
        }
    }
    //执行任务
    public void execTask(Runnable r){
        if (num<corePoolSize){
            new MyWork("核心线程："+num,tasks).start();
            num++;
        }else if (num<maxPoolSize){
            new MyWork("非核心线程："+num,tasks).start();
            num++;
        }else {
            System.out.println("任务；"+r+" 被缓存了...");
        }
    }

}
