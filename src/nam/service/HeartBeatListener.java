package nam.service;

import nam.util.GeodeConfig;
import nam.util.GeodeUtil;
import nam.zookeeper.ZkClientUtil;
import org.I0Itec.zkclient.ZkClient;

/**
 * Created by Namhwik on 2017/7/19.
 */
public class HeartBeatListener  implements Runnable{
    private ThriftServer server;
    public HeartBeatListener(ThriftServer server)  {
        this.server=server;
    }
    @Override
    public void run() {
        String[] hosts = GeodeConfig.SERVERS_HOST_ALL.split(",");
            if(GeodeUtil.ping()) {
               // zooKeeper.connect(GeodeConfig.ZOOKEEPER_HOST);
                //if(!zkWatchAPI.isExists(GeodeConfig.SERVERS_ZOOKEEPER_PATH+"/"+this.server.getServerName()))
               //     zkWatchAPI.createZNode("/thrift/thriftservices/" + this.server.getServerName(),
              //          this.server.getServerHost() + "," + this.server.getServerPort(), CreateMode.EPHEMERAL_SEQUENTIAL);
                server.registerLeader();
                if(server.getZkClient().numberOfListeners()==0)
                    server.registerListener();
            }
            else  {
                ZkClient zkClient = ZkClientUtil.getInstance().getZkClient();
                if (zkClient.exists("/thrift/thriftleader/leader"))
                    zkClient.delete("/thrift/thriftleader/leader");
                System.out.println("ERROR : Can not find any server in GEODE ...");
                throw new RuntimeException(" ERROR : Can not find any server in GEODE ...");
            }
    }
}
