package ficus.suitcase.designpattern.template;

public class Criket extends Game {
    @Override
    public void initailize() {
        System.out.println("Criket Game initailized ");
    }

    @Override
    public void startPlay() {
        System.out.println("Criket Game started enjoy the Game!");
    }

    @Override
    public void endPlay() {
        System.out.println("Criket Game finished!");
    }
}
