package star.vdm2cdf;

import java.util.EventObject;

import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelBeanPostProcessor;
import org.apache.camel.management.event.ExchangeCompletedEvent;
import org.apache.camel.support.EventNotifierSupport;
import org.apache.camel.util.ServiceHelper;
import org.cwatch.vdm.AisGsonProxy;
import org.cwatch.vdm.test.AisGsonProxyAdapter;
import org.cwatch.vdm.test.ProxySsnVdmCallback;
import org.cwatch.vdm.test.SsnVdmSimulator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;

import ssn.spm.domain.ais.AisMessageContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class Vdm2CdfWarRouteTestTool {

	@Autowired
	CamelContext camelContext;
	
	@Before
	public void setup() throws Exception {
		new DefaultCamelBeanPostProcessor(camelContext).postProcessBeforeInitialization(this, null);
	}
	
	@Produce(uri="direct:vdmHttpBatchInput", context="vdm2cdf-camelContext")
    protected ProducerTemplate template;	
	
	@Test
	public void test() {
		final AisGsonProxyAdapter adapter = new AisGsonProxyAdapter();
		adapter.setStiresSender(new AisGsonProxy() {
			@Override
			public void sendMonitoring(String sourceProxyName, byte[] zippedStream) {
			}
			
			@Override
			public void sendMessages(String sourceProxyName, byte[] zippedStream) {
				template.sendBodyAndHeader(template.getDefaultEndpoint(), ExchangePattern.InOnly, zippedStream, "SSNProxy", sourceProxyName);
			}
		});
		
		SsnVdmSimulator sim = new SsnVdmSimulator();
		sim.setNumberOfVessels(100);
		sim.setPositionMessagesPerSecond(100);
		sim.setCallback(new ProxySsnVdmCallback() {
			@Override
			public void onProxyMessage(AisMessageContainer messageContainer) {
				adapter.send(messageContainer);
			}
		});
		sim.runFor(10000);
	}
	
	int count2 = 0;
	
	@Test
	public void test2() throws Exception {
		EventNotifierSupport eventNotifier = new EventNotifierSupport() {
			
			@Override
			public void notify(EventObject event) throws Exception {
				if (event instanceof ExchangeCompletedEvent) {
					if (((ExchangeCompletedEvent) event).getExchange().getProperty("CamelSplitComplete")!=null) {
						count2++;
					};
				}
			}
			
			@Override
			public boolean isEnabled(EventObject event) {
				return true;
			}
		};
		ServiceHelper.startService(eventNotifier);
		camelContext.getManagementStrategy().addEventNotifier(eventNotifier);
		
		StopWatch sw = new org.springframework.util.StopWatch();
		sw.start();
		template.sendBodyAndHeader(template.getDefaultEndpoint(), ExchangePattern.InOnly, getClass().getResource("/vdmHttp-valid.json"), "SSNProxy", "BOO");
		template.sendBodyAndHeader(template.getDefaultEndpoint(), ExchangePattern.InOnly, getClass().getResource("/vdmHttp-valid.json"), "SSNProxy", "BOO");
		template.sendBodyAndHeader(template.getDefaultEndpoint(), ExchangePattern.InOnly, getClass().getResource("/vdmHttp-valid.json"), "SSNProxy", "BOO");
		template.sendBodyAndHeader(template.getDefaultEndpoint(), ExchangePattern.InOnly, getClass().getResource("/vdmHttp-valid.json"), "SSNProxy", "BOO");
		sw.stop();
		System.out.println(sw.shortSummary() + " msgs = " + count2 + " mgs/sec = " + (count2/sw.getTotalTimeSeconds()));
	}
	
	@Configuration
	@PropertySource("classpath:application-test.properties")
	@Import(Vdm2CdfConfiguration.class)
	public static class Context {}
	

}
