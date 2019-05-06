package ficus.suitcase.designpattern.observer;

public class OctalObserver extends Observer {

    public OctalObserver(Subject subject){
        this.subject=subject;
        this.subject.attatch(this);
    }

    @Override
    public void update() {
        System.out.println( "Octal String: "
                + Integer.toOctalString( subject.getState() ) );
    }
}
