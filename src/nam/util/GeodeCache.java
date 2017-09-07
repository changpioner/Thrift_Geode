package nam.util;

import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;

import java.net.InetSocketAddress;
import java.util.Set;

/**
 * Created by Namhwik on 2017/3/23.
 */

public class GeodeCache {
    private GeodeCache(){}
    private static ClientCache cache;


    private static void initCache() {
        cache = new ClientCacheFactory()
                .setPoolFreeConnectionTimeout(600000)
                .setPoolThreadLocalConnections(true)
                .setPoolReadTimeout(600000)
                .setPoolIdleTimeout(600000)
                .setPoolRetryAttempts(5)
                .addPoolLocator(GeodeConfig.HOST,GeodeConfig.HOST_PORT)
                .create();
    }
    public static ClientCache getCache(){
        if(cache==null){
            initCache();
        }
        return cache;
    }
    public static void closeCache () {
        if (cache!=null && (!cache.isClosed()))
            cache.close();
    }
    public static Set<InetSocketAddress> getServers() {
        if(cache==null){
            initCache();
        }
        return cache.getCurrentServers();
    }


}
