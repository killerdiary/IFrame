package com.hy.frame.common;

import android.view.View;

/**
 * title 图片加载器接口
 * author heyan
 * time 19-7-9 上午11:11
 * desc 无
 */
public interface IImageLoader {
    void load(View v, CharSequence url);

    /**
     * @param v 如果是 ImageView 设置src，否则 background
     */
    void load(View v, ImageInfo info);

    void onPause();

    void onResume();

    void onDestroy();

    class ImageInfo {
        private CharSequence url = null;
        private int loading = 0;
        private int fail = 0;
        private boolean isGif = false;
        private ILoadListener listener = null;

        public ImageInfo(CharSequence url) {
            this.url = url;
        }

        public ImageInfo(CharSequence url, int loading, int fail, boolean isGif) {
            this.url = url;
            this.loading = loading;
            this.fail = fail;
            this.isGif = isGif;
        }

        public ImageInfo(CharSequence url, int loading, int fail, boolean isGif, ILoadListener listener) {
            this.url = url;
            this.loading = loading;
            this.fail = fail;
            this.isGif = isGif;
            this.listener = listener;
        }

        public CharSequence getUrl() {
            return url;
        }

        public void setUrl(CharSequence url) {
            this.url = url;
        }

        public int getLoading() {
            return loading;
        }

        public void setLoading(int loading) {
            this.loading = loading;
        }

        public int getFail() {
            return fail;
        }

        public void setFail(int fail) {
            this.fail = fail;
        }

        public boolean isGif() {
            return isGif;
        }

        public void setGif(boolean gif) {
            isGif = gif;
        }

        public ILoadListener getListener() {
            return listener;
        }

        public void setListener(ILoadListener listener) {
            this.listener = listener;
        }

        public static ImageInfo createConfig(CharSequence url, int loading, int fail, boolean isGif) {
            return new ImageInfo(url, loading, fail, isGif);
        }
    }

    interface ILoadListener {
        void onLoading(View v);

        /**
         * 加载完成
         *
         * @param v   控件
         * @param obj 图片 为空时表示失败
         */
        void onLoadComplete(View v, Object obj);
    }
}
