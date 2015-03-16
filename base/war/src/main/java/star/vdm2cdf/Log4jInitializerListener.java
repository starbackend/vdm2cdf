package star.vdm2cdf;

import java.io.FileNotFoundException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.util.Log4jConfigurer;
import org.springframework.web.context.support.StandardServletEnvironment;

import com.google.common.base.Throwables;

public class Log4jInitializerListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.INFO);
		StandardServletEnvironment env = new StandardServletEnvironment();
		env.initPropertySources(sce.getServletContext(), null);
		String loc = env.getProperty(Vdm2CdfProperties.LOGGING_CONFIG_LOCATION_PROPERTY,
				env.getProperty(
						Vdm2CdfProperties.CONFIG_LOCATION_PROPERTY,
						"file:/wl_domains/star/star-apps/conf/vdm2cdf.properties"
				)
		);
		
		System.out.println("VDM2CDF Logging location: " + loc);
		
		try {
			Log4jConfigurer.initLogging(loc);
		} catch (FileNotFoundException e) {
			throw Throwables.propagate(e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
