import java.util.Queue;

/***
 * Queue作为所有队列实现的基础接口
 * https://blog.csdn.net/weixin_44052055/article/details/122880102
 */
public interface BaseQueue extends Queue {


    /**
     * 向队列插入元素，超出队列容量时会抛出异常
     * @param o
     * @return
     */
    @Override
    boolean add(Object o);

    /**
     * 向队列插入元素，插入失败时返回false而不是抛出异常
     * @param o
     * @return
     */
    @Override
    boolean offer(Object o);

    /**
     * 移除并返回头部元素，当队列为空时会抛出异常
     * @return
     */
    @Override
    Object remove();

    /**
     * 移除并返回头部元素，当队列为空时返回false,而不是抛出异常
     * @return
     */
    @Override
    Object poll();

    /**
     * 查询但不删除头部元素，当队列为空时会抛出异常
     * @return
     */
    @Override
    Object element();

    /**
     * 查询但不删除头部元素，当队列为空时会返回false,而不是抛出异常
     * @return
     */
    @Override
    Object peek();
}
