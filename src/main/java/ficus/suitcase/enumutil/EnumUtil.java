package ficus.suitcase.enumutil;

/**
 * Created by 01333346 on 2018/10/9.
 */
public class EnumUtil {

    /**
     * 返回指定编码的'枚举'
     * @param code
     * @return SharedObjTypeEnum
     * @throws
     */
    public static <T extends CommonEnum> T getEnumBycode(Class<T> clazz, int code) {
        for(T _enum : clazz.getEnumConstants())
            if(code == _enum.getCode())
                return _enum;
        return null;
    }

    /**
     * 返回指定名称的'枚举'
     * @param name
     * @return SharedObjTypeEnum
     * @throws
     */
    public static <T extends CommonEnum> T getEnumByName(Class<T> clazz, String name) {
        for(T _enum : clazz.getEnumConstants())
            if(_enum.getName().equals(name)) {
                return _enum;
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
        for(T _enum : clazz.getEnumConstants())
            if(_enum.getDesc().equals(desc)) {
                return _enum;
            }
        return null;
    }


}
