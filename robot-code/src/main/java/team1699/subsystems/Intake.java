package team1699.subsystems;

public class Intake {

    enum IntakeStates{
        DEPLOYED,
        STORED
    }

    //Start the system in an uninitialized state and set a wanted state
    private IntakeStates currentState = null;
    private IntakeStates wantedState;

    //TODO Figure out how the intake is being moved and how wheels are being spun
    public Intake(){
        wantedState = IntakeStates.STORED;
    }

    public void update(){
        //We do not have to change states
        if(currentState == wantedState){
            return;
        }

        if(wantedState == IntakeStates.STORED){
            //Store intake and turn off intake wheels

            //TODO Check that action was completed
            currentState = wantedState;
        }else if(wantedState == IntakeStates.DEPLOYED){
            //Deploy intake and turn on intake wheels

            //TODO Check that action was completed
            currentState = wantedState;
        }
    }

    public void setWantedState(final IntakeStates wantedState){
        this.wantedState = wantedState;
    }

    public IntakeStates getWantedState() {
        return wantedState;
    }
}
