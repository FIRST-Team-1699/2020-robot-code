package team1699.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import team1699.utils.controllers.SpeedControllerGroup;
import team1699.utils.sensors.LimeLight;

public class DriveTrain implements Subsystem{

	public enum DriveState{
		MANUAL,
		GOAL_TRACKING
	}

	public static final double kP = 0.0, kD = 0.0, kMinCommand = 0.0; //TODO Populate

	private DriveState systemState, wantedState;
	private final SpeedControllerGroup portDrive, starDrive;
	private final Joystick joystick;

	public DriveTrain(final SpeedControllerGroup portDrive, final SpeedControllerGroup starDrive, final Joystick joystick){
		this.portDrive = portDrive;
		this.starDrive = starDrive;
		this.joystick = joystick;
		wantedState = DriveState.MANUAL;
	}

	public void update(){
		if(systemState == wantedState){
			runSubsystem();
			return;
		}

		if(wantedState == DriveState.MANUAL){
			handleManualTransition();
		}else if(wantedState == DriveState.GOAL_TRACKING){
			handleGoalTrackingTransition();
		}

		systemState = wantedState;
		runSubsystem();
	}

	private void handleManualTransition(){
		LimeLight.getInstance().turnOff();
	}

	private void handleGoalTrackingTransition(){
		LimeLight.getInstance().turnOn();
	}

	private double portCommand, starCommand;

	private void runSubsystem(){
		switch (systemState){
			case MANUAL:
				runArcadeDrive(joystick.getX(), -joystick.getY());
				break;
			case GOAL_TRACKING:
				//TODO Add derivative
				//TODO LimeLight

				double headingError = -LimeLight.getInstance().getTX();
				double steeringAdjust = 0.0;

				if (LimeLight.getInstance().getTX() > 1.0)
				{
					steeringAdjust = kP * headingError - kMinCommand;
				}
				else if (LimeLight.getInstance().getTX() < 1.0)
				{
					steeringAdjust = kP * headingError + kMinCommand;
				}
				portCommand += steeringAdjust;
				starCommand -= steeringAdjust;

				break;
			default:
				break;
		}
	}

	//WPILib Differential Drive
	protected void runArcadeDrive(double throttle, double rotate){
		double portOutput = 0.0;
		double starOutput = 0.0;

		//TODO add deadband
		throttle = Math.copySign(throttle * throttle, throttle);
		rotate = Math.copySign(rotate * rotate, rotate);
		
		double maxInput = Math.copySign(Math.max(Math.abs(throttle), Math.abs(rotate)), throttle);

		if (throttle >= 0.0) {
			// First quadrant, else second quadrant
			if (rotate >= 0.0) {
				portOutput = maxInput;
				starOutput = throttle - rotate;
			} else {
				portOutput = throttle + rotate;
				starOutput = maxInput;
			}
		} else {
			// Third quadrant, else fourth quadrant
			if (rotate >= 0.0) {
				portOutput = throttle + rotate;
				starOutput = maxInput;
			} else {
				portOutput = maxInput;
				starOutput = throttle - rotate;
			}
		}

		portDrive.set(portOutput);
		starDrive.set(starOutput);
	}
}
