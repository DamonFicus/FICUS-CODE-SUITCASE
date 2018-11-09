package ficus.suitcase.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author DamonFicus
 * 正则regex
 */
public class RegexDemo {

    /**
     * QQ：^[1-9]\d{4,10}$
     * 邮箱：^[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\.){1,3}[a-zA-z\-]{1,}$
     * 用户名（字母开头 + 数字/字母/下划线）：^[A-Za-z][A-Za-z1-9_-]+$
     * URL：^((http|https)://)?([\w-]+\.)+[\w-]+(/[\w-./?%&=]*)?$
     * @param args
     */
    public static void main(String[] args) {
        String oriSource="13544006231";
        String regEx="^((0\\d{2,3}\\d{7,8})|(1[3-9]\\d{9}))$";
        Pattern pattern =Pattern.compile(regEx);
        Matcher matcher= pattern.matcher(oriSource);
        Boolean result=matcher.matches();
        System.out.println(result);
    }

}
