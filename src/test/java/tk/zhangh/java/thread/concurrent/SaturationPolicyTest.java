package tk.zhangh.java.thread.concurrent;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试线程池不同饱和策略
 * 取消main函数注释检测不同饱和策略执行方式
 * Created by ZhangHao on 2016/9/20.
 */
public class SaturationPolicyTest {

    /**
     * 线程池工作队列已满时，在不同饱和策略下表现
     * @param handler 线程池工作队列饱和策略
     */
    public static void policy(RejectedExecutionHandler handler){
        //基本线程2个，最大线程数为3，工作队列容量为5
        ThreadPoolExecutor exec = new ThreadPoolExecutor(2,3,0l, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<>(5));
        if (handler != null){
            exec.setRejectedExecutionHandler(handler);//设置饱和策略
        }
        for (int i = 0; i < 10; i++) {
            try {
                exec.submit(new Task());//提交任务
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        exec.shutdown();
    }

    public static void main(String[] args) {
//        policy(new ThreadPoolExecutor.AbortPolicy());
//        policy((new ThreadPoolExecutor.CallerRunsPolicy()));
//        policy(new ThreadPoolExecutor.DiscardPolicy());
//        policy(new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    //自定义任务
    static class Task implements Runnable {
        private static AtomicInteger count = new AtomicInteger(0);
        private int id = 0;  //任务标识
        public Task() {
            id = count.incrementAndGet();
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(3);//休眠3秒
            } catch (InterruptedException e) {
                System.err.println("线程被中断" + e.getMessage());
            }
            System.out.println(" 任务：" + id + "\t 工作线程: "+ Thread.currentThread().getName() + " 执行完毕");
        }
    }
}