package team1699.subsystems;

public class BallTransferStateMachine {

    private final Shooter shooter;
    private final Intake intake;
    private final Hopper hopper;
    private BallTransferState wantedState;
    private BallTransferState currentState;

    public BallTransferStateMachine(final Shooter shooter, final Intake intake, final Hopper hopper) {
        this.shooter = shooter;
        this.intake = intake;
        this.hopper = hopper;
    }

    public void update() {
        if (currentState == wantedState) {
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
    }

    private void handleIntakingStateTransition() {
        intake.setWantedState(Intake.IntakeStates.DEPLOYED);
        hopper.setWantedState(Hopper.HopperState.INTAKING);
        shooter.setWantedState(Shooter.ShooterState.RUNNING);
    }

    private void handleShootingStateTransition() {
        intake.setWantedState(Intake.IntakeStates.STORED);
        hopper.setWantedState(Hopper.HopperState.SHOOTING);
        shooter.setWantedState(Shooter.ShooterState.SHOOT);
    }

    private void handleEmptyingStateTransition() {
        intake.setWantedState(Intake.IntakeStates.STORED);
        hopper.setWantedState(Hopper.HopperState.MOVING_BACKWARD);
        shooter.setWantedState(Shooter.ShooterState.RUNNING);
    }

    private void handleWaitingStateTransition() {
        intake.setWantedState(Intake.IntakeStates.STORED);
        hopper.setWantedState(Hopper.HopperState.STOPPED);
        shooter.setWantedState(Shooter.ShooterState.RUNNING);
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
        WAITING,   //Waiting for next state. Store intake, keep shooter at target velocity
        OFF //Turn off all subsystems
    }
}
