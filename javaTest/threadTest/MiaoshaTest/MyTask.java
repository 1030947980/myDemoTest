package threadTest.MiaoshaTest;

/**
 * Created by Bing on 2023/3/17.
 */
public class MyTask implements Runnable{

    private static int id=10;
    private String userName;

    public MyTask(String userName) {
        this.userName = userName;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(userName+"正在使用"+name+"参与秒杀任务");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (MyTask.class){
            if (id>0){
                System.out.println(userName+"使用"+name+"秒杀:"+id--+"号商品");
            }else {
                System.out.println(userName+"使用"+name+"秒杀失败了");
            }
        }
    }
}
