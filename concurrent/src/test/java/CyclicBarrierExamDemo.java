import java.util.Random;
import java.util.concurrent.*;

/**
 * 使用CyclicBarrier模拟多场考试
 * Created by ZhangHao on 17/4/9.
 */
public class CyclicBarrierExamDemo {
    private static final int NUM = 10;

    private static final CyclicBarrier end = new CyclicBarrier(NUM, () -> System.out.println("本场考试结束,请离场"));

    private static class Person extends Thread {
        public Person(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(10) * 1000);
                System.out.println(getName() + "交卷");
                end.await();
                System.out.println(getName() + "离开");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 3; i++) {
            start(i);
            TimeUnit.SECONDS.sleep(12);
        }
    }

    private static void start(int index) {
        ExecutorService service;
        System.out.println("\n第" + index + "场考试开始");
        service = Executors.newFixedThreadPool(NUM);
        for (int i = 0; i < NUM; i++) {
            service.submit(new Person("thread" + i));
        }
        service.shutdown();
    }
}
