package ficus.suitcase.designpattern.command;

public class Car implements Command {
    @Override
    public void execute() {
        System.out.println("car on travlling to tibet!!!");
    }
}
