package com.hy.frame.util;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Pattern;

/**
 * title 无
 * author heyan
 * time 19-7-10 上午10:29
 * desc 无
 */
public final class FormatUtil {
    /**
     * 是否包含特殊符号
     */
    public static boolean isContainSpecialSymbols(String d) {
        if (d == null) return false;
        return !Pattern.compile("[。，、：；？！‘’“”′.,﹑:;?!'\"〝〞＂+\\-*=<_~#$&%‐﹡﹦﹤＿￣～﹟﹩﹠﹪@﹋﹉﹊｜‖^·¡…︴﹫﹏﹍﹎﹨ˇ¨¿—/（）〈〉‹›﹛﹜『』〖〗［］《》〔〕{}」【】︵︷︿︹﹁﹃︻︶︸﹀︺︾ˉ﹂﹄︼]").matcher(d).matches();
    }

    /**
     * 是否是图片地址
     */
    public static boolean isImagePath(String d) {
        if (d == null) return false;
        return d.matches("(?i).+?\\.(png|jpg|gif|bmp)");
    }

    /**
     * 手机号验证 模糊验证
     */
    public static boolean isMobileNumber(String d) {
        if (d == null) return false;
        return d.matches("^[1][3-9][0-9]{9}$");
    }

    /**
     * 手机号验证 精确验证 慎用
     */
    public static boolean isExactMobileNumber(String d) {
        if (d == null) return false;
        return d.matches("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");
    }

    /**
     * 电话号码验证(手机号码和座机号);
     */
    public static boolean isPhoneNumber(String d) {
        if (isEmpty(d)) return false;
        String text = d;
        boolean b = false;
        text = text.replaceAll("-", "");
        if (text.length() == 11) {
            Pattern pattern = Pattern.compile("^[0][1-9]{2,3}[0-9]{5,10}$"); // 验证带区号的
            b = pattern.matcher(d).matches();
        } else if (d.length() <= 9) {
            Pattern pattern = Pattern.compile("^[1-9][0-9]{5,8}$"); // 验证没有区号的
            b = pattern.matcher(d).matches();
        }
        if (!b)
            return isMobileNumber(d);
        return true;
    }

    /**
     * 是否是数字
     */
    public static boolean isNumber(String d) {
        if (d == null) return false;
        return Pattern.compile("[0-9]+").matcher(d).matches();
    }

    /**
     * 是否是英文
     */
    public static boolean isEnglish(String d) {
        if (d == null) return false;
        return Pattern.compile("[a-zA-Z]+").matcher(d).matches();
    }

    /**
     * 是否是中文
     */
    public static boolean isChinese(String d) {
        if (d == null) return false;
        return Pattern.compile("^[\u4e00-\u9fa5]+$").matcher(d).matches();
    }

    /**
     * 是否是IP地址
     */
    public static boolean isIpAddress(String d) {
        if (d == null) return false;
        return Pattern.compile("(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)){3}").matcher(d).matches();
    }

    /**
     * 是否是身份证
     */
    public static boolean isIdentityNumber(String d) {
        if (d == null) return false;
        if (d.length() == 15)
            return Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0-2]\\d)|3[0-1])\\d{3}$").matcher(d).matches();
        if (d.length() == 18) {
            String text = d.toUpperCase();
            return Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0-2]\\d)|3[0-1])\\d{3}([0-9]|X)$").matcher(text).matches();
        }
        return false;
    }

    /**
     * 邮箱验证
     *
     * @return 验证通过返回true;
     */
    public static boolean isEmail(String d) {
        if (d == null) return false;
        return Pattern.compile("^([a-zA-Z0-9_\\-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$").matcher(d).matches();
    }

    /**
     * 是否是银行卡号
     */
    public static boolean isBankCard(String d) {
        if (d == null) return false;
        return Pattern.compile("^(\\d{16}|\\d{19})$").matcher(d).matches();
    }

    /**
     * 判断字符串中是否含有表情
     */
    public static boolean isEmoji(String d) {
        int len = d.length();
        boolean r = false;
        for (int i = 0; i < len; i++) {
            char hs = d.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (d.length() > 1) {
                    char ls = d.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    return true;
                }
                if (!r && d.length() > 1 && i < d.length() - 1) {
                    char ls = d.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return r;
    }

    /**
     * 去掉数中多余的0
     */
    public static String removeUselessZero(String d) {
        if (isEmpty(d)) return "0";
        String text = d;
        if (text.indexOf(".") > 0) {
            text = text.replaceAll("0+?$", "");// 去掉多余的0
            text = text.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return text;
    }


    public static float strToFloat(String d) {
        if (d != null)
            try {
                return Float.parseFloat(d);
            } catch (Exception ignored) {
            }
        return 0f;
    }

    public static double strToDouble(String d) {
        if (d != null)
            try {
                return Double.parseDouble(d);
            } catch (Exception ignored) {
            }
        return 0.0;
    }

    public static int strToInt(String d) {
        if (d != null)
            try {
                // Integer.valueOf(d).intValue();
                return Integer.parseInt(d);
            } catch (Exception ignored) {
            }
        return 0;
    }

    /**
     * 转换成Money格式
     */
    public static String formatToMoney(Object obj) {
        if (obj != null)
            try {
                //0 显示bai一数字du，若此位置没有数字则补 0
                //# 显示一数字， 若此位置没有数字则不显示
                //% 数字乘以 100 并在右边加上”%”号 字符
                //#.00
                return new DecimalFormat("0.00").format(obj);
            } catch (Exception ignored) {
            }
        return "0.00";
    }

    /**
     * 是否是车牌号
     */
    public static boolean isCarNumber(String d) {
        if (isNoEmpty(d))
            return Pattern.compile("^[\u4e00-\u9fa5|A-Z][A-Z][A-Z_0-9]{5}$").matcher(d).matches();
        return false;
    }


    /**
     * 是否不为空
     */
    public static boolean isNoEmpty(String d) {
        return !isEmpty(d);
    }

    /**
     * 是否为空 排除null
     */
    public static boolean isEmpty(String d) {
        if (d == null || d.length() == 0 || d.trim().length() == 0) return true;
        return d.equals("null");
    }

    /**
     * 是否不为空
     */
    public static boolean isNoEmpty(List<?> d) {
        return !isEmpty(d);
    }

    /**
     * 是否为空
     */
    public static boolean isEmpty(List<?> d) {
        if (d == null) return true;
        return d.isEmpty();
    }

    /**
     * 是否为空
     */
    public static boolean isEmpty(Object[] d) {
        if (d == null) return true;
        return d.length == 0;
    }

//    public static void main(String[] args) {
//        String[] t = null;
//        System.out.println(isEmpty(t));
//    }
}
