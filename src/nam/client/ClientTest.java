/*
 * 文件名：ClientTest.java
 * 版权：Copyright 2007-2016 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： ClientTest.java
 * 修改人：yunhai
 * 修改时间：2016年3月25日
 * 修改内容：新增
 */
package nam.client;

import nam.thrift.GeodeQueryService;
import nam.util.GeodeConfig;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * 客户端实现
 * 
 * @author yunhai
 */
public class ClientTest {
    /**
     * 调用Hello服务
     */
    public void startClient() {
        try {

            // 设置调用的服务地址为本地，端口为6789
            //TTransport transport = new TSocket("localhost", 1234);
            TTransport transport =     new TFastFramedTransport(new TSocket(GeodeConfig.THRIFT_HOST_01, GeodeConfig.THRIFT_PORT));
            //TAsyncClientManager clientManager = new TAsyncClientManager();
            // 数据传输协议有：二进制协议、压缩协议、JSON格式协议
            // 这里使用的是二进制协议
            // 协议要和服务端一致
            //TProtocolFactory protocol = new TBinaryProtocol.Factory();
            TProtocol tProtocol = new TBinaryProtocol(transport);
            GeodeQueryService.Client client = new GeodeQueryService.Client(tProtocol);
            transport.open();
            // 调用服务器端的服务方法
                System.out.println(client.query("select * from /test"));
            transport.close();
        } catch (TException e) {
            e.printStackTrace();
        }
    }

}

