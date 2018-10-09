package ficus.suitcase.enumutil;

/**
 * Created by DamonFicus on 2018/10/9.
 * @author DamonFicus
 */
public class EnumUtilDemo {
    public static void main(String[] args) {
        StatusEnum semByCode = EnumUtil.getEnumBycode(StatusEnum.class, 1);
        System.out.println(semByCode);
        StatusEnum semByName = EnumUtil.getEnumByName(StatusEnum.class, "WAITTING");
        System.out.println(semByName);
        StatusEnum semByDesc = EnumUtil.getEnumByDesc(StatusEnum.class, "结束");
        System.out.println(semByDesc);
    }
}
