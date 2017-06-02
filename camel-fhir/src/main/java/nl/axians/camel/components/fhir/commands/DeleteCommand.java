/**
 * Copyright 2017 AXIANS
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.axians.camel.components.fhir.commands;

import org.apache.camel.Exchange;
import org.hl7.fhir.instance.model.api.IBaseOperationOutcome;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;

import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.IDeleteTyped;
import nl.axians.camel.components.fhir.DataFormatEnum;
import nl.axians.camel.components.fhir.FhirConfiguration;

public class DeleteCommand implements FhirCommand {

	private DeleteTypeEnum type;
	private IBaseResource resource;
	private IIdType resourceId;
	private Class<? extends IBaseResource> resourceClass;
	private String searchUrl;
	
	public static final DeleteCommand createDeleteResourceCommand(IBaseResource resource) {
		return new DeleteCommand(DeleteTypeEnum.RESOURCE, resource, null, null, null);
	}

	public static final DeleteCommand createDeleteResourceByIdCommand(IIdType resourceId) {
		return new DeleteCommand(DeleteTypeEnum.RESOURCE_BY_ID, null, resourceId, null, null);
	}
	
	public static final DeleteCommand createDeleteResourceByUrlCommand(Class<? extends IBaseResource> resourceClass, 
			String searchUrl) {
		return new DeleteCommand(DeleteTypeEnum.RESOURCE_CONDITIONAL_BY_URL, null, null, resourceClass, 
				searchUrl);
	}
	
	
	public DeleteCommand() {
	}
	
	public DeleteCommand(DeleteTypeEnum type, IBaseResource resource, IIdType resourceId,
			Class<? extends IBaseResource> resourceClass, String searchUrl) {
		super();
		this.type = type;
		this.resource = resource;
		this.resourceId = resourceId;
		this.resourceClass = resourceClass;
		this.searchUrl = searchUrl;
	}

	@Override
	public void execute(FhirConfiguration configuration, IGenericClient client, Exchange exchange) {
		switch(type) {
		case RESOURCE:
			delete(client.delete().resource(resource), configuration, client, exchange);
			break;
		case RESOURCE_BY_ID:
			delete(client.delete().resourceById(resourceId), configuration, client, exchange);
			break;
		case RESOURCE_CONDITIONAL_BY_URL:
			delete(client.delete().resourceConditionalByUrl(resourceClass.getSimpleName() + "?" + searchUrl), 
					configuration, client, exchange);
			break;
		}
	}
	
	public void delete(IDeleteTyped request, FhirConfiguration configuration, IGenericClient client, 
			Exchange exchange) {
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
		
		IBaseOperationOutcome response = request.execute();
		exchange.getIn().setBody(response);
	}

	public DeleteTypeEnum getType() {
		return type;
	}

	public void setType(DeleteTypeEnum type) {
		this.type = type;
	}

	public IBaseResource getResource() {
		return resource;
	}

	public void setResource(IBaseResource resource) {
		this.resource = resource;
	}

	public IIdType getResourceId() {
		return resourceId;
	}

	public void setResourceId(IIdType resourceId) {
		this.resourceId = resourceId;
	}

	public Class<? extends IBaseResource> getResourceClass() {
		return resourceClass;
	}

	public void setResourceClass(Class<? extends IBaseResource> resourceClass) {
		this.resourceClass = resourceClass;
	}

	public String getSearchUrl() {
		return searchUrl;
	}

	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}
	
}
