/*
 * 文件名：StartServer.java
 * 版权：Copyright 2007-2016 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： StartServer.java
 * 修改人：yunhai
 * 修改时间：2016年3月25日
 * 修改内容：新增
 */
package nam.service;

import nam.thrift.GeodeQueryService;
import nam.util.GeodeConfig;
import nam.util.GeodeUtil;
import nam.zookeeper.MyZooKeeper;
import nam.zookeeper.ZKOperate;
import nam.zookeeper.ZkClientUtil;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 启动服务
 * 
 * @author Namhwik
 */
public class ThriftServer {
    private String serverHost ;
    private Integer serverPort ;
    private String serverName;
    private ZkClient zkClient;
    private Boolean isLeader= false;
    private Boolean oldLeader = true;
    public ThriftServer(String serverName, String serverHost, Integer serverPort) {
        this.serverName=serverName;
        this.serverHost=serverHost;
        this.serverPort=serverPort;
    }

    /**
     * 启动Thrift服务器
     */
    private TServer init() {
        try {
            // 定义传输的socket，设置服务端口为8888
            TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(serverPort);
            // 关联处理器与 Hello服务的实现
            GeodeQueryService.Processor<GeodeServiceImpl> processor = new GeodeQueryService.Processor<>(new GeodeServiceImpl());
            // 定义服务端的参数值
            TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(serverTransport);
            // 设置协议工厂为 TBinaryProtocol.Factory
            args.protocolFactory(new Factory());
            args.processor(processor);
            //TServer server =new TThreadedSelectorServer(args)
            // 服务端开启服务
            System.out.println("init finished...");
            return new TThreadedSelectorServer(args);

        } catch (TTransportException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void startServer()  {
        //启动服务
        TServer server = init();
        if (server!=null) {
            ZkClientUtil zkClientUtil = ZkClientUtil.getInstance();
            this.zkClient = zkClientUtil.getZkClient();
            System.out.println(serverName+" is registing Server2ZooKeeper  ");
            registerServer2ZooKeeper(this.serverName,this.serverHost,this.serverPort.toString());
            System.out.println(serverName+" is registerServer2ZooKeeper  ");
            if(GeodeUtil.ping()) {
                // zooKeeper.connect(GeodeConfig.ZOOKEEPER_HOST);
                if(!zkClient.exists(GeodeConfig.SERVERS_ZOOKEEPER_PATH+"/"+getServerName()))
                    zkClient.createEphemeralSequential("/thrift/thriftservices/" + getServerName(),
                            getServerHost() + "," + getServerPort());
                registerLeader();
            }
            else
                throw new RuntimeException(" ERROR : Can not find any server in GEODE ...");

            ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            //从现在开始,启动心跳注册线程HeartBeat,每3秒钟再次向ZooKeeper注册心跳
            scheduledExecutorService.scheduleWithFixedDelay(new HeartBeatListener(this), 0, 3, TimeUnit.SECONDS);
            System.out.println(serverName+" is started ");
            server.serve();
        }
        else    {
            System.out.println("server is null ");
            throw new RuntimeException("server start failed...");
        }

    }
    public void stopServer()  {
        init().stop();
    }
    private void registerServer2ZooKeeper(String serverName, String serverHost, String serverPort)   {
        if(!zkClient.exists("/thrift"))
            zkClient.createPersistent("/thrift","thrift");
        else if(!zkClient.exists("/thrift/thriftservices"))
            zkClient.createPersistent("/thrift/thriftservices","thriftservices");
        else
            zkClient.createEphemeral("/thrift/thriftservices/"+serverName,serverHost+","+ serverPort);
        System.out.println("registered Server to ZooKeeper");
    }
    void registerLeader(){
        if(!zkClient.exists("/thrift"))
            zkClient.createPersistent( "/thrift","thrift");
        else if(!zkClient.exists("/thrift/thriftleader")) {
            zkClient.createPersistent("/thrift/thriftleader", "thriftleader");
        }
        if(zkClient.getChildren("/thrift/thriftleader").isEmpty()) {
            if (!"registing".equals(zkClient.readData("/thrift/thriftleader").toString())) {
                zkClient.writeData("/thrift/thriftleader", "registing");
                try {
                    zkClient.createEphemeral("/thrift/thriftleader/" + "leader", serverHost + "," + serverPort+","+serverName);
                    this.isLeader = true;
                    zkClient.writeData("/thrift/thriftleader", "registed");
                } catch (Exception ex) {
                    oldLeader = false;
                    System.out.println("multiple error in registerLeader ..."+ex.getMessage()+"regist leader"+isLeader);
                    zkClient.writeData("/thrift/thriftleader", "thriftleader");
                }
            }
        }
    }
    public void registerListener()  {
        // 如果当前Server是Leader 注册监听
        if (isLeader)  {
          ZkClient zkClient = ZkClientUtil.getInstance().getZkClient();
          zkClient.subscribeChildChanges("/thrift/thriftservices",new ZkChildrenListener());
          //如果是新的leader，汇报当前的Thrift Server 列表
          if(!oldLeader) {
              List<String> list = zkClient.getChildren("/thrift/thriftservices");
              System.out.println(list);
              oldLeader=true;
          }
        }
    }

    public String getServerHost() {
        return serverHost;
    }
    public ZkClient getZkClient() {
        return zkClient;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
