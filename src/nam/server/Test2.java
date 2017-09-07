package nam.server;

import nam.client.ClientTest;
import nam.service.ThriftServer;
import nam.util.GeodeConfig;
import nam.zookeeper.MyZooKeeper;
import nam.zookeeper.ZKOperate;
import org.apache.thrift.TException;
import org.apache.zookeeper.CreateMode;

/**
 * Created by Namhwik on 2017/7/19.
 */
public class Test2 {
    public static void main(String[] args) throws TException {
        //ThriftServer server2 = new ThriftServer("server02","172.20.182.89",8888);
        //server2.startServer();
       // ThriftServer server1 = new ThriftServer("server06","172.20.182.89",8886);
        //server1.startServer();
        //ZKOperate zkWatchAPI = new ZKOperate();
        //MyZooKeeper zooKeeper = new MyZooKeeper();
        //zooKeeper.connect(GeodeConfig.ZOOKEEPER_HOST);
        //zkWatchAPI.createZNode("/jet222","test", CreateMode.EPHEMERAL_SEQUENTIAL);
        // ThriftClient clientTest = new ThriftClient();
        // System.out.println(clientTest.query("select * from /test"));
        }
    }


