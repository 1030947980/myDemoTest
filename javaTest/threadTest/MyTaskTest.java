package threadTest;

/**
 * Created by Bing on 2023/3/16.
 */
public class MyTaskTest {
    public static void main(String[] args) {
        MyThreadPool pool = new MyThreadPool(2,4,20);
        for (int i = 0; i < 10; i++) {
            MyTask task = new MyTask(i);
            pool.submit(task);
        }
    }
}
