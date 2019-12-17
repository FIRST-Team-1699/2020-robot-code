package team1699.subsystems;

import org.junit.Test;
import team1699.utils.controllers.BetterSpeedController;
import team1699.utils.controllers.SpeedControllerGroup;
import team1699.utils.controllers.TestSpeedController;
import team1699.subsystems.DriveTrain;

import static org.junit.Assert.assertEquals;

public class DriveTrainTest{

	@Test
	public void testFullForward(){
		BetterSpeedController portTest = new TestSpeedController(0);
		BetterSpeedController starTest = new TestSpeedController(1);
		SpeedControllerGroup portGroup = new SpeedControllerGroup(portTest);
		SpeedControllerGroup starGroup = new SpeedControllerGroup(starTest);
		DriveTrain driveTrain = new DriveTrain(portGroup, starGroup);
		driveTrain.update(1.0, 0);
		assertEquals(1.0, portTest.get(), 0.1);
		assertEquals(1.0, starTest.get(), 0.1);
	}

}
