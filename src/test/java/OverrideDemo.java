/**
 * 对父类方法进行覆盖时
 * 无论是否是抽象还是常规的方式，都可以进行重写；
 * 验证对于相同的switch判断在多个类中均有时，可以
 * 将判断提取到上层进行，子类如果有多加入的判断，
 * 某个特定类的switch判断新增则只需要在子类进行重写，
 * 如果是整个共用的switch判断做了扩展，也只需要在父类中统一进行处理
 * 而不必在每个类的swith中都进行修改；
 * @author DamonFicus
 */
public class OverrideDemo {
    public static void main(String[] args) {
        Person ps = new ConCreatePerson();
        System.out.println(ps.test(1));
    }
}

