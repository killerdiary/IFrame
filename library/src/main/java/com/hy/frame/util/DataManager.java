package com.hy.frame.util;

import com.hy.frame.common.IApplication;

import java.util.HashMap;
import java.util.Map;

public class DataManager implements IApplication.IDataCache {

    /**
     * 全局数据
     */
    private Map<String, Object> maps = null;

    @Override
    public void putValue(String key, Object value) {
        if (maps == null) {
            maps = new HashMap<>();
        }
        if (value == null)
            maps.remove(key);
        else
            maps.put(key, value);
    }

    @Override
    public Object getValue(String key) {
        return maps != null ? maps.get(key) : null;
    }

    @Override
    public void clear() {
        if (maps != null)
            maps.clear();
        maps = null;
    }
}
