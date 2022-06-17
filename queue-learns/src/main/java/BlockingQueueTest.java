import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列。
 * 插入元素时，如果队列满载会被阻塞。
 * 获取元素时，如果队列为空会被阻塞。
 *
 * 对应的双端操作的队列为BlockingDeque
 * @author cloud
 */
public class BlockingQueueTest {

    public  static LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);



    public static void main(String[] args) {
        //success
        testPut(1);

        //success
        testTake(1);

        //超时退出 不再插入
        testOffer(10,1);
        //超时退出 不再提取
        testPoll(10,1);

        //block 插入元素时，如果队列满载会被阻塞。
        //testPut(6);

        //block 获取元素时，如果队列为空会被阻塞。
        //testTake(10);


        System.out.println("finish");
    }

    public static void testPut(Integer itemsCount){

        for (int i = 0; i < itemsCount; i++) {
            try {
                System.out.println("put->"+i);
                queue.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void testTake(Integer itemsCount){
        for (int i = 0; i < itemsCount; i++) {
            try {
                int item = queue.take();
                System.out.println("take->"+item);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void testOffer(Integer itemsCount,Integer timeoutSeconds){
        for (int i = 0; i < itemsCount; i++) {
            try {

                boolean result = queue.offer(i,timeoutSeconds, TimeUnit.SECONDS);
                System.out.println("offer->"+i+"  result->"+result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void testPoll(Integer itemsCount,Integer timeoutSeconds){
        for (int i = 0; i < itemsCount; i++) {
            try {

                Integer item =  queue.poll(timeoutSeconds, TimeUnit.SECONDS);

                System.out.println("poll->"+item);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }





}
