package team1699.subsystems;

import team1699.utils.Utils;
import team1699.utils.controllers.SpeedControllerGroup;
import team1699.utils.sensors.BetterEncoder;

//TODO Fix
//public class Shooter implements Subsystem{
public class Shooter{

    enum ShooterState{
        UNINITIALIZED,
        RUNNING,
        SHOOT,
        STOPPED
    }

    private final SpeedControllerGroup controllerGroup;
    private final BetterEncoder encoder;
    private double goal = 0.0; //TODO Figure out units
    private ShooterState currentState = ShooterState.UNINITIALIZED;
    private ShooterState wantedState;
    double lastError = 0.0;
    double filteredGoal = 0.0;

    static final double kDt = 0.05;

    //Max Velocity
    static final double kMaxVelocity = 2.0;

    //Min Velocity
    static final double kMinVelocity = 0.0;

    //Max voltage to be applied
    static final double kMaxVoltage = 12.0;

    //Max voltage when zeroing
    static final double kMaxZeroingVoltage = 4.0;

    //Control loop constants
    static final double Kp = 40.0;
    static final double Kv = 0.01;

    //TODO Add a constructor so we don't have to use a group?
    public Shooter(final SpeedControllerGroup controllerGroup, final BetterEncoder encoder){
        this.controllerGroup = controllerGroup;
        this.encoder = encoder;
    }

    public void update(double encoderRate){
        switch(currentState){
            case UNINITIALIZED:
                currentState = ShooterState.RUNNING;
                filteredGoal = encoderRate;
                break;
            case RUNNING:
                filteredGoal = goal;
                break;
            case SHOOT:
                break;
            case STOPPED:
                //TODO Set motor to zero voltage output or set goal to zero velocity
                break;
            default:
                currentState = ShooterState.UNINITIALIZED;
                break;
        }

        final double error = filteredGoal - encoderRate;
        final double vel = (error - lastError) / kDt;
        lastError = error;
        final double voltage = Kp * error + Kv * vel;

        final double maxVoltage = currentState == ShooterState.RUNNING ? kMaxVoltage : kMaxZeroingVoltage;

        if(voltage >= maxVoltage){
            controllerGroup.set(Math.min(voltage, maxVoltage));
        }else {
            controllerGroup.set(Math.max(voltage, -maxVoltage));
        }
    }

    public void setGoal(final double goal){
        this.goal = goal;
    }

    public double getGoal(){
        return goal;
    }

    public void setWantedState(final ShooterState wantedState){
        this.wantedState = wantedState;
    }

    public ShooterState getCurrentState() {
        return currentState;
    }

    /**
     * Checks if the shooter had reached target velocity
     * @return True if we have reached the target velocity, false otherwise
     */
    public boolean atGoal() {
        //TODO Check tolerance
        return Utils.epsilonEquals(lastError, 0, 0.05);
    }
}
