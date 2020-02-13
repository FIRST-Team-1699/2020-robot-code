package team1699.utils.sensors;

public class BeamBreak {

    //TODO Check state names
    enum BeamState{
        BROKEN,
        CLOSED
    }

    private final int port;

    public BeamBreak(final int port){
        this.port = port;
    }
}
