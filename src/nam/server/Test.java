package nam.server;

import nam.service.ThriftServer;
import nam.util.GeodeConfig;
import nam.util.GeodeUtil;
import nam.zookeeper.MyZooKeeper;
import nam.zookeeper.ZKOperate;
import org.I0Itec.zkclient.ZkClient;
import org.apache.geode.cache.query.*;
import org.apache.zookeeper.CreateMode;

/**
 * Created by Namhwik on 2017/7/18.
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {

       //List<String> arr  = query("select name,age from /test3");
       // for (String anArr : arr) {
        //   System.out.println("result ::====>" + anArr);}
       // System.out.println(GeodeUtil.getInstance().ping());

      //ZkClient zkClient = new ZkClient(GeodeConfig.ZOOKEEPER_HOST);
        //zkClient.createEphemeral("/test","data");
       ThriftServer server1 = new ThriftServer("server05","172.20.182.89",8886);
       server1.startServer();
      //  ZKOperate zkWatchAPI = new ZKOperate();
      //  MyZooKeeper zooKeeper = new MyZooKeeper();
      //  zooKeeper.connect(GeodeConfig.ZOOKEEPER_HOST);
      //  System.out.println(!zkWatchAPI.isExists("/thrift/thriftservices"));
       // zkWatchAPI.createZNode("/thrift/thriftservices","thriftservices", CreateMode.PERSISTENT);
       // System.out.println(GeodeUtil.ping());
    }
    public void struct2String(Struct struct) {

        Object[] objects=struct.getFieldValues();
        for (Object object : objects) {
            object.toString();
        }

    }
    }

