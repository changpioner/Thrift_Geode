/*
package nam.server;

import nam.util.GeodeUtil;
import org.apache.geode.cache.Region;
import org.apache.geode.pdx.JSONFormatter;
import org.apache.geode.pdx.PdxInstance;
import org.json.JSONException;
import org.json.JSONObject;

*/
/**
 * Created by Namhwik on 2017/7/18.
 *//*

public class PutData {
    public static void main(String[] args) {
                JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name","lilei");
            jsonObject.put("age","12");
            jsonObject.put("sex","man");
            jsonObject.put("height","180");
            jsonObject.put("weight","60");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Region<String,PdxInstance> region= GeodeUtil.getInstance().getRegion("test");
        region.put("A001", JSONFormatter.fromJSON(jsonObject.toString()));
        region.put("A002", JSONFormatter.fromJSON(jsonObject.toString()));
        region.put("A003", JSONFormatter.fromJSON(jsonObject.toString()));
        region.put("A004", JSONFormatter.fromJSON(jsonObject.toString()));
        region.put("A005", JSONFormatter.fromJSON(jsonObject.toString()));
        region.close();
    }
}
*/
