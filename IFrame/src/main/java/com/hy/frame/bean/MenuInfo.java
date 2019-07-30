package com.hy.frame.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * title MenuInfo
 * author heyan
 * time 19-7-10 下午3:54
 * desc 无
 */
public class MenuInfo {
    private int id;
    private int icon;
    private int title;
    private boolean isSelected;
    private Map<String, String> maps = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getValue(String key) {
        if (maps != null && maps.containsKey(key)) {
            return maps.get(key);
        }
        return null;
    }

    public void putValue(String key, String value) {
        if (maps == null)
            maps = new HashMap<>();
        maps.put(key, value);
    }
}
