package cn.com.lgf.common.observer;

import android.os.Bundle;

/**
 * @author: lgf
 * @date: 2022/3/10
 */
public interface Observer {
    /**
     * @param observable
     * @param bundle
     */
    void notifyChanged(Observable observable, String tag, Bundle bundle);
}
