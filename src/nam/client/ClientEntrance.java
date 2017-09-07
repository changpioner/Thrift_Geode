package nam.client;

import nam.service.ThriftServer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Namhwik on 2017/7/17.
 */
public class ClientEntrance {
    public static void main(String[] args) {
        final CountDownLatch begin = new CountDownLatch(1);
        final ExecutorService exec = Executors.newFixedThreadPool(10);
        for (int index = 0; index < 4; index++)
        {
            final String NO = String.valueOf(index + 1);
            Runnable run = () -> {
                try {
                    // 等待，所有一起执行
                    begin.await(2L, TimeUnit.SECONDS);
                    //*****执行程序去********//
                   // ClientTest clientTest = new ClientTest();
                    //System.out.print("Time :===> "+NO +"<===");
                    //clientTest.startClient();
                    ThriftServer server = new ThriftServer("server0"+NO,"172.20.182.89",8880+Integer.valueOf(NO));
                    server.startServer();
                    //*****执行程序去********//
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            };
            exec.submit(run);
        }
        System.out.println("开始执行");
        // begin减一，开始并发执行
        begin.countDown();
        //关闭执行
        exec.shutdown();
    }
}
