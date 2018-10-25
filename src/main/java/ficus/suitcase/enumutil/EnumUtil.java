package ficus.suitcase.enumutil;

/**
 * Created by DamonFicus on 2018/10/9.
 * @author DamonFicus
 * 1.思路一是通过反射获取到对应的枚举对象
 * 2.思路二是通过自带的values方法遍历，根据name来匹配，如 A(Y,'DESC') 即根据Y来找到对应的A，然后getName拿到其描述DESC
 */
public class EnumUtil {

    /**
     * 返回指定编码的'枚举'
     * @param code
     * @return SharedObjTypeEnum
     * @throws
     */
    public static <T extends CommonEnum> T getEnumBycode(Class<T> clazz, int code) {
        for(T enumOjb : clazz.getEnumConstants()) {
            if (code == enumOjb.getCode()) {
                return enumOjb;
            }
        }
        return null;
    }

    /**
     * 返回指定名称的'枚举'
     * @param name
     * @return SharedObjTypeEnum
     * @throws
     */
    public static <T extends CommonEnum> T getEnumByName(Class<T> clazz, String name) {
        for(T enumOjb : clazz.getEnumConstants()) {
            if (enumOjb.getName().equals(name)) {
                return enumOjb;
            }
        }
        return null;
    }

    /**
     * 返回指定描述的'枚举'
     * @param desc
     * @return SharedObjTypeEnum
     * @throws
     */
    public static <T extends CommonEnum> T getEnumByDesc(Class<T> clazz, String desc) {
        for(T enumOjb : clazz.getEnumConstants()) {
            if (enumOjb.getDesc().equals(desc)) {
                return enumOjb;
            }
        }
        return null;
    }


}
