package star.vdm2cdf;

import org.cwatch.service.cdf.CdfForwardRouteBuilder;
import org.cwatch.service.routes.AisToCdfErrorRouteBuilder;
import org.cwatch.service.routes.AisToCdfRouteBuilder;
import org.cwatch.split.routes.CwatchSplitInputRouteBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({ 
	Vdm2CdfCamelConfiguration.class,
	CwatchSplitInputRouteBuilder.class, 
	Vdm2CdfWarRouteBuilder.class,
	AisToCdfRouteBuilder.class, 
	AisToCdfErrorRouteBuilder.class,
	CdfForwardRouteBuilder.class,
})
@PropertySource("classpath:vdm2cdf.properties")
public class Vdm2CdfConfiguration {
	
}
