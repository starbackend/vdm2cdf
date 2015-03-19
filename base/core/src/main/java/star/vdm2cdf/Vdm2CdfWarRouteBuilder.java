package star.vdm2cdf;

import javax.jms.ConnectionFactory;
import javax.naming.NamingException;

import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.JndiDestinationResolver;
import org.springframework.jndi.JndiTemplate;
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
		
		from("direct:vdmHttpBatchInput")
		.id("cdfBatchReceiver")
		.threads()
		.filter(simple("${body} != null"))
		.to("direct:ais2cdf");
		
		from("direct:vdmHttpMonitoringInput")
		.stop();
		
		from("direct:ais2cdfPositionOut")
		.id("cdfPositionOut")
		.to("direct:cdfPositionForward");
		
		from("direct:ais2cdfVoyageOut")
		.id("cdfVoyageOut")
		.to("direct:cdfVoyageForward");
		
		from("direct:ais2cdfInvalidDeadLetter")
		.to("deadLetter:"+configuration.getDeadLetterInvalidDestinationType()+":"+configuration.getDeadLetterInvalidDestinationName() + "?jmsMessageType=Text");
		
		from("direct:ais2cdfErrorDeadLetter")
		.to("deadLetter:"+configuration.getDeadLetterErrorDestinationType()+":"+configuration.getDeadLetterErrorDestinationName() + "?jmsMessageType=Text");
		
		
	}

	@Bean
	JndiTemplate vdm2cdfDeadLetterJndiTemplate() {
		return new JndiTemplate(configuration.getDeadLetterInitialContext().createProperties());
	}
	
	@Bean
	ConnectionFactory vdm2cdfDeadLetterConnectionFactory(@Qualifier("vdm2cdfDeadLetterJndiTemplate") JndiTemplate vdm2cdfDeadLetterJndiTemplate) throws NamingException {
		return configuration.getDeadLetterConnectionFactory().lookup(vdm2cdfDeadLetterJndiTemplate);
	}

	@Bean
	JndiDestinationResolver vdm2cdfDeadLetterDestinationResolver(@Qualifier("vdm2cdfDeadLetterJndiTemplate") JndiTemplate vdm2cdfDeadLetterJndiTemplate) {
		JndiDestinationResolver resolver = new JndiDestinationResolver();
		resolver.setJndiTemplate(vdm2cdfDeadLetterJndiTemplate);
		return resolver;
	}
	
	@Bean
	JmsComponent deadLetter(@Qualifier("vdm2cdfDeadLetterConnectionFactory") ConnectionFactory vdm2cdfDeadLetterConnectionFactory, @Qualifier("vdm2cdfDeadLetterDestinationResolver") DestinationResolver vdm2cdfDeadLetterDestinationResolver) {
		JmsComponent jms = new JmsComponent();
		jms.setConnectionFactory(vdm2cdfDeadLetterConnectionFactory);
		jms.setDestinationResolver(vdm2cdfDeadLetterDestinationResolver);
		return jms;
	}
	
	
}
