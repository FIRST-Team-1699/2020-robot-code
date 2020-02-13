package team1699.subsystems;

public class Hopper {

    enum HopperState{
        MOVING_FORWARD,
        MOVING_BACKWARD,
        STOPPED
    }

    //TODO Need to figure out if the flipper is part of this subsystem or another one

    public HopperState currentState;
    public HopperState wantedState;

    //TODO Add motors
    public Hopper(){
        wantedState = HopperState.STOPPED;
    }

    public void update(){
        if(wantedState == currentState){
            return;
        }

        //TODO Handle state transitions
    }

    public void setWantedState(final HopperState wantedState) {
        this.wantedState = wantedState;
    }

    public HopperState getWantedState() {
        return wantedState;
    }
}
