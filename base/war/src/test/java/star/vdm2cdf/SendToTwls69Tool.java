package star.vdm2cdf;

import java.io.IOException;

import org.cwatch.vdm.VdmHttpSender;
import org.springframework.util.StopWatch;

import com.google.common.io.ByteStreams;

public class SendToTwls69Tool {
	
	public static void main(String[] args) throws InterruptedException, IOException {
		VdmHttpSender sim = new VdmHttpSender();
		sim.setTargetBaseUrl("http://twls69.emsa.local:7011");
		sim.setMessagesPath("/vdm2cdf/stiresServices/stiresVdmBatchService/");
		sim.setMonitoringPath("/vdm2cdf/stiresServices/stiresMonitoringService/");
		StopWatch sw = new org.springframework.util.StopWatch();
		sw.start();
		sim.sendMessages("TEST", ByteStreams.toByteArray(SendToTwls69Tool.class.getResourceAsStream("/vdmHttp-valid.json")));
		sim.sendMessages("TEST", ByteStreams.toByteArray(SendToTwls69Tool.class.getResourceAsStream("/vdmHttp-valid.json")));
		sim.sendMessages("TEST", ByteStreams.toByteArray(SendToTwls69Tool.class.getResourceAsStream("/vdmHttp-valid.json")));
		sim.sendMessages("TEST", ByteStreams.toByteArray(SendToTwls69Tool.class.getResourceAsStream("/vdmHttp-valid.json")));
		sw.stop();
		System.out.println(sw.shortSummary());
	}

}
