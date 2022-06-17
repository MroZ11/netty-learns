import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 一种延迟元素的无界阻塞队列，其中一个元素只有在其延迟过期时才能执行。
 * 队列的头是延迟过期时间最长的延迟元素。如果没有延迟过期，则没有head，轮询将返回null。
 * 当元素的getDelay（TimeUnit.NANOSECONDS）方法返回小于或等于零的值时，就会发生过期。
 */
public class DelayQueueTest {


    static DelayQueue queue = new DelayQueue<DelayOrder>();

    public static void main(String[] args) throws Exception {
        queue.add(new DelayOrder(LocalDateTime.of(2022, 6, 15, 18, 1, 0), "Cake"));
        queue.add(new DelayOrder(LocalDateTime.of(2022, 6, 15, 17, 59, 0), "Apple"));
        queue.add(new DelayOrder(LocalDateTime.of(2022, 6, 15, 17, 58, 0), "Banana"));

        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
    }

    private static class DelayOrder implements Delayed {

        public LocalDateTime runTime;
        public String content;

        public DelayOrder(LocalDateTime runTime, String content) {
            this.runTime = runTime;
            this.content = content;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            LocalDateTime now = LocalDateTime.now();
            return unit.convert(runTime.toEpochSecond(ZoneOffset.of("+8")) - now.toEpochSecond(ZoneOffset.of("+8")), TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {

            return Long.compare(this.getDelay(TimeUnit.NANOSECONDS), o.getDelay(TimeUnit.NANOSECONDS));
        }

        @Override
        public String toString() {
            return "DelayOrder{" +
                    "runTime=" + runTime +
                    ", content='" + content + '\'' +
                    '}';
        }
    }


}
