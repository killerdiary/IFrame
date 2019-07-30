package com.hy.frame.util;

import android.content.Context;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * title 避免内存泄漏Handler
 * author heyan
 * time 19-7-9 上午10:33
 * desc 无
 */
public final class MyHandler extends android.os.Handler {
    private final WeakReference<Context> wcxt;
    private final HandlerListener listener;

    public MyHandler(Context cxt, HandlerListener listener) {
        if (cxt != null) this.wcxt = new WeakReference<>(cxt);
        else this.wcxt = null;
        this.listener = listener;
    }

    @Override
    public void handleMessage(Message msg) {
        if (this.wcxt == null) return;
        Context cxt = this.wcxt.get();
        if (cxt != null && listener != null) {
            listener.handleMessage(msg);
        }
    }

    public interface HandlerListener {
        void handleMessage(android.os.Message msg);
    }
}
