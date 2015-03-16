package star.vdm2cdf;

import org.apache.camel.model.ContextScanDefinition;
import org.apache.camel.spring.CamelContextFactoryBean;
import org.cwatch.boot.camel.CamelBootProperties;
import org.cwatch.service.cdf.CdfForwardRouteBuilder;
import org.cwatch.service.routes.AisToCdfErrorRouteBuilder;
import org.cwatch.service.routes.AisToCdfRouteBuilder;
import org.cwatch.split.routes.CwatchSplitInputRouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import({ 
	CwatchSplitInputRouteBuilder.class, 
	Vdm2CdfWarRouteBuilder.class,
	AisToCdfRouteBuilder.class, 
	AisToCdfErrorRouteBuilder.class,
	CdfForwardRouteBuilder.class,
})
@PropertySource("classpath:vdm2cdf.properties")
public class Vdm2CdfWarConfiguration {

	@Bean
	CamelContextFactoryBean cwatchCamelContext() {
		CamelContextFactoryBean factory = new CamelContextFactoryBean();
		factory.setId("vdm2cdf-camelContext");
		factory.setContextScan(new ContextScanDefinition());
		return factory;
	}

	@Configuration
	@EnableWebMvc
	public static class WebConfig extends WebMvcConfigurerAdapter {
		
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/**")
					.addResourceLocations(new String[] {"classpath:/static/"});
		}		
		
	}
	
}
