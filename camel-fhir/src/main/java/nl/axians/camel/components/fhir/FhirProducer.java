package nl.axians.camel.components.fhir;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.api.PreferReturnEnum;
import ca.uhn.fhir.rest.client.IGenericClient;

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
		IBaseResource resource;
		MethodOutcome response;
		
		switch(getEndpoint().getRemaining()) {
		case "search":
			break;
		case "create":
			resource = exchange.getIn().getBody(IBaseResource.class);
			logger.log(Level.INFO, "Body = " + resource);
			response = client.create()
					.resource(resource)
					.prettyPrint()
					.encodedJson()
					.prefer(getEndpoint().getReturnType())
					.execute();
			logger.log(Level.INFO, "Body = " + response);
			logger.log(Level.INFO, "Id = " + response.getId());
			logger.log(Level.INFO, "Resource = " + response.getResource());
			if (PreferReturnEnum.REPRESENTATION == getEndpoint().getReturnType())
				exchange.getIn().setBody(response.getResource());
			else
				exchange.getIn().setBody(response.getId());
			break;
		case "delete":
			client.delete().resourceConditionalByUrl("");
			break;
		case "history":
			break;
		case "read":
			client.read().resource("");
			break;
		case "update":
			resource = exchange.getIn().getBody(IBaseResource.class);
			client.update().resource(resource).execute();
			break;
		case "patch":
			client.patch().withBody("");
			break;
		case "fetchConformance":
			client.fetchConformance();
			break;
		case "forceConformanceCheck":
			client.forceConformanceCheck();
			break;
		case "loadPage":
			client.loadPage().byUrl("");
			break;
		case "meta":
			break;
		case "operation":
			break;
		case "transaction":
			@SuppressWarnings("unchecked") 
			List<IBaseResource> resources = exchange.getIn().getBody(List.class);
			client.transaction().withResources(resources);
			break;
		case "vread":
			client.vread(null, "", "");
			break;
		case "validate":
			resource = exchange.getIn().getBody(IBaseResource.class);
			client.validate().resource(resource).execute();
			break;
		}
		
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
