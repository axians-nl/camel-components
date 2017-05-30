package nl.axians.camel.components.fhir.commands;

import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.IReadExecutable;
import ca.uhn.fhir.rest.gclient.IReadTyped;
import nl.axians.camel.components.fhir.DataFormatEnum;
import nl.axians.camel.components.fhir.FhirConfiguration;

public class ReadCommand implements FhirCommand {

	private Class<? extends IBaseResource> resourceClass;
	private String resourceClassName;
	private String resourceId;
	private String resourceVersion;
	private String resourceUrl;
	
	public ReadCommand() {
	}
	
	public ReadCommand(Class<? extends IBaseResource> resourceClass, String resourceClassName, String resourceId,
			String resourceVersion, String resourceUrl) {
		super();
		this.resourceClass = resourceClass;
		this.resourceClassName = resourceClassName;
		this.resourceId = resourceId;
		this.resourceVersion = resourceVersion;
		this.resourceUrl = resourceUrl;
	}
	
	public Class<? extends IBaseResource> getResourceClass() {
		return resourceClass;
	}

	public void setResourceClass(Class<? extends IBaseResource> resourceClass) {
		this.resourceClass = resourceClass;
	}

	public String getResourceClassName() {
		return resourceClassName;
	}

	public void setResourceClassName(String resourceClassName) {
		this.resourceClassName = resourceClassName;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceVersion() {
		return resourceVersion;
	}

	public void setResourceVersion(String resourceVersion) {
		this.resourceVersion = resourceVersion;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	@Override
	public void execute(FhirConfiguration configuration, IGenericClient client, Exchange exchange) {
		IReadTyped<? extends IBaseResource> request;
		if (resourceClass != null) {
			request = client.read().resource(resourceClass);
		} else {
			request = client.read().resource(resourceClassName);
		}
		
		IReadExecutable<? extends IBaseResource> reader;
		if (resourceId != null && StringUtils.isBlank(resourceVersion)) {
			reader = request.withId(resourceId);
		} else if (resourceId != null && StringUtils.isNotBlank(resourceVersion)) {
			reader = request.withIdAndVersion(resourceId, resourceVersion);
		} else {
			reader = request.withUrl(resourceUrl);
		}
		
		// Check if we need to apply pretty printing of the request.
		if (configuration.getPrettyPrint()) {
			reader.prettyPrint();
		}
		
		// Check which output format to use: XML or JSON.
		if (configuration.getDataFormat() == DataFormatEnum.XML) {
			reader.encodedXml();
		} else {
			reader.encodedJson();
		}
		
		IBaseResource response = reader.execute();
		exchange.getIn().setBody(response);
	}

}
