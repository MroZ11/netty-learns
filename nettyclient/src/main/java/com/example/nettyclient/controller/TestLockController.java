package com.example.nettyclient.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test-lock")
public class TestLockController {

    public static ConcurrentHashMap<String, String> ordersInDeal = new ConcurrentHashMap<>();

    /**
     * 用postman同时发两条 会在5s后同时返回
     *
     * @param orderId
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/no-lock")
    public String noLock(String orderId) throws InterruptedException {
        System.out.println(orderId);
        //模拟耗时操作
        TimeUnit.SECONDS.sleep(5);
        return "Your request has been processed";
    }

    /**
     * synchronized 锁住方法
     * 用postman同时发两条 第一条5s后返回 第二条10s后返回（等待第一条执行完成）
     * 可用于解决 微信支付回调通知并发问题
     *
     * @param orderId
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/lock")
    public synchronized String lock(String orderId) throws InterruptedException {
        System.out.println(orderId);
        //模拟耗时操作
        TimeUnit.SECONDS.sleep(5);
        return "Your request has been processed";
    }


    /**
     * 用postman同时发相同的两条1条5秒后返回。1条立即返回在处理中。
     *
     * @param orderId
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/by-con-map")
    public String byConcurrentMap(String orderId) throws InterruptedException {
        //如果不存在就保存，并返回NULL。如果存在就不保存，直接返回原来的对象。
        String exists = ordersInDeal.putIfAbsent(orderId, orderId);
        if (exists != null) {
            System.out.printf(" Same orderId:%s is processing\n",orderId);
            return "Same orderId is  processing ";
        }
        try {
            System.out.printf(" orderId:%s is processing\n",orderId);
            //模拟耗时操作
            //TimeUnit.SECONDS.sleep(5);
        } finally {
            ordersInDeal.remove(orderId);
            System.out.printf(" orderId:%s process finished\n",orderId);
        }
        return "Your request has been processed";
    }


}
