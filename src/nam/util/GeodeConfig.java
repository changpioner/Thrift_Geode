package nam.util;

/**
 * Created by Namhwik on 2017/7/18.
 */
public class GeodeConfig {
    final static String HOST ="172.20.182.31";
    final static Integer HOST_PORT =10334;
    public final static Integer THRIFT_PORT=8884;
    public final static String THRIFT_HOST_01 ="localhost";
    public final static String ZOOKEEPER_HOST="172.20.32.211:2181,172.20.32.212:2181,172.20.32.213:2181";
    public final static String SERVERS_HOST_ALL="172.20.182.31:8884,172.20.182.32:8884,172.20.182.33:8884";
    public final static String LEADER_ZOOKEEPER_PATH ="/thrift/thriftleader";
    public final static String SERVERS_ZOOKEEPER_PATH ="/thrift/thriftservices";
}
