package team1699.subsystems;

public class BallTransferStateMachine {

    private final Shooter shooter;
    private final Intake intake;
    private final Hopper hopper;
    private BallTransferState wantedState;
    private BallTransferState currentState;
    //TODO Figure out if we need all of the subsystems to be passed in or if there is a better way
    public BallTransferStateMachine(final Shooter shooter, final Intake intake, final Hopper hopper) {
        this.shooter = shooter;
        this.intake = intake;
        this.hopper = hopper;
    }

    public void update() {
        if (currentState == wantedState) {
            runSubsystem();
            return;
        }

        if (wantedState == BallTransferState.INTAKING) {
            handleIntakingStateTransition();
        } else if (wantedState == BallTransferState.SHOOTING) {
            handleShootingStateTransition();
        } else if (wantedState == BallTransferState.EMPTYING) {
            handleEmptyingStateTransition();
        } else if (wantedState == BallTransferState.WAITING) {
            handleWaitingStateTransition();
        }

        currentState = wantedState;
        runSubsystem();
    }

    private void runSubsystem() {
        switch (currentState) {
            case INTAKING:
                break;
            case SHOOTING:
                break;
            case EMPTYING:
                break;
            case WAITING:
                break;
        }
    }

    private void handleIntakingStateTransition() {
        intake.setWantedState(Intake.IntakeStates.DEPLOYED);
        hopper.setWantedState(Hopper.HopperState.INTAKING);
    }

    private void handleShootingStateTransition() {

    }

    private void handleEmptyingStateTransition() {

    }

    private void handleWaitingStateTransition() {

    }

    public BallTransferState getWantedState() {
        return wantedState;
    }

    public void setWantedState(BallTransferState wantedState) {
        this.wantedState = wantedState;
    }

    enum BallTransferState {
        INTAKING, //Intaking Balls
        SHOOTING, //Shooting all balls in hopper
        EMPTYING, //Empty hopper without the shooter due to an error
        WAITING   //Waiting for next state. Store intake, keep shooter at target velocity
    }
}
