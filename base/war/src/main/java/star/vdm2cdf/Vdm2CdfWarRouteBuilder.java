package star.vdm2cdf;

import org.apache.camel.spring.SpringRouteBuilder;
import org.cwatch.service.routes.AisToCdfRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(Vdm2CdfProperties.class)
public class Vdm2CdfWarRouteBuilder extends SpringRouteBuilder {

	@Autowired
	Vdm2CdfProperties configuration;
	
	@Override
	public void configure() throws Exception {
		errorHandler(
				defaultErrorHandler()
				.logExhaustedMessageHistory(false)
		);
		
		from("direct:vdmHttpInput")
		.id("cdfBatchReceiver")
		.to("direct:ais2cdf");
		
		from("direct:vdmHttpMonitoringInput")
		.stop();
		
		from("direct:ais2cdfPositionOut")
		.id("cdfPositionOut")
		.to("direct:cdfPositionForward");
		
		from("direct:ais2cdfVoyageOut")
		.id("cdfVoyageOut")
		.to("direct:cdfVoyageForward");
		
		from("direct:ais2cdfInvalidLetter")
		.to("activemq:topic:"+configuration.getCdfInvalidTopicName() + "?jmsMessageType=Text");
		
		from("direct:ais2cdfErrorLetter")
		.to("activemq:topic:"+configuration.getCdfErrorTopicName() + "?jmsMessageType=Text");
		
		
	}

}
