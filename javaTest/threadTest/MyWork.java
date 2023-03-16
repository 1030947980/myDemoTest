package threadTest;

import java.util.List;

/**
 * 线程类
 * Created by Bing on 2023/3/16.
 */
public class MyWork extends Thread{

    private String name;//线程名称
    private List<Runnable> taskList;//任务列表

    public MyWork(String name, List<Runnable> taskList) {
        this.name = name;
        this.taskList = taskList;
    }

    @Override
    public void run(){
        while (taskList.size()>0){
            Runnable r = taskList.remove(0);
            r.run();
        }
    }
}
