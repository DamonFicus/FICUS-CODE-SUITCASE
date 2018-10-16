package ficus.suitcase.fileio;

import ficus.suitcase.enumutil.EncodingType;

import java.util.Collection;
import java.util.Collections;

/**
 * String常用判断工具
 * @author DamonFicus
 */
public class StringUtils {
    /**
     * 是否为空字符串
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return null == str || str.trim().length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !StringUtils.isEmpty(str);
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    /**
     * 获取ftp文件名
     * @param path
     * @return
     */
    public static String getFtpFileName(String path) {
        /**split里面必须是正则表达式，"\\"的作用是对字符串转义*/
        String temp[] = path.split("/");
        return temp[temp.length - 1];
    }

    /**
     * 空字符串对象转为""
     * @param str
     * @return
     */
    public static String nullToEmpty(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }


    /**
     * 方法说明：集合转换到List空对象<br>
     * @param list 集合类
     * @return  T
     */
    public static <T> Collection<T> nullToEmptyList(Collection<T> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return list;
    }

    /**
     * 方法说明：按照指定的最大长度截取字符串<br>
     * @param str
     * @param maxLen
     * @return
     */
    public static String trimStr(String str, int maxLen) {
        try {
            if (org.apache.commons.lang3.StringUtils.isBlank(str) ||
                    (str.getBytes(EncodingType.UTF_8.getText()).length <= maxLen) ) {
                return str;
            }
            StringBuilder strB = new StringBuilder();
            char[] cs = str.toCharArray();
            int len = 0;
            for (char c : cs) {
                len += String.valueOf(c).getBytes(EncodingType.UTF_8.getText()).length;
                if (len > maxLen) {
                    break;
                }
                strB.append(c);
            }
            return strB.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }



}
