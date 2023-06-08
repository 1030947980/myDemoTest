package threadTest.MiaoshaTest;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bing on 2023/3/17.
 */
public class MyTest {
    public static void main(String[] args) {
        //创建线程池
        ThreadPoolExecutor  poolExecutor = new ThreadPoolExecutor(
                3,
                5,
                1,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>());
        for (int i = 1; i <= 20; i++) {
            MyTask myTask = new MyTask("客户"+i);
            poolExecutor.submit(myTask);
        }
        poolExecutor.shutdown();
    }

}
