package com.hy.frame.util;

import android.os.Process;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * title 日志
 * author heyan
 * time 19-7-8 下午4:05
 * desc 无
 */
public final class LogUtil {
    private static final String TAG = "HyLog";
    private static final long MAX_LOG_FILE = 1024L * 1024 * 8; //8MB

    public static void i(Object msg) {
        i("", msg);
    }

    public static void i(Class<?> cls, Object msg) {
        i(cls.getSimpleName(), msg);
    }

    public static void i(String tag, Object msg) {
        println(Log.INFO, tag, msg);
    }

    public static void d(Object msg) {
        d("", msg);
    }

    public static void d(Class<?> cls, Object msg) {
        d(cls.getSimpleName(), msg);
    }

    public static void d(String tag, Object msg) {
        println(Log.DEBUG, tag, msg);
    }

    public static void w(Object msg) {
        w("", msg);
    }

    public static void w(Class<?> cls, Object msg) {
        w(cls.getSimpleName(), msg);
    }

    public static void w(String tag, Object msg) {
        println(Log.WARN, tag, msg);
    }

    public static void e(Object msg) {
        e("", msg);
    }

    public static void e(Class<?> cls, Object msg) {
        e(cls.getSimpleName(), msg);
    }

    public static void e(String tag, Object msg) {
        println(Log.ERROR, tag, msg);
    }

    private static ILogger impl = null;

    /**
     * 日志输出
     *
     * @param priority 级别
     * @param tag      标签
     * @param msg      信息
     */
    private static void println(int priority, String tag, Object msg) {
        if (impl == null) impl = new IMPL();
        impl.println(priority, tag, msg);
    }

    /**
     * 开启日志print
     *
     * @param enable 是否开启日志
     */
    public static void enableLog(boolean enable) {
        if (impl == null) impl = new IMPL();
        impl.setLoggable(enable);
    }

    /**
     * 开启文件日志记录
     *
     * @param logDir 日志文件目录
     */
    public static void enableFileLog(String logDir) {
        if (impl == null) impl = new IMPL();
        impl.setLogDir(logDir);
    }

    public static void setLogger(ILogger logger){

    }

    interface ILogger {
        void println(int priority, String tag, Object msg);

        boolean isLoggable();

        void setLoggable(boolean enable);

        boolean isEnableFile();

        void setEnableFile(boolean enableFile);

        String getLogDir();

        void setLogDir(String logDir);

        File getLogFile();
    }

    static final class IMPL implements ILogger {
        /**
         * 是否开启日志
         */
        private boolean isLoggable = false;
        /**
         * 是否开启日志文件功能
         */
        private boolean isEnableFile = false;
        /**
         * 日志目录
         */
        private String logDir = null;
        /**
         * 当前日志文件
         */
        private File logFile = null;

        @Override
        public boolean isLoggable() {
            return isLoggable;
        }

        @Override
        public void setLoggable(boolean loggable) {
            isLoggable = loggable;
        }

        public boolean isEnableFile() {
            return isEnableFile;
        }

        public void setEnableFile(boolean enableFile) {
            isEnableFile = enableFile;
        }

        public String getLogDir() {
            return logDir;
        }

        @Override
        public void setLogDir(String logDir) {
            this.logDir = logDir;
            this.isEnableFile = logDir != null && logDir.length() > 0;
        }

        public File getLogFile() {
            return logFile;
        }

        private String levelToStr(int level) {
            switch (level) {
                case Log.VERBOSE:
                    return "V";
                case Log.DEBUG:
                    return "D";
                case Log.INFO:
                    return "I";
                case Log.WARN:
                    return "W";
                case Log.ERROR:
                    return "E";
                case Log.ASSERT:
                    return "A";
                default:
                    return "UNKNOWN";
            }
        }

        private File createNewLogFile(String dir) {
            String fileName = String.format("LOG_%s_%s.log", DateUtil.getNowTime("yyyyMMddHHmmss"), Process.myPid());
            File file = null;
            try {
                file = new File(dir, fileName);
                if (!file.exists()) {
                    if (file.createNewFile()) return file;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return file;
        }

        @Override
        public void println(int priority, String tag, Object msg) {
            if (isLoggable) {
                if (tag != null && tag.length() > 0)
                    Log.println(priority, TAG, String.format("%s: %s", tag, String.valueOf(msg)));
                else
                    Log.println(priority, TAG, String.valueOf(msg));
            }
            if (isEnableFile) {
                if (logDir == null) return;
                PrintWriter writer = null;
                try {
                    if (logFile == null || logFile.length() > MAX_LOG_FILE) logFile = createNewLogFile(logDir);
                    writer = new PrintWriter(new FileWriter(logFile, true));
                    String data = String.format("%s %s-%s %s/%s %s", DateUtil.getNowTime(), Process.myPid(), Process.myUid(), levelToStr(priority), tag, msg);
                    writer.println(data);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }
}
