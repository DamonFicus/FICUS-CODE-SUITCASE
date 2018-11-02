package leetcode;

/**
 * Created by DamonFicus on 2018/10/15.
 * 判断回文数
 * Determine whether an integer is a palindrome.
 * An integer is a palindrome when it reads the same backward as forward.
 */
public class Plindrome {
    /**
     * Example 1:
     * Input: 121
     * Output: true
     * Example 2:
     * Input: -121
     Output: false
     Explanation: From left to right, it reads -121.
     From right to left, it becomes 121-. Therefore it is not a palindrome.
     */
    public boolean isPalindrome(int x) {
        //思路一：直接正逆序比较数据串是否是一致
        String numStr=new String(""+x);
        //思路二：看前后对称位置上的数值是否一致
        return numStr.equals(new StringBuilder(numStr).reverse().toString());
    }


    public String reverse(String str){
        StringBuilder sb = new StringBuilder(str);
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        Plindrome plindrome = new Plindrome();
        System.out.println(plindrome.isPalindrome(121));
        System.out.println(plindrome.isPalindrome(-121));
    }

}
