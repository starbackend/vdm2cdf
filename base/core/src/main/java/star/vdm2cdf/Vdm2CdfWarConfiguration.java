package star.vdm2cdf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import({ 
	Vdm2CdfConfiguration.class 
})
public class Vdm2CdfWarConfiguration {

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
