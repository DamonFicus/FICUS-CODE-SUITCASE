package ficus.suitcase.fileio;

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

}
