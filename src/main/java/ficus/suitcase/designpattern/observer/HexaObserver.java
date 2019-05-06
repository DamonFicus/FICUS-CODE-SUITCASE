package ficus.suitcase.designpattern.observer;

public class HexaObserver extends Observer {

    public HexaObserver(Subject subject){
        this.subject=subject;
        this.subject.attatch(this);
    }

    @Override
    public void update() {
        System.out.println( "Hex String: "
                + Integer.toHexString( subject.getState() ).toUpperCase() );
    }
}
