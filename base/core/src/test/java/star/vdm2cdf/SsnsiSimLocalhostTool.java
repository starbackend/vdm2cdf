package star.vdm2cdf;

import org.cwatch.ssnsisim.SsnsiSimulator;

public class SsnsiSimLocalhostTool {
	
	public static void main(String[] args) throws InterruptedException {
		SsnsiSimulator sim = new SsnsiSimulator("http://localhost:8080");
		sim.setNumberOfVessels(10);
		sim.setPositionMessagesPerSecond(100);
		sim.runFor(1000000);
	}

}
