package nl.axians.camel.components.fhir;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

import ca.uhn.fhir.rest.client.IGenericClient;
import nl.axians.camel.components.fhir.internal.Operation;

/**
 * FHIR Camel Producer to send messages to FHIR compatible endpoints.
 * 
 * @author Jacob Hoeflaken
 * @since 1.0
 */
public class FhirProducer extends DefaultProducer {

	private static final Logger logger = Logger.getLogger(FhirProducer.class.getName());
	
	//*************************************************************************
	// Constructors.
	//*************************************************************************
	
	/**
	 * Specialized constructor.
	 * 
	 * @param endpoint The {@link Endpoint} that created this producer.
	 */
	public FhirProducer(Endpoint endpoint) {
		super(endpoint);
	}
	
	//*************************************************************************
	// Functionality.
	//*************************************************************************	

	/**
	 * Called when a message should be sent to a FHIR compatible endpoint.
	 * 
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		String serverBase = exchange.getIn().getHeader(Exchange.HTTP_BASE_URI, String.class);
		logger.log(Level.INFO, "FHIR server base = " + serverBase);
		
		IGenericClient client = getEndpoint().getFhirContext().newRestfulGenericClient(serverBase);
		FhirConfiguration configuration = new FhirConfiguration();
		configuration.setReturnType(getEndpoint().getReturnType());
		
		Operation operation = Operation.fromValue(getEndpoint().getRemaining());
		operation.execute(configuration, client, exchange);
	}
	
	//*************************************************************************
	// Getters and Setters.
	//*************************************************************************
	
	/**
	 * Get the endpoint that created this producer as {@link FhirEndpoint} instance.
	 * 
	 * @return Instance of the {@link FhirEndpoint} that created this producer. 
	 */
	public FhirEndpoint getEndpoint() {
		return (FhirEndpoint)super.getEndpoint();
	}
}
