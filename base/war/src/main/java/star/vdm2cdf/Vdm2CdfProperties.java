package star.vdm2cdf;

import org.cwatch.env.InitialContextProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "vdm2cdf")
public class Vdm2CdfProperties {
	
	public static final String LOGGING_CONFIG_LOCATION_PROPERTY = "vdm2cdf.logging.configuration.file";
	
	public static final String CONFIG_LOCATION_PROPERTY = "vdm2cdf.configuration.file";
	
	private InitialContextProperties deadLetterInitialContext = new InitialContextProperties();
	
	private String deadLetterConnectionFactoryName = "weblogic.jms.ConnectionFactory";
	
	private String deadLetterErrorDestinationType = "queue";
	
	private String deadLetterErrorDestinationName = "star.deadLetter.error";

	private String deadLetterInvalidDestinationType = "queue";
	
	private String deadLetterInvalidDestinationName = "star.deadLetter.invalid";

	public String getDeadLetterErrorDestinationType() {
		return deadLetterErrorDestinationType;
	}

	public void setDeadLetterErrorDestinationType(
			String deadLetterErrorDestinationType) {
		this.deadLetterErrorDestinationType = deadLetterErrorDestinationType;
	}

	public String getDeadLetterErrorDestinationName() {
		return deadLetterErrorDestinationName;
	}

	public void setDeadLetterErrorDestinationName(
			String deadLetterErrorDestinationName) {
		this.deadLetterErrorDestinationName = deadLetterErrorDestinationName;
	}

	public String getDeadLetterInvalidDestinationType() {
		return deadLetterInvalidDestinationType;
	}

	public void setDeadLetterInvalidDestinationType(
			String deadLetterInvalidDestinationType) {
		this.deadLetterInvalidDestinationType = deadLetterInvalidDestinationType;
	}

	public String getDeadLetterInvalidDestinationName() {
		return deadLetterInvalidDestinationName;
	}

	public void setDeadLetterInvalidDestinationName(
			String deadLetterInvalidDestinationName) {
		this.deadLetterInvalidDestinationName = deadLetterInvalidDestinationName;
	}

	public String getDeadLetterConnectionFactoryName() {
		return deadLetterConnectionFactoryName;
	}

	public void setDeadLetterConnectionFactoryName(
			String deadLetterConnectionFactoryName) {
		this.deadLetterConnectionFactoryName = deadLetterConnectionFactoryName;
	}

	
	public InitialContextProperties getDeadLetterInitialContext() {
		return deadLetterInitialContext;
	}

	public void setDeadLetterInitialContext(
			InitialContextProperties deadLetterInitialContext) {
		this.deadLetterInitialContext = deadLetterInitialContext;
	}
}
