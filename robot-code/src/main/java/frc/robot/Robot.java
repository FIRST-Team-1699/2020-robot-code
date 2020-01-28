package frc.robot;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

    private Joystick mDriveJoystick;

    private TalonFX mPortMaster, mPortSlave1, mPortSlave2, mStarMaster, mStarSlave1, mStarSlave2;

    @Override
    public void robotInit() {
        //Setup port drive motors
        mPortMaster = new TalonFX(10);
        mPortSlave1 = new TalonFX(11);
        mPortSlave1.follow(mPortMaster);
        mPortSlave2 = new TalonFX(12);
        mPortSlave2.follow(mPortMaster);

        //Setup starboard drive motors
        mStarMaster = new TalonFX(13);
        mStarMaster.setInverted(true);
        mStarSlave1 = new TalonFX(14);
        mStarSlave1.follow(mStarMaster);
        mStarSlave1.setInverted(true);
        mStarSlave2 = new TalonFX(15);
        mStarSlave2.follow(mStarMaster);
        mStarSlave2.setInverted(true);

        //Setup joystick
        mDriveJoystick = new Joystick(0);
    }

    @Override
    public void robotPeriodic() {
        update(mDriveJoystick.getX(), mDriveJoystick.getY());
    }

    //WPILib Differential Drive
    private void update(double throttle, double rotate){
        double portOutput = 0.0;
        double starOutput = 0.0;

        //TODO add deadband
        throttle = Math.copySign(throttle * throttle, throttle);
        rotate = Math.copySign(rotate * rotate, rotate);

        double maxInput = Math.copySign(Math.max(Math.abs(throttle), Math.abs(rotate)), throttle);

        if(throttle >= 0.0){
            ////First quadrant, else second quadrant
            if(rotate >= 0.0){
                portOutput = maxInput;
                starOutput = throttle - rotate;
            }else{
                portOutput = throttle + rotate;
                starOutput = maxInput;
            }
        }else{
            if(rotate >= 0.0){
                portOutput = maxInput;
                starOutput = throttle - rotate;
            }else{
                portOutput = throttle + rotate;
                starOutput = maxInput;
            }
        }

        mPortMaster.set(TalonFXControlMode.PercentOutput, portOutput);
        mStarMaster.set(TalonFXControlMode.PercentOutput, starOutput);
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testPeriodic() {
    }
}
