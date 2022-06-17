import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

/***
 * 一种阻塞队列，其中每个插入操作必须等待另一个线程执行相应的删除操作，反之亦然。
 *
 *  同步队列没有任何内部容量，甚至没有一个容量。您无法窥视同步队列，因为只有当您尝试删除某个元素时，该元素才存在；
 *  除非另一个线程试图删除某个元素，否则不能插入该元素（使用任何方法）；您不能进行迭代，因为没有什么可迭代的。
 *
 *  队列的头是第一个排队的插入线程试图添加到队列中的元素；如果没有此类排队线程，则没有可用于删除的元素，poll（）将返回null。
 *  对于其他集合方法（例如contains），SynchronousQueue充当空集合。此队列不允许空元素。
 *
 * 同步队列类似于CSP和Ada中使用的集合通道。它们非常适合切换设计，在切换设计中，一个线程中运行的对象必须与另一个线程中运行的对象同步，以便向其传递一些信息、事件或任务。
 *
 * 此类支持一个可选的公平策略，用于排序等待的生产者线程和消费者线程。默认情况下，不保证此顺序。然而，公平性设置为true的队列以FIFO顺序授予线程访问权限。
 *
 */
public class SynchronousQueueTest {


    public static SynchronousQueue<String> queue = new SynchronousQueue<String>();

    private static ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(5);

    public static void main(String[] args) throws InterruptedException {

        producer();
        consume();


    }

    public static void consume(){
        Thread thread = new Thread(() -> {
            while (true){
                try {
                System.out.printf("[consume] Time %s,attempt take\n", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                final String take = queue.take(); //使用take而不是poll
                System.out.printf("[consume] Time %s,take->%s \n", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),take);
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    public static void producer(){
        Thread thread = new Thread(() -> {
            while (true){
                try {
                    System.out.printf("[producer]Time %s,attempt put\n", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    queue.put("DOM");
                    System.out.printf("[producer]Time %s,put finished\n", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }




}
