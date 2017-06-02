package nl.axians.camel.components.fhir.commands;

import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.instance.model.api.IBaseResource;
import ca.uhn.fhir.model.primitive.IdDt;

import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.IReadExecutable;
import ca.uhn.fhir.rest.gclient.IReadTyped;
import nl.axians.camel.components.fhir.DataFormatEnum;
import nl.axians.camel.components.fhir.FhirConfiguration;

public class VReadCommand implements FhirCommand {

	private Class<? extends IBaseResource> resourceClass;
	private String resourceClassName;
	private IdDt resourceId;
	private String resourceVersion;
	private String resourceUrl;
	
	public VReadCommand() {
	}
	
	public VReadCommand(Class<? extends IBaseResource> resourceClass, String resourceClassName, IdDt resourceId,
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

	public IdDt getResourceId() {
		return resourceId;
	}

	public void setResourceId(IdDt resourceId) {
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
		IBaseResource response = client.vread( resourceClass, resourceId);
		exchange.getIn().setBody(response);
	}


}
