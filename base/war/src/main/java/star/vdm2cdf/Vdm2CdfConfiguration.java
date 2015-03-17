package star.vdm2cdf;

import org.apache.camel.model.ContextScanDefinition;
import org.apache.camel.spring.CamelContextFactoryBean;
import org.cwatch.service.cdf.CdfForwardRouteBuilder;
import org.cwatch.service.routes.AisToCdfErrorRouteBuilder;
import org.cwatch.service.routes.AisToCdfRouteBuilder;
import org.cwatch.split.routes.CwatchSplitInputRouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({ 
	CwatchSplitInputRouteBuilder.class, 
	Vdm2CdfWarRouteBuilder.class,
	AisToCdfRouteBuilder.class, 
	AisToCdfErrorRouteBuilder.class,
	CdfForwardRouteBuilder.class,
})
@PropertySource("classpath:vdm2cdf.properties")
public class Vdm2CdfConfiguration {

	@Bean
	CamelContextFactoryBean cwatchCamelContext() {
		CamelContextFactoryBean factory = new CamelContextFactoryBean();
		factory.setId("vdm2cdf-camelContext");
		factory.setContextScan(new ContextScanDefinition());
		return factory;
	}
	
}
