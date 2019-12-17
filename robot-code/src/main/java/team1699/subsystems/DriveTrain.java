package team1699.subsystems;

import team1699.utils.controllers.SpeedControllerGroup;

public class DriveTrain{

	private SpeedControllerGroup portDrive, starDrive;

	//TODO Figure out best way to pass in controllers so that it is flexable
	public DriveTrain(final SpeedControllerGroup portDrive, final SpeedControllerGroup starDrive){
		this.portDrive = portDrive;
		this.starDrive = starDrive;
	}

	//TODO Change to use DriveSignal
	//WPILib Differential Drive
	public void update(double throttle, double rotate){
		double portOutput = 0.0;
		double starOutput = 0.0;

		//TODO add deadband
		throttle = Math.copySign(throttle * throttle, throttle);
		rotate = Math.copySign(rotate * rotate, rotate);
		
		double maxInput = Math.copySign(Math.max(Math.abs(throttle), Math.abs(rotate)), throttle);

		if(throttle >= 0.0){
			//First quadrant, else second quadrant
			//
			//double maxInput = Math.copySign(Math.max(Math.abs(throttle), Math.abs(rotate)), throttle);
			
			//if(throttle >=){
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
