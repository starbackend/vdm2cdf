package star.vdm2cdf;

import org.cwatch.ssnsisim.SsnsiSimulator;

public class SsnsiSimTwls69Tool {
	
	public static void main(String[] args) throws InterruptedException {
		SsnsiSimulator sim = new SsnsiSimulator("http://twls69.emsa.local:7011/");
		sim.start();
		Thread.sleep(1000);
		sim.stop();
	}

}
