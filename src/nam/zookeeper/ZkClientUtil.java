package nam.zookeeper;

import nam.service.ZkChildrenListener;
import nam.util.GeodeConfig;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

/**
 * Created by Namhwik on 2017/7/21.
 */
public class ZkClientUtil {
    private ZkClient zkClient;
    private static class ZkClientSingletonHolder  {
        private static final ZkClientUtil INSTANCE = new ZkClientUtil();
    }
    private ZkClientUtil(){
        this.zkClient =new ZkClient(new ZkConnection(GeodeConfig.ZOOKEEPER_HOST, 2000));
        //zkClient.subscribeChildChanges("/thrift/thriftservices", new ZkChildrenListener());
    }
    public static final ZkClientUtil getInstance()  {
        return ZkClientSingletonHolder.INSTANCE;
    }
    public ZkClient getZkClient()  {
        return this.zkClient;
    }
}
