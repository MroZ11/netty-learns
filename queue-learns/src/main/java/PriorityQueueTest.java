import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 优先队列 默认按自然顺序升序排列，可以传入Comparator自定排序规则,越小的越前
 */
public class PriorityQueueTest {



    public static PriorityQueue<String> stringQueue = new PriorityQueue<String>();
    public static PriorityQueue<Integer> intQueue = new PriorityQueue<Integer>();
    public static PriorityQueue<Order> orderQueue = new PriorityQueue<Order>(new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            return o1.orderTime.isAfter(o2.orderTime)?1:(o1.orderTime.isEqual(o2.orderTime))?0:-1;
        }
    });



    public static void main(String[] args) {
        testStrPriorityQueue();
        testIntPriorityQueue();
        testOrderPriorityQueue();
    }

    public static void testStrPriorityQueue(){
        stringQueue.add("C");
        stringQueue.add("A");
        stringQueue.add("B");
        System.out.println(stringQueue.poll());
        System.out.println(stringQueue.poll());
        System.out.println(stringQueue.poll());
    }


    public static void testIntPriorityQueue(){
        intQueue.add(3);
        intQueue.add(1);
        intQueue.add(2);
        System.out.println(intQueue.poll());
        System.out.println(intQueue.poll());
        System.out.println(intQueue.poll());
    }


    public static void testOrderPriorityQueue(){
        orderQueue.add(new Order(LocalDateTime.of(2022,1,6,0,0,0),"Cake"));
        orderQueue.add(new Order(LocalDateTime.of(2022,1,1,0,0,0),"Cake"));
        orderQueue.add(new Order(LocalDateTime.of(2022,1,2,0,0,0),"Cake"));
        System.out.println(orderQueue.poll());
        System.out.println(orderQueue.poll());
        System.out.println(orderQueue.poll());
    }

    private static class Order {
        private LocalDateTime orderTime;
        private String content;
        public Order(LocalDateTime orderTime, String content) {
            this.orderTime = orderTime;
            this.content = content;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "orderTime=" + orderTime +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}
