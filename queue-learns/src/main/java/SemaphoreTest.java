import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

/**
 *计数信号灯。从概念上讲，信号量维护一组许可。
 * 如有必要，每个采集模块都会阻塞，直到获得许可证，然后再获取。每次发布都会添加一个许可证，可能会释放一个阻塞收单机构。
 * 但是，未使用实际许可对象；信号量只保留可用数字的计数，并相应地进行操作。
 * 举例：线程池的交接。池内有可用线程时释放许可，以供消费者获取空闲线程。消费者使用完线程后归还线程标记为空闲，并再次释放许可，以便其他消费者获取。
 */
public class SemaphoreTest {

    public static Semaphore semaphore = new Semaphore(0);

    private static ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(5);

    public static void main(String[] args) throws Exception {
        //testAcquire();
        testTryAcquire();
    }


    public  static void  testAcquire() throws InterruptedException {
        //模拟释放许可
        delayRelease(5);

        while (true){
            //这里会被阻塞 直到获取到许可
            semaphore.acquire();
            System.out.printf("Now time->%s \n", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        }
    }


    public  static void  testTryAcquire() throws InterruptedException {
        //模拟释放许可 每10秒释放一个
        delayRelease(10);

        while (true){
            //这里会被阻塞 知道获取到许可或者超时
            //可以预想到 由于这里是5秒超时，而我们的信号10秒才产生一个。所有间隔5秒先会获取失败，再过5秒就会获取成功。
            boolean success =  semaphore.tryAcquire(1,5,TimeUnit.SECONDS);
            System.out.printf("Now time->%s \n", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            System.out.printf("TryAcquire success?->%s \n",success);
        }
    }






    /**
     * 定时释放许可
     * @param delaySeconds
     */
    public  static void delayRelease(int delaySeconds){
        ScheduledFuture scheduledFuture = executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                semaphore.release(1);
            }
        }, delaySeconds, delaySeconds, TimeUnit.SECONDS);
    }




}
