package star.vdm2cdf;

import org.cwatch.ssnsisim.SsnsiSimulator;

public class SsnsiSimStarBackLocalhostTool {
	
	public static void main(String[] args) throws InterruptedException {
		SsnsiSimulator sim = new SsnsiSimulator("http://localhost:30380");
		sim.setNumberOfVessels(10);
		sim.setPositionMessagesPerSecond(300);
		sim.runFor(3000);
	}

}
