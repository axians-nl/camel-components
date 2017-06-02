package nl.axians.camel.components.fhir.commands;

import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.IPatchWithBody;
import ca.uhn.fhir.rest.gclient.IUpdateTyped;
import nl.axians.camel.components.fhir.DataFormatEnum;
import nl.axians.camel.components.fhir.FhirConfiguration;

public class PatchCommand implements FhirCommand {

	private String resourceBody;
	private IBaseResource resource;
	
	@Override
	public void execute(FhirConfiguration configuration, IGenericClient client, Exchange exchange) {
		IPatchWithBody request = client.patch().withBody(resourceBody);
		
		// Check if we need to apply pretty printing of the request.
		if (configuration.getPrettyPrint()) {
			request.prettyPrint();
		}
		
		// Check which output format to use: XML or JSON.
		if (configuration.getDataFormat() == DataFormatEnum.XML) {
			request.encodedXml();
		} else {
			request.encodedJson();
		}

		// Execute the request and put response on the exchange body.
		MethodOutcome response = request.execute();
		exchange.getIn().setBody(response);
	}

}
