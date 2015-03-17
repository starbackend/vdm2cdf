package star.vdm2cdf;

import org.cwatch.ssnsisim.SsnsiSimulator;

public class SsnsiSimTwls69StarBackTool {
	
	public static void main(String[] args) throws InterruptedException {
		SsnsiSimulator sim = new SsnsiSimulator("http://twls69.emsa.local:30380");
		sim.setNumberOfVessels(10);
		sim.setPositionMessagesPerSecond(30);
		sim.runFor(10000);
	}

}
