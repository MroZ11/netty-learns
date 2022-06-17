import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * 传输队列  基于阻塞队列
 *
 * 生产者可以等待消费者接收元素的阻塞队列。
 * 例如，在消息传递应用程序中，TransferQueue可能很有用，其中生产者有时（使用方法传输）
 * 等待消费者调用take或poll来接收元素，而有时（通过方法put）将元素排队而不等待接收。
 * tryTransfer的非阻塞和超时版本也可用。
 * 还可以通过hasWaitingConsumer查询TransferQueue是否有线程等待项，这与peek操作相反。
 *
 * 与其他阻塞队列一样，TransferQueue可能有容量限制。
 * 如果是这样，尝试的传输操作最初可能会阻止等待可用空间，和/或随后阻止等待消费者的接收。
 * 请注意，在容量为零的队列中，例如SynchronousQueue，put和transfer实际上是同义的。
 *
 */
public class TransferQueueTest {

    public static LinkedTransferQueue<String> queue = new LinkedTransferQueue();
    private static ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(5);

    public static void main(String[] args) throws Exception{
        testTransfer();
    }


    public  static  void testTransfer() throws Exception{
        //模拟多线程 消费者每五秒消费一次
        delayConsumeTime(5);
        //这里会被阻塞，直到消费者take 或者 poll
        queue.transfer("Bing Go");
        queue.transfer("Yellow");
    }

    public  static void delayConsumeTime(int delaySeconds){
        ScheduledFuture  scheduledFuture = executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                    final String poll = queue.poll(2, TimeUnit.SECONDS);
                    System.out.println("poll->" + poll);
                    if(poll==null){
                        //获取不到数据后 取消定时任务
                        executorService.shutdown();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, delaySeconds, delaySeconds, TimeUnit.SECONDS);
    }


}
