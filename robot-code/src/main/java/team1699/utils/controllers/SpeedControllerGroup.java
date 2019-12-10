package team1699.utils.controllers;

import java.util.List;

public class SpeedControllerGroup{

	private BetterSpeedController master;
	private List<BetterSpeedController> controllers;

	public SpeedControllerGroup(final BetterSpeedController master, BetterSpeedController ... controller){

	}

	public void set(final int percent){

	}

	public int get(){
		return 0;
	}

	//TODO Figure out how to generalize talon motion control

}
