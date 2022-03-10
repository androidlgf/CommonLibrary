package cn.com.lgf.common.observer;

import android.util.SparseArray;
import cn.com.lgf.common.debug.DebugLog;

/**
 * @author: lgf
 * @date: 2022/3/10
 */
public class ObservableManager {
    private static final String TAG = "ObservableManager";

    private static ObservableManager sInstance = null;

    private SparseArray<Observable> mObservableSparseArray;

    public synchronized static ObservableManager getInstance() {

        if (sInstance == null) {
            sInstance = new ObservableManager();
        }
        return sInstance;
    }

    private ObservableManager() {
        mObservableSparseArray = new SparseArray<>();
    }

    public <T extends Observable> T getObservable(Class<T> t) {

        Observable observable = mObservableSparseArray.get(t.hashCode());
        if (observable == null) {
            try {
                observable = t.newInstance();
                mObservableSparseArray.put(t.hashCode(), observable);
            } catch (InstantiationException e) {
                DebugLog.e(TAG, e.getMessage());
            } catch (IllegalAccessException e) {
                DebugLog.e(TAG, e.getMessage());
            }
        }
        // 仅在内部使用一次强制类型转换，外部调用者不再需要强制类型转换
        return (T) observable;
    }

    /***
     *
     * @return  Observable
     */
    public static Observable getDefaultObservable() {
        return ObservableManager.getInstance().getObservable(Observable.class);
    }
}
