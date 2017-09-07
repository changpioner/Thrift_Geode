package nam.service;

import org.I0Itec.zkclient.DataUpdater;

/**
 * Created by Namhwik on 2017/7/21.
 */
public class ZkUpdate implements DataUpdater {
    @Override
    public Object update(Object currentData) {
        return currentData;
    }
}
