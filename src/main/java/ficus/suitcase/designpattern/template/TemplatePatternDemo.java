package ficus.suitcase.designpattern.template;

public class TemplatePatternDemo {

    public static void main(String[] args) {
        Game game =new Football();
        game.play();

        Game game1= new Criket();
        game1.play();
    }
}
