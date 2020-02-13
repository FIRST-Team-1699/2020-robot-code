package team1699.subsystems;

public class BallTransferStateMachine {

    enum BallTransferStates{
        INTAKING, //Intaking Balls
        SHOOTING, //Shooting all balls in hopper
        EMPTYING, //Empty hopper without the shooter due to an error
        WAITING   //Waiting for next state. Store intake, keep shooter at target velocity
    }
}
