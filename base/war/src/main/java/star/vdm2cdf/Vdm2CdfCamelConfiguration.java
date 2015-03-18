package star.vdm2cdf;

import org.apache.camel.model.ContextScanDefinition;
import org.apache.camel.spring.CamelContextFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Vdm2CdfCamelConfiguration {

	@Bean
	CamelContextFactoryBean cwatchCamelContext() {
		CamelContextFactoryBean factory = new CamelContextFactoryBean();
		factory.setId("vdm2cdf-camelContext");
		factory.setContextScan(new ContextScanDefinition());
		return factory;
	}
	
}
