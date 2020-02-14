package team1699.subsystems;

import team1699.utils.sensors.BeamBreak;

public class Hopper {

    //TODO Need to rework states so only moves forward when allowed
    enum HopperState{
        MOVING_FORWARD,
        MOVING_BACKWARD,
        STOPPED
    }

    //TODO Need to figure out if the flipper is part of this subsystem or another one

    private HopperState currentState;
    private HopperState wantedState;
    private final BeamBreak intakeBreak, ballBreak;
    private int mBallsStored;

    //TODO Add motors
    public Hopper(final BeamBreak intakeBreak, final BeamBreak ballBreak){
        wantedState = HopperState.STOPPED;
        this.intakeBreak = intakeBreak;
        this.ballBreak = ballBreak;
    }

    public void update(){
        if(wantedState == currentState){
            runSubsystem();
            return;
        }

        if(wantedState == HopperState.STOPPED){
            handleStoppedTransition();
        }else if(wantedState == HopperState.MOVING_FORWARD){
            handleMovingForwardTransition();
        } else if (wantedState == HopperState.MOVING_BACKWARD) {
            handleMovingBackwardTransition();
        }

        runSubsystem();
    }

    private void runSubsystem(){
        switch(currentState){
            case STOPPED:
                //TODO Set all motors to speed zero
                break;
            case MOVING_FORWARD:
                //TODO Run motors when ball are in the correct position
                if(intakeBreak.triggered()){ //TODO Rework the switch def changes to states
                    //Move motors until ball break is triggered
                    new Runnable(){ //TODO Figure out if this stops the method from competing
                        @Override
                        public void run() {
                            //Need to build beam break to be more of a one time switch
                            while(!ballBreak.triggered()){
                                //Move forward
                            }
                            //TODO Turn off motor
                            //This is prob not thread safe
                        }
                    };
                }
                break;
            case MOVING_BACKWARD:
                //TODO Run motors backwards
                break;
            default:
                //TODO Handle invalid system state
                break;
        }
    }

    private void handleStoppedTransition(){

    }

    private void handleMovingForwardTransition(){

    }

    private void handleMovingBackwardTransition(){

    }

    /**
     * Removed a ball from the system. For example if a ball is fired, this should be called
     */
    public void removeBall(){
        //TODO Rework subsystems to remove the need for this
        mBallsStored--;
    }

    public void setWantedState(final HopperState wantedState) {
        this.wantedState = wantedState;
    }

    public HopperState getCurrentState() {
        return currentState;
    }
}
