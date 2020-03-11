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
    public static boolean isContainSpecialSymbols(String str) {
        if (str == null) return false;
        return !Pattern.compile("[。，、：；？！‘’“”′.,﹑:;?!'\"〝〞＂+\\-*=<_~#$&%‐﹡﹦﹤＿￣～﹟﹩﹠﹪@﹋﹉﹊｜‖^·¡…︴﹫﹏﹍﹎﹨ˇ¨¿—/（）〈〉‹›﹛﹜『』〖〗［］《》〔〕{}」【】︵︷︿︹﹁﹃︻︶︸﹀︺︾ˉ﹂﹄︼]").matcher(str).matches();
    }

    /**
     * 是否是图片地址
     */
    public static boolean isImagePath(String str) {
        if (str == null) return false;
        return str.matches("(?i).+?\\.(png|jpg|gif|bmp)");
    }

    /**
     * 手机号验证 模糊验证
     */
    public static boolean isMobileNumber(String str) {
        if (str == null) return false;
        return str.matches("^[1][3-9][0-9]{9}$");
    }

    /**
     * 手机号验证 精确验证 慎用
     */
    public static boolean isExactMobileNumber(String str) {
        if (str == null) return false;
        return str.matches("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");
    }

    /**
     * 电话号码验证(手机号码和座机号);
     */
    public static boolean isPhoneNumber(String str) {
        if (isEmpty(str)) return false;
        String text = str;
        boolean b = false;
        text = text.replaceAll("-", "");
        if (text.length() == 11) {
            Pattern pattern = Pattern.compile("^[0][1-9]{2,3}[0-9]{5,10}$"); // 验证带区号的
            b = pattern.matcher(str).matches();
        } else if (str.length() <= 9) {
            Pattern pattern = Pattern.compile("^[1-9][0-9]{5,8}$"); // 验证没有区号的
            b = pattern.matcher(str).matches();
        }
        if (!b)
            return isMobileNumber(str);
        return true;
    }

    /**
     * 是否是数字
     */
    public static boolean isNumber(String str) {
        if (str == null) return false;
        return Pattern.compile("[0-9]+").matcher(str).matches();
    }

    /**
     * 是否是英文
     */
    public static boolean isEnglish(String str) {
        if (str == null) return false;
        return Pattern.compile("[a-zA-Z]+").matcher(str).matches();
    }

    /**
     * 是否是中文
     */
    public static boolean isChinese(String str) {
        if (str == null) return false;
        return Pattern.compile("^[\u4e00-\u9fa5]+$").matcher(str).matches();
    }

    /**
     * 是否是IP地址
     */
    public static boolean isIpAddress(String str) {
        if (str == null) return false;
        return Pattern.compile("(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)){3}").matcher(str).matches();
    }

    /**
     * 是否是身份证
     */
    public static boolean isIdentityNumber(String str) {
        if (str == null) return false;
        if (str.length() == 15)
            return Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0-2]\\d)|3[0-1])\\d{3}$").matcher(str).matches();
        if (str.length() == 18) {
            String text = str.toUpperCase();
            return Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0-2]\\d)|3[0-1])\\d{3}([0-9]|X)$").matcher(text).matches();
        }
        return false;
    }

    /**
     * 邮箱验证
     *
     * @return 验证通过返回true;
     */
    public static boolean isEmail(String str) {
        if (str == null) return false;
        return Pattern.compile("^([a-zA-Z0-9_\\-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$").matcher(str).matches();
    }

    /**
     * 是否是银行卡号
     */
    public static boolean isBankCard(String str) {
        if (str == null) return false;
        return Pattern.compile("^(\\d{16}|\\d{19})$").matcher(str).matches();
    }

    /**
     * 去掉数中多余的0
     */
    public static String removeUselessZero(String str) {
        if (isEmpty(str)) return "0";
        String text = str;
        if (text.indexOf(".") > 0) {
            text = text.replaceAll("0+?$", "");// 去掉多余的0
            text = text.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return text;
    }

    /**
     * 转换成Money格式
     */
    public static String formatToMoney(Object obj) {
        if (obj != null)
            try {
                return new DecimalFormat("#.00").format(obj);
            } catch (Exception ignored) {

            }
        return "0.00";
    }

    /**
     * 是否是车牌号
     */
    public static boolean isCarNumber(String str) {
        if (isNoEmpty(str))
            return Pattern.compile("^[\u4e00-\u9fa5|A-Z][A-Z][A-Z_0-9]{5}$").matcher(str).matches();
        return false;
    }


    /**
     * 是否不为空
     */
    public static boolean isNoEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 是否为空 排除null
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || str.trim().length() == 0) return true;
        return str.equals("null");
    }

    /**
     * 是否不为空
     */
    public static boolean isNoEmpty(List<?> datas) {
        return !isEmpty(datas);
    }

    /**
     * 是否为空
     */
    public static boolean isEmpty(List<?> datas) {
        if (datas == null) return true;
        return datas.isEmpty();
    }

    /**
     * 是否为空
     */
    public static boolean isEmpty(Object[] datas) {
        if (datas == null) return true;
        return datas.length == 0;
    }

//    public static void main(String[] args) {
//        String[] t = null;
//        System.out.println(isEmpty(t));
//    }
}
