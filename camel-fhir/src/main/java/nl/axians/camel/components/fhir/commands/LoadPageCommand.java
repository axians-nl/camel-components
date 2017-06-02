package nl.axians.camel.components.fhir.commands;

import org.apache.camel.Exchange;
import org.hl7.fhir.instance.model.api.IBaseBundle;

import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.IGetPageTyped;
import nl.axians.camel.components.fhir.DataFormatEnum;
import nl.axians.camel.components.fhir.FhirConfiguration;

public class LoadPageCommand implements FhirCommand {

	private PageDirectionEnum pageDirection;
	private IBaseBundle bundle;
	
	@Override
	public void execute(FhirConfiguration configuration, IGenericClient client, Exchange exchange) {
		IGetPageTyped<IBaseBundle> request;
		if (pageDirection == PageDirectionEnum.NEXT) {
			request = client.loadPage().next(bundle);
		} else {
			request = client.loadPage().previous(bundle);
		}
		
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

		IBaseBundle bundle = request.execute();
		exchange.getIn().setBody(bundle);
	}

}
