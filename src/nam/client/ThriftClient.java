/*
package nam.client;

import nam.thrift.GeodeQueryService;
import nam.util.GeodeConfig;
import nam.zookeeper.MyZooKeeper;
import nam.zookeeper.ZKOperate;
import nam.zookeeper.ZkClientUtil;
import org.I0Itec.zkclient.ZkClient;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.jgroups.util.Tuple;

import java.util.List;
import java.util.UUID;

*/
/**
 * Created by Namhwik on 2017/7/18.
 *//*

public class ThriftClient {
    private  String thrift_host;
    private  Integer thrift_port;
    public ThriftClient() throws TException {
        initClient();
    }
    private void initClient() throws TException {
        String leader_host ="";
        Integer leader_port =0;
        ZkClient zkClient = ZkClientUtil.getInstance().getZkClient();
        List<String> leaders = zkClient.getChildren(GeodeConfig.LEADER_ZOOKEEPER_PATH);
        if(leaders.size()==1)   {
            String leaderData = zkClient.readData(GeodeConfig.LEADER_ZOOKEEPER_PATH+"/"+leaders.get(0));
            leader_host=getCheckedData(leaderData).getVal1();
            leader_port=getCheckedData(leaderData).getVal2();
            // 设置调用的服务地址为本地，端口为6789
            TTransport transport =  new TFastFramedTransport(new TSocket(leader_host,leader_port));
            // 数据传输协议有：二进制协议、压缩协议、JSON格式协议
            // 这里使用的是二进制协议
            // 协议要和服务端一致
            TProtocol tProtocol = new TBinaryProtocol(transport);
            GeodeQueryService.Client client = new GeodeQueryService.Client(tProtocol);
            transport.open();
            String server = client.getServer();
            String serverData = zkClient.readData(GeodeConfig.SERVERS_ZOOKEEPER_PATH+"/"+server);
            this.thrift_host=getCheckedData(serverData).getVal1();
            this.thrift_port=getCheckedData(serverData).getVal2();
            transport.close();
        }
        else {
            System.out.println("Client do not find the message of leader...  ");
            throw new RuntimeException("Client do not find the message of leader...  ");
        }

    }

    public List<String> query(String queryStr) throws TException {
        List<String> result = null;
        TTransport transport =  new TFastFramedTransport(new TSocket(thrift_host,thrift_port));
        TProtocol tProtocol = new TBinaryProtocol(transport);
        GeodeQueryService.Client client = new GeodeQueryService.Client(tProtocol);
        transport.open();
        result=client.query(queryStr);
        transport.close();
        return result;
    }

    public Tuple<String,Integer> getCheckedData(String zookeeperData)    {
        System.out.println(zookeeperData+"isssssss");
        String[] datas = zookeeperData.split(",");
        for (int i = 0; i < datas.length; i++) {
            System.out.println(">>>>>>>>"+datas[i]);
        }

        if(datas.length==3||datas.length==2)  {
            String data_1 =  datas[0];
            Integer data_2 = Integer.valueOf(datas[1]);
            return new Tuple<>(data_1,data_2);
        }
        else  {
            System.out.println("Client do not find the server from zookeeper...  ");
            throw new RuntimeException("Client do not find the server from zookeeper...  ");
        }
    }
}
*/
