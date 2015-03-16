package star.vdm2cdf;

import java.io.IOException;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.google.common.base.Throwables;

public class Vdm2CdfApplicationContext extends AnnotationConfigWebApplicationContext {
	
	@Override
	protected ConfigurableEnvironment createEnvironment() {
		ConfigurableEnvironment env = super.createEnvironment();
		String config = env.getProperty(
				Vdm2CdfProperties.CONFIG_LOCATION_PROPERTY,
				"file:/wl_domains/star/star-apps/conf/vdm2cdf.properties"
		);	
		try {
			env.getPropertySources().addLast(new ResourcePropertySource(config));
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
		return env;
	}

}
