package team1699.utils.controllers;

//This class is used to emulate a motor controller so we can test
//if the values being output from control loops are correct
public class TestSpeedController extends BetterSpeedController{

	private double speed;
	private final int port;

	public TestSpeedController(final int port){
		this.port = port;
	}

	public void set(final double percent){
		this.speed = percent;
	}

	public double get(){
		return speed;
	}
}
