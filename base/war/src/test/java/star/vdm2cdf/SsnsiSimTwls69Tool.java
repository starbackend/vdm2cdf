package star.vdm2cdf;

import org.cwatch.ssnsisim.SsnsiSimulator;

public class SsnsiSimTwls69Tool {
	
	public static void main(String[] args) throws InterruptedException {
		SsnsiSimulator sim = new SsnsiSimulator("http://twls69.emsa.local:7011");
		sim.setMessagesPath("/vdm2cdf/stiresServices/stiresVdmBatchService/");
		sim.setMonitoringPath("/vdm2cdf/stiresServices/stiresMonitoringService/");
		sim.setNumberOfVessels(100);
		sim.setPositionMessagesPerSecond(200);
		sim.runFor(300000);
	}

}
