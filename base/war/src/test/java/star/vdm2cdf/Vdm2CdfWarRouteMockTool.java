package star.vdm2cdf;

import java.util.EventObject;

import org.apache.camel.CamelContext;
import org.apache.camel.Consume;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelBeanPostProcessor;
import org.apache.camel.management.event.ExchangeCompletedEvent;
import org.apache.camel.support.EventNotifierSupport;
import org.apache.camel.util.ServiceHelper;
import org.cwatch.boot.camel.CwatchCamelTools;
import org.cwatch.service.routes.AisToCdfErrorRouteBuilder;
import org.cwatch.service.routes.AisToCdfRouteBuilder;
import org.cwatch.split.routes.CwatchSplitInputRouteBuilder;
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
public class Vdm2CdfWarRouteMockTool {

	@Autowired
	CamelContext camelContext;
	
	@Before
	public void setup() throws Exception {
		new DefaultCamelBeanPostProcessor(camelContext).postProcessBeforeInitialization(this, null);
	}
	
	@Produce(uri="direct:ais2cdf", context="vdm2cdf-camelContext")
    protected ProducerTemplate template;
	
//	@EndpointInject(uri = "direct:ais2cdfPositionOut", context = "vdm2cdf-camelContext")
//    protected MockEndpoint pos;	
//	
//	@EndpointInject(uri = "direct:ais2cdfVoyageOut", context = "vdm2cdf-camelContext")
//	protected MockEndpoint voyage;	
//	
//	@EndpointInject(uri = "direct:ais2cdfInvalidDeadLetter", context = "vdm2cdf-camelContext")
//	protected MockEndpoint invalid;	
//	
//	@EndpointInject(uri = "direct:ais2cdfErrorDeadLetter", context = "vdm2cdf-camelContext")
//	protected MockEndpoint error;	
	
	@Consume(uri = "direct:ais2cdfPositionOut", context = "vdm2cdf-camelContext")
	public void pos(Exchange exchange) throws Exception {
		reporter.process(exchange);
	}
	
	@Consume(uri = "direct:ais2cdfVoyageOut", context = "vdm2cdf-camelContext")
	public void voyage(Exchange exchange) throws Exception {
		reporter.process(exchange);
	}
	
	@Consume(uri = "direct:ais2cdfInvalidDeadLetter", context = "vdm2cdf-camelContext")
	public void invalid(Exchange exchange) throws Exception {
		reporter.process(exchange);
	}
	
	@Consume(uri = "direct:ais2cdfErrorDeadLetter", context = "vdm2cdf-camelContext")
	public void error(Exchange exchange) throws Exception {
		reporter.process(exchange);
	}
	
	int count = 0;
	int count2 = 0;
	
	Processor reporter = new Processor() {
		@Override
		public void process(Exchange exchange) throws Exception {
			count++;
			//System.out.println("boo " + (count++));
		}
	};
	
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
	
	@Test
	public void test2() throws Exception {
//		pos.setReporter(reporter);
//		voyage.setReporter(reporter);
//		invalid.setReporter(reporter);
//		error.setReporter(reporter);
		
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
		sw.stop();
		System.out.println(sw.shortSummary() + " msgs = " + count + " mgs/sec = " + (count/sw.getTotalTimeSeconds()));
		System.out.println(count2);
	}
	
	@Configuration
	@PropertySource("classpath:application-test.properties")
	@Import({ 
		CwatchSplitInputRouteBuilder.class, 
		AisToCdfRouteBuilder.class, 
		AisToCdfErrorRouteBuilder.class,
		Vdm2CdfCamelConfiguration.class,
	})
	public static class Context {}
	

}
