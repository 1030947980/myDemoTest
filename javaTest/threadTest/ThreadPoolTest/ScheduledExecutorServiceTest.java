package threadTest.ThreadPoolTest;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledExecutorService是ExecutorService的子接口
 * 具备延迟运行或定期执行任务的能力
 */
public class ScheduledExecutorServiceTest {
}


class newScheduledThreadPool{
    public static  void main(String[] args) {
        //创建具备延迟执行的线程池对象
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(3);
        for (int i = 0; i < 10; i++) {
            exec.schedule(new MyRunnable(i),2, TimeUnit.SECONDS);
        }
    }
}

class newScheduledThreadPool2{
    public static  void main(String[] args) {
        //创建具备延迟执行的线程池对象
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(2, new ThreadFactory() {
            int i=0;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,"自定义线程名称"+i++);
            }
        });
        //每两秒执行一次 第一次延迟时间，后续间隔时间。
        exec.scheduleAtFixedRate(new MyRunnable2(1),3,2, TimeUnit.SECONDS);
        //任务执行完成后过X秒再执行 第一次延迟时间，任务完成后间隔时间。
        exec.scheduleWithFixedDelay(new MyRunnable2(2),3,2, TimeUnit.SECONDS);
    }
}

class MyRunnable2 implements Runnable{

    private int id;

    public MyRunnable2(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String name = Thread.currentThread().getName();
        System.out.println("执行任务"+name+"....任务id:"+id);
    }
}

