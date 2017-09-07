package nam.service;

import org.I0Itec.zkclient.IZkChildListener;

import java.util.List;

/**
 * Created by Namhwik on 2017/7/20.
 */
public class ZkChildrenListener implements IZkChildListener {
    @Override
    public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
        System.out.println("ZK listener ....."+parentPath +"has changed :" +currentChilds.toString());
    }
}
