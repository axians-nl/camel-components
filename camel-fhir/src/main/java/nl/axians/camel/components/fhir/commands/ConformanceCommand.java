package nl.axians.camel.components.fhir.commands;

import org.apache.camel.Exchange;
import org.hl7.fhir.instance.model.api.IBaseConformance;

import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.IFetchConformanceTyped;
import nl.axians.camel.components.fhir.DataFormatEnum;
import nl.axians.camel.components.fhir.FhirConfiguration;

public class ConformanceCommand implements FhirCommand {

	private Class<? extends IBaseConformance> type;

	public static final ConformanceCommand createHL7ConformanceCommand() {
		return new ConformanceCommand(org.hl7.fhir.instance.model.Conformance.class);
	}

	public static final ConformanceCommand createDTU2ConformanceCommand() {
		return new ConformanceCommand(ca.uhn.fhir.model.dstu2.resource.Conformance.class);
	}
	
	public ConformanceCommand() {
	}
	
	public ConformanceCommand(Class<? extends IBaseConformance> type) {
		super();
		this.type = type;
	}

	@Override
	public void execute(FhirConfiguration configuration, IGenericClient client, Exchange exchange) {
		IFetchConformanceTyped<?> request = client.fetchConformance().ofType(type);

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
		IBaseConformance response = request.execute();
		exchange.getIn().setBody(response);
	}

	public Class<? extends IBaseConformance> getType() {
		return type;
	}

	public void setType(Class<? extends IBaseConformance> type) {
		this.type = type;
	}
	
}
