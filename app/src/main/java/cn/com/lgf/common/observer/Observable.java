package cn.com.lgf.common.observer;

import android.os.Bundle;
import android.text.TextUtils;
import cn.com.lgf.common.debug.DebugLog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author: lgf
 * @date: 2022/3/10
 */
public class Observable {

    protected HashMap<String, ArrayList<WeakReference<Observer>>> observerMap = new HashMap();

    /**
     * @param tag      TAG标识
     * @param observer
     */
    public void addObserver(String tag, Observer observer) {
        if (TextUtils.isEmpty(tag)) {
            throw new NullPointerException("Observer tag can't be null");
        }
        if (observer == null) {
            throw new NullPointerException("observer == null");
        }
        synchronized (Observable.class) {
            if (observerMap.containsKey(tag)) {
                ArrayList<WeakReference<Observer>> listOfObserver = observerMap.get(tag);
                if (listOfObserver != null) {
                    boolean needToAdd = true;
                    for (WeakReference<Observer> weakReference : listOfObserver) {

                        if (weakReference != null && weakReference.get() != null && weakReference.get() == observer) {
                            needToAdd = false;
                            break;
                        }
                    }
                    if (needToAdd) {
                        listOfObserver.add(new WeakReference<>(observer));
                    }
                    observerMap.put(tag, listOfObserver);
                    return;
                }
            }
            ArrayList<WeakReference<Observer>> listOfObserver = new ArrayList<>();
            listOfObserver.add(new WeakReference<>(observer));
            observerMap.put(tag, listOfObserver);
        }
    }

    /***
     *
     * @param tag TAG标识
     * @return true 删除成功 false 删除失败
     */
    public boolean removeObserverByTag(String tag) {
        synchronized (Observable.class) {
            return observerMap.containsKey(tag) && observerMap.remove(tag) != null;
        }
    }

    /***
     *
     * @param tag TAG标识
     * @param observer
     * @return true 删除成功 false 删除失败
     */
    public boolean removeObserver(String tag, Observer observer) {
        if (!observerMap.containsKey(tag)) {
            return false;
        }
        ArrayList<WeakReference<Observer>> list = observerMap.get(tag);
        if (list == null || list.isEmpty()) {
            return false;
        }
        synchronized (Observable.class) {
            WeakReference<Observer> removeTag = null;
            for (WeakReference<Observer> weakReference : list) {
                if (weakReference != null && weakReference.get() != null && weakReference.get() == observer) {
                    removeTag = weakReference;
                    break;
                }
            }
            return removeTag != null && list.remove(removeTag);
        }
    }

    /**
     * 清空操作
     */
    public void removeAllObserver() {
        synchronized (Observable.class) {
            observerMap.clear();
        }
    }

    /**
     * @param tag    TAG标识
     * @param bundle 监听数据
     */
    public void sendToTarget(String tag, Bundle bundle) {
        if (!observerMap.containsKey(tag)) {
            return;
        }
        ArrayList<WeakReference<Observer>> list = observerMap.get(tag);
        if (list == null) {
            return;
        }
        ArrayList<WeakReference<Observer>> cloneList = (ArrayList<WeakReference<Observer>>) list.clone();
        if (cloneList == null) {
            return;
        }
        synchronized (Observable.class) {
            try {
                for (WeakReference<Observer> observerWeakReference : cloneList) {
                    if (observerWeakReference != null) {
                        Observer observerResult = observerWeakReference.get();
                        if (observerResult != null) {
                            observerResult.notifyChanged(this, tag, bundle);
                        }
                    }
                }

            } catch (Exception e) {
                DebugLog.e(Observable.class.getName(), e);
            }
        }
    }

    /**
     * @param tag TAG标识
     */
    public void sendToTarget(String tag) {
        this.sendToTarget(tag, null);
    }
}
