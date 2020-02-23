package team1699.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import team1699.utils.controllers.SpeedControllerGroup;

public class DriveTrain implements Subsystem{

	private final SpeedControllerGroup portDrive, starDrive;
	private final Joystick joystick;

	public DriveTrain(final SpeedControllerGroup portDrive, final SpeedControllerGroup starDrive, final Joystick joystick){
		this.portDrive = portDrive;
		this.starDrive = starDrive;
		this.joystick = joystick;
	}

	public void update(){
		//TODO Check correct joystick axis
		runArcadeDrive(joystick.getX(), joystick.getY());
	}

	//WPILib Differential Drive
	protected void runArcadeDrive(double throttle, double rotate){
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

		portDrive.set(portOutput);
		starDrive.set(starOutput);
	}
}
