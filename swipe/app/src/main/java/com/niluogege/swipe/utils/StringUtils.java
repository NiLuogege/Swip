package com.niluogege.swipe.utils;

import android.text.TextUtils;

import java.util.List;


/**
 * Created by buffert on 17/1/20.
 * StringUitls
 */

public class StringUtils {


    public static String formatPhone(String phone) {
        if (phone.length() == 11) {
            StringBuffer sb = new StringBuffer();
            sb.append(phone.substring(0, 3));
            sb.append("****");
            sb.append(phone.substring(7));
            return sb.toString();
        }
        return "";
    }

    public static String formatPersonPhone(String phone) {
        if (!TextUtils.isEmpty(phone) && phone.length() == 11) {
            StringBuffer sb = new StringBuffer();
            sb.append(phone.substring(0, 3));
            sb.append("****");
            sb.append(phone.substring(7));
            return sb.toString();
        }
        return "未关联";
    }

    public static String formatPersonIdCardNo(String no) {
        if (!TextUtils.isEmpty(no) && no.length() == 18) {
            StringBuffer sb = new StringBuffer();
            sb.append(no.substring(0, 6));
            sb.append("*******");
            sb.append(no.substring(14));
            return sb.toString();
        }
        return "未验证";
    }

    public static String formatIdCardNo(String no) {
        if (no.length() == 18) {
            StringBuffer sb = new StringBuffer();
            sb.append(no.substring(0, 6));
            sb.append("*******");
            sb.append(no.substring(14));
            return sb.toString();
        }
        return "";
    }

    public static String formatPersonName(String name) {
        if (!TextUtils.isEmpty(name) && name.length() != 0) {
            StringBuffer sb = new StringBuffer();
            sb.append("*");
            sb.append(name.substring(1));
            return sb.toString();
        }
        return "未验证";
    }

    public static String formatName(String name) {
        if (name.length() != 0) {
            StringBuffer sb = new StringBuffer();
            sb.append("*");
            sb.append(name.substring(1));
            return sb.toString();
        }
        return "";
    }

    public static String formatBankCard(String bankCard) {
        if (bankCard.length() >= 12) {
            StringBuffer sb = new StringBuffer();
            sb.append(bankCard.substring(0, 4));
            sb.append("********");
            sb.append(bankCard.substring(12));
            return sb.toString();
        }
        return "";
    }

    /*** 全角转换为半角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /*** 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToSBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }


    public static <T extends Object> String mergeList2String(List<T> list) {
        return mergeList2String(list, ",");
    }

    public static <T extends Object> String mergeList2String(List<T> list, String sperator) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;

        if (list != null) {
            for (T i : list) {
                if (!isFirst) {
                    sb.append(sperator);
                } else {
                    isFirst = false;
                }

                sb.append(i);
            }
        }

        return sb.toString();
    }

    /**
     * 是否不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !TextUtils.isEmpty(str);
    }


    /**
     * 是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.isEmpty() || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回安全的String
     *
     * @param str
     * @return
     */
    public static String getSafeString(String str) {
        if (isEmpty(str)) {
            return "";
        } else {
            return str;
        }
    }

    /**
     * 传入值是否大于0
     *
     * @param str
     * @return
     */
    public static boolean isPriceBigThen0(String str) {
        if (isEmpty(str)) {
            return false;
        }
        if (str.contains("¥")) {
            str = str.replace("¥", "");
        }

        if (str.contains(",")) {
            str = str.replace(",", "");
        }
        return Float.parseFloat(str) > 0;
    }

    public static String[] getSplitWith$0(String str) {
        if (isNotEmpty(str) && str.contains("$0")) {
            return str.split("\\$0");
        }
        return null;
    }

    /**
     * @param str 字符串
     * @return 字符长度
     */
    public static int getWordCount(String str) {
        int length = 0;
        for (int i = 0; i < str.length(); i++) {
            int ascii = Character.codePointAt(str, i);
            if (ascii >= 0 && ascii <= 255) {
                length++;
            } else {
                length += 2;
            }
        }
        return length;
    }

    /**
     * 去除价格结尾的.0 .00
     *
     * @param price
     * @return
     */
    public static String removePriceEnd0(String price) {
        if (isNotEmpty(price)) {
            if (price.endsWith(".00")) {
                price = price.substring(0, price.length() - 3);
            } else if (price.endsWith(".0")) {
                price = price.substring(0, price.length() - 2);
            }
            return price;
        }
        return "0";
    }


    /**
     * 获取真实金额
     *
     * @return
     */
    public static String getCleanPrice(String price) {
        if (isNotEmpty(price)) {
            if (price.startsWith("¥")) {
                price = price.replace("¥", "");
            }
            return price.trim();
        }
        return "";
    }


    /**
     * 两个金额是否相等
     *
     * @return
     */
    public static boolean isPriceEquals(String p1, String p2) {
        String price1 = getCleanPrice(p1);
        String price2 = getCleanPrice(p2);
        return TextUtils.equals(price1, price2);
    }

    /**
     * 显示带 ¥ 的金额
     */
    public static String getUIPrice(String price) {
        if (isNotEmpty(price)) {
            if (!price.startsWith("¥")) {
                price = "¥" + price;
            }
        }
        return price;
    }




    /**
     * string转化为int
     * @param str
     * @return
     */
    public static int getStrToInt(String str){
        if (isNotEmpty(str)){
            try {
                return Integer.parseInt(str);
            }catch (Exception e){
                return 0;
            }
        }
        return 0;
    }
}
