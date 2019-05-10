package ficus.suitcase.designpattern.template;

public class Football extends Game {
    @Override
    public void initailize() {
        System.out.println("Football Game initailized ");
    }

    @Override
    public void startPlay() {
        System.out.println("Football Game started enjoy the Game!");
    }

    @Override
    public void endPlay() {
        System.out.println("Football Game finished!");
    }
}
