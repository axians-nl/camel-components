package nl.axians.camel.components.fhir.commands;

import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.IValidateUntyped;
import nl.axians.camel.components.fhir.DataFormatEnum;
import nl.axians.camel.components.fhir.FhirConfiguration;

public class ValidateCommand implements FhirCommand {

	private String resourceBody;
	private IBaseResource resource;
	
	@Override
	public void execute(FhirConfiguration configuration, IGenericClient client, Exchange exchange) {
		IValidateUntyped request = null;
		if (StringUtils.isNotBlank(resourceBody)) {
			request = client.validate().resource(resourceBody);
		} else {
			request = client.validate().resource(resource);
		}

		if (configuration.getPrettyPrint()) {
			request.prettyPrint();
		}
		
		// Check which output format to use: XML or JSON.
		if (configuration.getDataFormat() == DataFormatEnum.XML) {
			request.encodedXml();
		} else {
			request.encodedJson();
		}		
		
		MethodOutcome response = request.execute();
		exchange.getIn().setBody(response);
	}

}
