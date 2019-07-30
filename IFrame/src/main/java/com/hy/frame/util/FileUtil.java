package com.hy.frame.util;

import android.content.Context;
import android.os.Build;
import android.os.StatFs;

import java.io.File;
import java.text.DecimalFormat;

/**
 * title 文件工具类
 * author heyan
 * time 19-7-9 上午11:49
 * desc 无
 */
public final class FileUtil {
    public static final String DIR_CACHE = "CFrame";

    public static String getCachePath(Context cxt, String dirType) {
        File fDir = null;
//        try {
//            fDir = cxt.getExternalFilesDir(DIR_CACHE);
//            if (fDir != null && !fDir.exists()) fDir.mkdirs();
//        } catch (Exception ignored) {
//        }
        try {
            if (fDir == null) {
                File dir = cxt.getFilesDir();
                if (dir == null) return null;
                fDir = new File(dir.getParentFile(), DIR_CACHE);

            }
        } catch (Exception ignored) {
        }
        if (fDir == null) {
            fDir = cxt.getFilesDir();
            if (fDir == null) return null;
            if (!fDir.exists()) fDir.mkdirs();
            if (dirType == null || dirType.length() == 0) return fDir.getPath();
            File file = new File(fDir, dirType);
            // 判断文件夹存在与否，否则创建
            if (!file.exists()) file.mkdirs();
            return fDir.getPath();
        } else {
            if (!fDir.exists()) fDir.mkdirs();
            if (dirType == null || dirType.length() == 0) return fDir.getPath();
            return fDir.getPath();
        }
    }

    /**
     * 获取网页缓存路径
     */
    public static String getCachePathWeb(Context cxt) {
        return getCachePath(cxt, "Web");
    }

    /**
     * 获取下载缓存路径
     */
    public static String getCachePathDownload(Context cxt) {
        return getCachePath(cxt, "Download");
    }

    /**
     * 获取图片缓存路径
     */
    public static String getCachePathAlbum(Context cxt) {
        return getCachePath(cxt, "Album");
    }

    /**
     * 获取音视频缓存路径
     */
    public static String getCachePathMedia(Context cxt) {
        return getCachePath(cxt, "Media");
    }

    /**
     * 获取数据库频缓存路径
     */
    public static String getCachePathDb(Context cxt) {
        return getCachePath(cxt, "Db");
    }


    /**
     * 获取缓存大小
     *
     * @param cxt 上下文
     */
    public static String getCacheSize(Context cxt) {
        // 取得sdcard文件路径
        String dir = getCachePath(cxt, null);
        if (dir == null) return "0M";
        long size = getDirSize(dir);
        return formatFileSizeAuto(size);
    }

    /**
     * 清空缓存 耗时操作
     *
     * @param cxt 上下文
     */
    public static boolean clearCache(Context cxt) {
        return clearDir(getCachePath(cxt, null), true);
    }


    /**
     * Access to a directory available size.
     * 获取指定目录可用的内存大小
     *
     * @param path path.
     * @return Long size.
     */
    public static long getDirAvailableSize(String path) {
        if (path == null) return 0L;
        try {
            StatFs stat = new StatFs(path);
            long blockSize;
            long availableBlocks;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.getBlockSizeLong();
                availableBlocks = stat.getAvailableBlocksLong();
            } else {
                blockSize = stat.getBlockSize();
                availableBlocks = stat.getAvailableBlocks();
            }
            return availableBlocks * blockSize;
        } catch (Exception ignored) {
            return 0L;
        }
    }

    /**
     * 获取指定目录已用的内存大小
     *
     * @param path path.
     * @return Long size.
     */
    public static long getDirSize(String path) {
        if (path == null) return 0L;
        return getDirSizeImpl(new File(path));
    }

    /**
     * 获取指定目录已用的内存大小
     *
     * @param f 文件夹
     * @return Long size.
     */
    public static long getDirSizeImpl(File f) {
        if (f == null) return 0L;
        long size = 0L;
        try {
            if (f.isDirectory()) {
                File[] files = f.listFiles();
                if (files != null && files.length > 0)
                    for (File item : files) {
                        size += getDirSizeImpl(item);
                    }
            } else {
                size += f.length();
            }
        } catch (Exception ignored) {

        }
        return size;
    }

    public static final int SIZE_TYPE_B = 1;// 获取文件大小单位为B
    public static final int SIZE_TYPE_KB = 2;// 获取文件大小单位为KB
    public static final int SIZE_TYPE_MB = 3;// 获取文件大小单位为MB
    public static final int SIZE_TYPE_GB = 4;// 获取文件大小单位为GB

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param size     文件大小
     * @param sizeType 指定需要的结果单位 SIZE_TYPE_*
     * @return String "#.00"
     */
    public static String formatFileSize(long size, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        switch (sizeType) {
            case SIZE_TYPE_B:
                return df.format(size) + "B";
            case SIZE_TYPE_KB:
                return df.format(((double) size) / 1024L) + "K";
            case SIZE_TYPE_MB:
                return df.format(((double) size) / (1024L * 1024)) + "M";
            case SIZE_TYPE_GB:
                return df.format(((double) size) / (1024L * 1024 * 1024)) + "G";
            default:
                return "0.00K";
        }
    }

    /**
     * 转换文件大小,自动判断转换的类型
     *
     * @param size 文件大小
     * @return String "#.00"
     */
    public static String formatFileSizeAuto(long size) {
        if (size < 1024L) {
            return formatFileSize(size, SIZE_TYPE_B);
        } else if (size < (1024L * 1024)) {
            return formatFileSize(size, SIZE_TYPE_KB);
        } else if (size < (1024L * 1024 * 1024)) {
            return formatFileSize(size, SIZE_TYPE_MB);
        } else {
            return formatFileSize(size, SIZE_TYPE_GB);
        }
    }

    /**
     * 清空目录 耗时
     *
     * @param path   path.
     * @param ignore 忽略当前文件
     * @return Long size.
     */
    public static boolean clearDir(String path, boolean ignore) {
        if (path == null) return true;
        File f = null;
        try {
            f = new File(path);
        } catch (Exception ignored) {

        }
        if (f != null) {
            if (f.isDirectory()) {
                File[] files = f.listFiles();
                if (files != null && files.length > 0)
                    for (File item : files) {
                        clearDirImpl(item);
                    }
            }
            if (!ignore) {
                try {
                    f.delete();
                } catch (Exception ignored) {

                }
            }
        }
        return true;
    }

    /**
     * 清空目录
     *
     * @param f 文件夹
     * @return Long size.
     */
    public static boolean clearDirImpl(File f) {
        if (f == null) return true;
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null && files.length > 0)
                for (File item : files) {
                    clearDirImpl(item);
                }
        }
        try {
            f.delete();
        } catch (Exception ignored) {

        }
        return true;
    }

}
