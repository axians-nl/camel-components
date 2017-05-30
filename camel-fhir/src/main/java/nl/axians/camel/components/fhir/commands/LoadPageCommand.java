package nl.axians.camel.components.fhir.commands;

import org.apache.camel.Exchange;

import ca.uhn.fhir.rest.client.IGenericClient;
import nl.axians.camel.components.fhir.FhirConfiguration;

public class LoadPageCommand implements FhirCommand {

	@Override
	public void execute(FhirConfiguration configuration, IGenericClient client, Exchange exchange) {
	}

}
