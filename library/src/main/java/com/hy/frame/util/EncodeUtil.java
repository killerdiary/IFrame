package com.hy.frame.util;


/**
 * title EncodeUtil
 * author HeYan
 * time 20-8-15 下午2:58
 * desc 无
 */
public final class EncodeUtil {
    public static String getUUID() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * MD5加密字符串
     * @param d 待加密字符串
     * @return 加密字符串
     */
    public static String md5(String d) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(d.getBytes());//加密
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                int c = b & 0xff; //负数转换成正数
                String result = Integer.toHexString(c); //把十进制的数转换成十六进制的数
                if (result.length() < 2) {
                    sb.append(0); //让十六进制全部都是两位数
                }
                sb.append(result);
            }
            return sb.toString(); //返回加密后的密文
        } catch (Exception ignored) {
        }
        return null;
    }

}
