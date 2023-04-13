package threadTest.ThreadPoolTest;

import java.util.concurrent.*;

/**
 * ExecutorService线程池测试
 * Created by Bing on 2023/3/16.
 */
public class ExecutorServiceTest {
    public static void main(String[] args) {

    }
}

class newCachedThreadPool{
    public static  void main(String[] args) {
        //创建一个默认的线程池对象,里面的线程可重用,且在第一次使用时才创建,线程数量不做限制，默认空闲60S后销毁
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            exec.submit(new MyRunnable(i));
        }
    }
}
class newCachedThreadPool2{
    public static  void main(String[] args) {
        //工厂类获取线程池对象 默认空闲60S
        ExecutorService exec = Executors.newCachedThreadPool(new ThreadFactory() {
            int i=0;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,"自定义线程名称"+i++);
            }
        });
        for (int i = 0; i < 10; i++) {
            exec.submit(new MyRunnable(i));
        }
    }
}
/**
 * 多线程获取异步计算结果
 */
class newCachedThreadPoolResult{
    public static  void main(String[] args) throws ExecutionException, InterruptedException {
        //创建一个默认的线程池对象,里面的线程可重用,且在第一次使用时才创建,线程数量不做限制，默认空闲60S后销毁
        ExecutorService exec = Executors.newCachedThreadPool();
        Future<Integer> f = exec.submit(new Mycall(1,1));
        //判断任务是否完成
        boolean done = f.isDone();
        System.out.println(f.get());
    }
}


class newFixedThreadPool{
    public static  void main(String[] args) {
        //创建一个固定大小的线程池 默认空闲0S
        ExecutorService exec = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 10; i++) {
            exec.submit(new MyRunnable(i));
        }
    }
}
class newFixedThreadPool2{
    public static  void main(String[] args) {
        //创建一个固定大小的线程池 默认空闲0S
        ExecutorService exec = Executors.newFixedThreadPool(3, new ThreadFactory() {
            int i=0;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,"自定义线程名称"+i++);
            }
        });
        for (int i = 0; i < 10; i++) {
            exec.submit(new MyRunnable(i));
        }
    }
}
class newSingleThreadExecutor{
    public static  void main(String[] args) {
        //创建一个固定大小的线程池 默认空闲0S
        ExecutorService exec = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            exec.submit(new MyRunnable(i));
        }
        // 关闭线程池，不能再提交新的任务，但是先前的任务还会继续执行
//        exec.shutdown();
        //立刻关闭线程池，返回未执行的任务列表，这些任务不会执行
//        exec.shutdownNow()
    }
}
class newSingleThreadExecutor2{
    public static  void main(String[] args) {
        //创建一个固定大小的线程池 默认空闲0S
        ExecutorService exec = Executors.newSingleThreadExecutor(new ThreadFactory() {
            int i=0;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,"自定义线程名称"+i++);
            }
        });
        for (int i = 0; i < 10; i++) {
            exec.submit(new MyRunnable(i));
        }
    }
}

class MyRunnable implements Runnable{

    private int id;

    public MyRunnable(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println("执行任务"+name+"....任务id:"+id);
    }
}
class Mycall implements Callable<Integer>{

    private int a;
    private int b;

    public Mycall(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Integer call() throws Exception {
        String name = Thread.currentThread().getName();
        System.out.println("线程："+name+"准备开始计算"+a+"+"+b);
        Thread.sleep(2000);
        System.out.println("线程"+name+"开始计算"+a+"+"+b);
        return a+b;
    }
}