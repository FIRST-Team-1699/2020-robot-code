package team1699.subsystems;

import team1699.utils.controllers.BetterSpeedController;
import team1699.utils.sensors.BeamBreak;

public class Hopper implements Subsystem{

    public static final double FORWARD_SPEED = 1.0;
    public static final double REVERSE_SPEED = FORWARD_SPEED;

    //TODO Need to rework states so only moves forward when allowed
    enum HopperState{
        INTAKING,
        SHOOTING,
        MOVING_BACKWARD,
        STOPPED
    }

    //TODO Need to figure out if the flipper is part of this subsystem or another one

    private HopperState currentState;
    private HopperState wantedState = HopperState.STOPPED;
    private final BeamBreak intakeBreak, ballBreak, shooterBreak;
    private final BetterSpeedController carrierMotor;
    private int mBallsStored;
    private boolean lock = false; //Used to make sure that we ignore the correct time the beam is closed

    //TODO Add motors
    public Hopper(final BeamBreak intakeBreak, final BeamBreak ballBreak, final BeamBreak shooterBreak, final BetterSpeedController carrierMotor){
        wantedState = HopperState.STOPPED;
        this.intakeBreak = intakeBreak;
        this.ballBreak = ballBreak;
        this.shooterBreak = shooterBreak;
        this.carrierMotor = carrierMotor;
    }

    public void update(){
        if(wantedState == currentState){
            runSubsystem();
            return;
        }

        if(wantedState == HopperState.STOPPED){
            handleStoppedTransition();
        }else if(wantedState == HopperState.INTAKING){
            handleIntakingTransition();
        } else if(wantedState == HopperState.SHOOTING){
            handleShootingTransition();
        } else if (wantedState == HopperState.MOVING_BACKWARD) {
            handleMovingBackwardTransition();
        }

        currentState = wantedState;
        runSubsystem();
    }

    private void runSubsystem(){
        switch(currentState){
            case STOPPED:
                //TODO Set all motors to speed zero
                break;
            case INTAKING:
                //TODO Keep track of balls stored in system

                //If the beam is broken, set the motor to forward.
                if(intakeBreak.triggered() == BeamBreak.BeamState.BROKEN){
                    carrierMotor.set(FORWARD_SPEED);
                    lock = true;
                }

                //Ignore first time ballBreak is broken
                if(ballBreak.triggered() == BeamBreak.BeamState.BROKEN && lock){
                    lock = false;
                }

                //If the beam is closed, set the motor to stop.
                if(ballBreak.triggered() == BeamBreak.BeamState.CLOSED && !lock){
                    carrierMotor.set(0.0);
                }

                break;
            case SHOOTING:
                //TODO Run motors to allow shooting
                //TODO May need some sort of lock if the flipper changes beam state too often
                //Run forward until ball breaks shooter beam break
                //Wait until beam is restored
                //Repeat

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

    private void handleIntakingTransition(){

    }

    private void handleShootingTransition() {

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
