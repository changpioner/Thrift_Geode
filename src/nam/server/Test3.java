package nam.server;

import nam.service.ThriftServer;
import nam.zookeeper.ZkClientUtil;
import org.I0Itec.zkclient.ZkClient;
import org.apache.thrift.TException;

/**
 * Created by Namhwik on 2017/7/20.
 */
public class Test3 {
    public static void main(String[] args) throws TException {
        //ThriftServer server2 = new ThriftServer("server02","172.20.182.89",8888);
        //server2.startServer();
        //ThriftServer server1 = new ThriftServer("server07","172.20.182.89",8887);
        //server1.startServer();
        ZkClient zkClient = ZkClientUtil.getInstance().getZkClient();
       // zkClient.createPersistent("/thrift/test");
        zkClient.writeData("/thrift/test", "registing");
        System.out.println(zkClient.readData("/thrift/thriftleader/leader").toString());
    }
}
