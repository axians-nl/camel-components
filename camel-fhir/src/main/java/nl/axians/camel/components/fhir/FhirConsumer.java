package nl.axians.camel.components.fhir;

import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;

public class FhirConsumer extends DefaultConsumer {

	public FhirConsumer(Endpoint endpoint, Processor processor) {
		super(endpoint, processor);
	}

}
