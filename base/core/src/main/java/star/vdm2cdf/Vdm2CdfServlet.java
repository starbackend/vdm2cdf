package star.vdm2cdf;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.web.servlet.DispatcherServlet;

import com.google.common.base.Throwables;

@SuppressWarnings("serial")
public class Vdm2CdfServlet extends DispatcherServlet {
	
	Logger LOG = LoggerFactory.getLogger(Vdm2CdfServlet.class);
	
	@Override
	protected ConfigurableEnvironment createEnvironment() {
		ConfigurableEnvironment env = super.createEnvironment();
		String config = env.getProperty(
				Vdm2CdfProperties.CONFIG_LOCATION_PROPERTY,
				"file:/wl_domains/star/star-apps/conf/vdm2cdf.properties"
		);	
		try {
			env.getPropertySources().addFirst(new ResourcePropertySource(config));
		} catch (FileNotFoundException e) {
			LOG.info("Configuration file not found, using defaults. " + e.getMessage());
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
		return env;
	}

}
