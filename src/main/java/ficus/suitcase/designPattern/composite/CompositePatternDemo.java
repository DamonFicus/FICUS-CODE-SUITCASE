package ficus.suitcase.designPattern.composite;

/**
 * @author DamonFicus
 * @date 2018/10/18
 * compositePattern组合模式
 *把一组类似的对象当作一个单一的对象，组合模式依据树形结构来组合对象;
 *
 *
 * 使用场景：部分整体，如树形菜单、文件、文件夹管理
 * 实现概要：树枝和叶子实现同一接口，树枝内部组合该接口
 * 关键代码：树枝内部组合该接口，并且含有内部属性List,里面存放Component
 * 优点：高层模块调用简单，节点自由增加
 * 缺点：在使用组合模式时，树枝和叶子都是实现类而不是接口，违反了依赖倒置原则
 * 应用实例：
 * 1、算术表达式包括操作数、操作符和另一个操作数，其中，另一个操作符也可以是操作数、操作符和另一个操作数。
 * 2、在 JAVA AWT 和 SWING 中，对于 Button 和 Checkbox 是树叶，Container 是树枝。
 * 3、杀毒软件，不仅可以对文件杀毒，也可以对文件目录进行杀毒，不同格式的文件进行处理；
 * 也会用到这种树形结构的组合模式处理；
 */
public class CompositePatternDemo {

    public static void main(String[] args) {
        Employee CEO = new Employee("John","CEO", 30000);

        Employee headSales = new Employee("Robert","Head Sales", 20000);

        Employee headMarketing = new Employee("Michel","Head Marketing", 20000);

        Employee clerk1 = new Employee("Laura","Marketing", 10000);
        Employee clerk2 = new Employee("Bob","Marketing", 10000);

        Employee salesExecutive1 = new Employee("Richard","Sales", 10000);
        Employee salesExecutive2 = new Employee("Rob","Sales", 10000);

        CEO.add(headSales);
        CEO.add(headMarketing);

        headSales.add(salesExecutive1);
        headSales.add(salesExecutive2);

        headMarketing.add(clerk1);
        headMarketing.add(clerk2);

        //打印该组织的所有员工
        System.out.println(CEO);
        for (Employee headEmployee : CEO.getSubordinates()) {
            System.out.println(headEmployee);
            for (Employee employee : headEmployee.getSubordinates()) {
                System.out.println(employee);
            }
        }
    }
}
