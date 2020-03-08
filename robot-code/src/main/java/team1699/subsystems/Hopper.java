package team1699.subsystems;

import team1699.utils.controllers.BetterSpeedController;
import team1699.utils.sensors.BeamBreak;
import team1699.utils.sensors.LimitSwitch;

public class Hopper implements Subsystem {

    public static final double FORWARD_SPEED = 0.35;
    public static final double REVERSE_SPEED = -FORWARD_SPEED;
    public static final byte MAX_BALLS = 4;
    private final BeamBreak intakeBreak, ballBreak;
    private final LimitSwitch shooterBreak;

    //TODO Need to figure out if the flipper is part of this subsystem or another one
    private final BetterSpeedController carrierMotor;
    private HopperState currentState, wantedState;
    private byte mBallsStored;
    private boolean lock = false; //Used to make sure that we ignore the correct time the beam is closed
    //TODO Add motors
    public Hopper(final BeamBreak intakeBreak, final BeamBreak ballBreak, final LimitSwitch shooterBreak, final BetterSpeedController carrierMotor) {
        wantedState = HopperState.STOPPED;
        this.intakeBreak = intakeBreak;
        this.ballBreak = ballBreak;
        this.shooterBreak = shooterBreak;
        this.carrierMotor = carrierMotor;
    }

    public void update() {
        if (wantedState == currentState) {
            runSubsystem();
            return;
        }

        if (wantedState == HopperState.STOPPED) {
            handleStoppedTransition();
        } else if (wantedState == HopperState.INTAKING) {
            handleIntakingTransition();
        } else if (wantedState == HopperState.SHOOTING) {
            handleShootingTransition();
        } else if (wantedState == HopperState.MOVING_BACKWARD) {
            handleMovingBackwardTransition();
        }

        currentState = wantedState;
        runSubsystem();
    }

    private void runSubsystem() {
        switch (currentState) {
            case STOPPED:
                carrierMotor.set(0.0);
                break;
            case INTAKING:
                //TODO Keep track of balls stored in system

                //If the beam is broken, set the motor to forward.
                if (intakeBreak.triggered() == BeamBreak.BeamState.BROKEN) {
                    carrierMotor.set(FORWARD_SPEED);
                    lock = true;
                }

                //Ignore first time ballBreak is broken
                if (ballBreak.triggered() == BeamBreak.BeamState.BROKEN && lock) {
                    lock = false;
                }

                //If the beam is closed, set the motor to stop.
                if (ballBreak.triggered() == BeamBreak.BeamState.CLOSED && !lock) {
                    carrierMotor.set(0.0);
                }

                break;
            case SHOOTING:
                //TODO Change ball count and stop when ball could it zero
                //TODO May need some sort of lock if the flipper changes beam state too often
                //Run forward until ball breaks shooter beam break
                //Wait until beam is restored
                //Repeat
                if (!shooterBreak.isPressed()) {
                    carrierMotor.set(FORWARD_SPEED);
                } else if (shooterBreak.isPressed()) {
                    carrierMotor.set(0.0);
                }

                break;
            case MOVING_BACKWARD:
                carrierMotor.set(REVERSE_SPEED);
                break;
        }
    }

    private void handleStoppedTransition() {

    }

    private void handleIntakingTransition() {

    }

    private void handleShootingTransition() {

    }

    private void handleMovingBackwardTransition() {

    }

    public void setWantedState(final HopperState wantedState) {
        this.wantedState = wantedState;
    }

    public HopperState getCurrentState() {
        return currentState;
    }

    public boolean isFull() {
        return mBallsStored == MAX_BALLS;
    }

    //TODO Change to use runState instead of runSubsystem
    enum HopperState {
        INTAKING,
        SHOOTING,
        MOVING_BACKWARD,
        STOPPED
    }
}
