package ficus.suitcase.designpattern.command;

/**
 * 命令模式：
 * 将“请求”封装成对象，以便使用不同的请求，队列，日志来参数化其他对象，
 * 命令模式也支持可撤销的操作；
 *
 * 将一个请求封装成一个对象，从而使您可以用不同的请求对客户进行参数化
 *
 * 在软件系统中，行为请求者与行为实现者通常是一种紧耦合的关系，
 * 但某些场合，比如需要对行为进行记录、撤销或重做、事务等处理时，
 * 这种无法抵御变化的紧耦合的设计就不太合适。
 *
 */
public class ClientCommand {

    public static void main(String[] args) {

//        Lighter lighter = new Lighter();
//        CommanderDemo cd = new CommanderDemo(lighter);
//        cd.doCommand();


          Car landCrusier = new Car();
          CommanderDemo cd = new CommanderDemo(landCrusier);
          cd .doCommand();
    }
}
