package nl.axians.camel.components.fhir;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

/**
 * FHIR Camel component.
 * 
 * @author Jacob Hoeflaken
 * @since 1.0
 */
public class FhirComponent extends DefaultComponent  {

	//*************************************************************************
	// Constructors.
	//*************************************************************************

	/**
	 * Default constructor.
	 */
	public FhirComponent() {
	}

	/**
	 * Specialized constructor.
	 * 
	 * @param camelContext The {@link CamelContext} where to add the component to.
	 */
	public FhirComponent(CamelContext camelContext) {
		super(camelContext);
	}
	
	//*************************************************************************
	// Functionality.
	//*************************************************************************
	
	/**
	 * Create a new FHIR endpoint. Automatically called by Camel when it needs a new
	 * FHIR endpoint.
	 * 
	 * @param protocol The protocol ("fhir").
	 * @param remaining Any remaining part of the protocol.
	 * @param parameters Any parameters specified in the component URI.
	 * 
	 * @return New initialized FHIR endpoint.
	 * 
	 * @see org.apache.camel.impl.DefaultComponent#createEndpoint(java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	protected Endpoint createEndpoint(String protocol, String remaining, 
			Map<String, Object> parameters) throws Exception {
		
		// Create the endpoint.
		FhirEndpoint endpoint = new FhirEndpoint(this, remaining);
		
		// Set the endpoint properties using the specified parameters.
		setProperties(endpoint, parameters);
		
		return endpoint;
	}
	
}
