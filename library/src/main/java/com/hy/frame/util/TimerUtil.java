package com.hy.frame.util;

import android.app.Activity;
import android.content.Context;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * title 定时器
 * author heyan
 * time 19-7-12 下午4:38
 * desc 主线程
 */
public final class TimerUtil implements MyHandler.HandlerListener {

    private final WeakReference<Context> wcxt;
    private MyHandler handler = null;
    private ICallback callback = null;
    private long milliseconds;

    public TimerUtil(Context cxt) {
        if (cxt != null) this.wcxt = new WeakReference<>(cxt);
        else this.wcxt = null;
        if (cxt != null)
            this.handler = new MyHandler(cxt, this);
    }

    @Override
    public void handleMessage(Message msg) {
        if (this.wcxt == null) return;
        Context cxt = this.wcxt.get();
        if (cxt == null) return;
        if (!PmUtil.isContextExisted(cxt)) return;
        if (this.callback == null) return;
        this.callback.doNext();
        if (handler != null && milliseconds > 0)
            handler.sendEmptyMessageDelayed(0, milliseconds);
    }

    /**
     * 取消订阅
     */
    public void cancel() {
        this.callback = null;
        this.milliseconds = 0;
    }

    /**
     * milliseconds毫秒后执行next操作
     *
     * @param milliseconds 毫秒
     * @param callback     回调
     */
    public void delayed(long milliseconds, ICallback callback) {
        this.callback = callback;
        if (callback == null) return;
        if (handler == null) return;
        this.milliseconds = 0;
        handler.sendEmptyMessageDelayed(0, milliseconds);
    }


    /**
     * 每隔milliseconds毫秒后执行
     *
     * @param milliseconds 间隔时间
     * @param callback     回调
     */
    public void interval(long milliseconds, ICallback callback) {
        this.callback = callback;
        if (callback == null) return;
        if (handler == null) return;
        this.milliseconds = milliseconds;
        handler.sendEmptyMessageDelayed(0, milliseconds);
    }

    public interface ICallback {
        void doNext();
    }
}
