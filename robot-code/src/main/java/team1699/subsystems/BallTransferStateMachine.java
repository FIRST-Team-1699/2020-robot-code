package team1699.subsystems;

public class BallTransferStateMachine {

    enum BallTransferState{
        INTAKING, //Intaking Balls
        SHOOTING, //Shooting all balls in hopper
        EMPTYING, //Empty hopper without the shooter due to an error
        WAITING   //Waiting for next state. Store intake, keep shooter at target velocity
    }

    private BallTransferState wantedState;
    private BallTransferState currentState;

    private final Shooter shooter;
    private final Intake intake;
    private final Hopper hopper;

    //TODO Figure out if we need all of the subsystems to be passed in or if there is a better way
    public BallTransferStateMachine(final Shooter shooter, final Intake intake, final Hopper hopper){
        this.shooter = shooter;
        this.intake = intake;
        this.hopper = hopper;
    }

    public void update(){
        if(currentState == wantedState){
            return;
        }

        if(wantedState == BallTransferState.INTAKING){
            handleIntakingStateTransition();
        }
    }

    private void handleIntakingStateTransition(){
        intake.setWantedState(Intake.IntakeStates.DEPLOYED);
    }

    public BallTransferState getWantedState() {
        return wantedState;
    }

    public void setWantedState(BallTransferState wantedState) {
        this.wantedState = wantedState;
    }
}
