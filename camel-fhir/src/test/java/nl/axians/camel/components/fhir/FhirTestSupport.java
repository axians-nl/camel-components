package nl.axians.camel.components.fhir;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.test.junit4.CamelTestSupport;

public class FhirTestSupport extends CamelTestSupport {
	
	private static final String FHIRTEST_BASE_DSTU2 = "http://fhirtest.uhn.ca/baseDstu2";

	protected Map<String, Object> getFhirHeaders() {
		Map<String, Object> headers = new HashMap<>();
		headers.put(Exchange.HTTP_BASE_URI, FHIRTEST_BASE_DSTU2);	
		
		return headers;
	}
	
}
