package threadTest;

/**
 * 任务类
 * Created by Bing on 2023/3/16.
 */
public class MyTask implements Runnable{

    private int id;//任务ID

    public MyTask(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println("线程："+name+" 即将执行任务"+id);
        try {
            Thread.sleep(200);
        }catch (Exception e){}
        System.out.println("线程："+name+" 完成了任务"+id);
    }

    @Override
    public String toString() {
        return " MyTask {id="+id+"} ";
    }
}
