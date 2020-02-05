package team1699.subsystems;

import team1699.utils.Utils;
import team1699.utils.controllers.SpeedControllerGroup;

public class Shooter {

    enum ShooterState{
        UNINITIALIZED,
        RUNNING,
        ESTOPPED
    }

    private final SpeedControllerGroup controllerGroup;
    private double goal = 0.0; //TODO Figure out units
    private ShooterState state = ShooterState.UNINITIALIZED;
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
    static final double Kp = 60.0;
    static final double Kv = 100.0;

    //TODO Add a constructor so we don't have to use a group?
    public Shooter(SpeedControllerGroup controllerGroup){
        this.controllerGroup = controllerGroup;
    }

    public void update(double encoderRate, final boolean enabled){
        switch(state){
            case UNINITIALIZED:
                if(enabled){
                    state = ShooterState.RUNNING;
                    filteredGoal = encoderRate;
                }
                break;
            case RUNNING:
                filteredGoal = goal;
                break;
            case ESTOPPED:
                //TODO Figure out what to do here
                break;
            default:
                state = ShooterState.UNINITIALIZED;
                break;
        }

        final double error = filteredGoal - encoderRate;
        final double vel = (error - lastError) / kDt;
        lastError = error;
        double voltage = Kp * error + Kv * vel;

        final double maxVoltage = state == ShooterState.RUNNING ? kMaxVoltage : kMaxZeroingVoltage;

        if(voltage >= maxVoltage){
            controllerGroup.set(maxVoltage);
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

    /**
     * Checks if the shooter had reached target velocity
     * @return True if we have reached the target velocity, false otherwise
     */
    public boolean atGoal() {
        //TODO Check tolerance
        return Utils.epsilonEquals(lastError, 0, 0.05);
    }
}
