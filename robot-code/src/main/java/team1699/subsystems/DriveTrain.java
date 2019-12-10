package team1699.subsystems;

import team1699.utils.controllers.SpeedControllerGroup;

public class DriveTrain{

	private SpeedControllerGroup portDrive, starDrive;

	//TODO Figure out best way to pass in controllers so that it is flexable
	public DriveTrain(final SpeedControllerGroup portDrive, final SpeedControllerGroup starDrive){
		this.portDrive = portDrive;
		this.starDrive = starDrive;
	}

	public void update(final double joystickX, final double joystickY){

	}
}
