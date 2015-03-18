package star.vdm2cdf;

import java.io.IOException;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.web.servlet.DispatcherServlet;

import com.google.common.base.Throwables;

@SuppressWarnings("serial")
public class Vdm2CdfServlet extends DispatcherServlet {
	
	@Override
	protected ConfigurableEnvironment createEnvironment() {
		ConfigurableEnvironment env = super.createEnvironment();
		String config = env.getProperty(
				Vdm2CdfProperties.CONFIG_LOCATION_PROPERTY,
				"/wl_domains/star/star-apps/conf/vdm2cdf.properties"
		);	
		try {
			env.getPropertySources().addFirst(new ResourcePropertySource("file:"+config));
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
		return env;
	}

}
