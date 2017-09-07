package nam.service;

import nam.thrift.GeodeQueryService;
import nam.util.GeodeCache;
import nam.util.GeodeConfig;
import nam.util.GeodeUtil;
import nam.util.WeightedRoundRobinScheduling;
import nam.zookeeper.MyZooKeeper;
import nam.zookeeper.ZKOperate;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.cache.query.*;
import org.apache.geode.cache.query.internal.StructImpl;
import org.apache.geode.pdx.JSONFormatter;
import org.apache.geode.pdx.PdxInstance;
import org.apache.thrift.TException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by Namhwik on 2017/7/17.
 */
public class GeodeServiceImpl implements GeodeQueryService.Iface {
    @Override
    public  List<String> query(String querystr) {
        Object result= null;
        try {
            result = GeodeUtil.getInstance().query(querystr);
        } catch (NameResolutionException | TypeMismatchException | FunctionDomainException | QueryInvocationTargetException e) {
            e.printStackTrace();
        }
        List<String> finalResult = new ArrayList<>();
        if (result instanceof Collection) {
            Collection results =(Collection) result;
            Object[] arrResults =((Collection) result).toArray();
            if ((arrResults).length!=0) {
                if(arrResults[0] instanceof PdxInstance)    {
                    for (int i=0;i<arrResults.length;i++)
                        finalResult.add(JSONFormatter.toJSON((PdxInstance) arrResults[i]));
                    return finalResult;
                }
                else if (arrResults[0] instanceof Struct) {
                    Struct struct = (Struct) arrResults[0];
                    System.out.println(">>?>?>>>>????>>>>"+struct.getStructType());
                    for (int i=0;i<arrResults.length;i++)
                        finalResult.add ((arrResults[i]).toString());
                    return finalResult;
                    }
                    else
                        throw new RuntimeException("Incorrect Query Type ... ");
            }
            else
                return finalResult;
        }
        else
            throw new RuntimeException("Incorrect Query Type ... ");
    }
    public static String list2json(List<?> list) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
               // json.append(object2json(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    @Override
    public void update(String key, String region, String data)  {
        if(data!=null&&!data.isEmpty())  {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            GeodeUtil.getInstance().getRegion(region).put(key,JSONFormatter.fromJSON(jsonObject.toString()));
        }
        else
            throw new RuntimeException("Data is null ...");
    }

    @Override
    public void remove(List<String> key, String region) {
        GeodeUtil.getInstance().getRegion(region).removeAll(key);
    }

    @Override
    public boolean ping() throws TException {
        return GeodeUtil.ping();
    }

    @Override
    public String getServer() throws TException {
        WeightedRoundRobinScheduling scheduling = new WeightedRoundRobinScheduling();
        List<String> servers ;
        ZKOperate zkWatchAPI = new ZKOperate();
        MyZooKeeper zooKeeper = new MyZooKeeper();
        zooKeeper.connect(GeodeConfig.ZOOKEEPER_HOST);
        servers = zkWatchAPI.getChild(GeodeConfig.SERVERS_ZOOKEEPER_PATH);
        scheduling.init(servers);
        return scheduling.GetBestServer();
    }


}
