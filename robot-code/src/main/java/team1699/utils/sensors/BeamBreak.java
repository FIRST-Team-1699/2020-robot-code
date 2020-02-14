package team1699.utils.sensors;

public abstract class BeamBreak {

    //TODO Check state names
    enum BeamState{
        BROKEN,
        CLOSED,
        ERROR
    }

    private final int port;

    public BeamBreak(final int port){
        this.port = port;
    }

    public abstract BeamState triggered();

    public int getPort(){
        return port;
    }
}
