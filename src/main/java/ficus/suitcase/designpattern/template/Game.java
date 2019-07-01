package ficus.suitcase.designpattern.template;

/**
 * 模板方法模式，定义算法骨架
 * 对于有执行步骤，顺序工序的可以统一抽象出来，约定子类的行为；
 */
public abstract class Game {
    public abstract void initailize();
    public abstract void startPlay();
    public abstract void endPlay();
    public final void play(){
        initailize();
        startPlay();
        endPlay();
    }
}
