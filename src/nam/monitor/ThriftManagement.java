package nam.monitor;

import nam.service.ThriftServer;
import nam.util.GeodeConfig;
import nam.zookeeper.MyZooKeeper;
import nam.zookeeper.ZKOperate;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Namhwik on 2017/7/18.
 */
public class ThriftManagement {

    public void startServers()  {
        System.out.println("Server Start!");
       // ThriftServer server = new ThriftServer();
       // server.startServer();
        ZkClient zkClient = new ZkClient("172.20.182.31:2181,172.20.182.32:2181,172.20.182.33:2181");
      //  zkClient.
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        //从现在开始,启动线程A，线程A执行完后,延迟5秒钟再次启动线程A进行监控扫描
     //   scheduledExecutorService.scheduleWithFixedDelay(new GeodeListener(), 0, 5, TimeUnit.SECONDS);

      /*
      获取是Leader 的 thrift server 的 ip 端口号
      ZKOperate zkWatchAPI = new ZKOperate();
        MyZooKeeper zooKeeper = new MyZooKeeper();
        zooKeeper.connect(GeodeConfig.ZOOKEEPER_HOST);
        List<String> leaders = zkWatchAPI.getChild(GeodeConfig.LEADER_ZOOKEEPER_PATH);
        if (leaders.isEmpty())    {
            System.out.println("Can not find any leader ...");
            throw new RuntimeException("No avaliable services ...");
        } else if(leaders.size()!=1) {
            System.out.println("Error finded too much leader ...");
            throw new RuntimeException("There are several leaders conflicted ...");
        } else
            return zkWatchAPI.readData(GeodeConfig.LEADER_ZOOKEEPER_PATH+"/"+leaders.get(0));

            */
    }
}
