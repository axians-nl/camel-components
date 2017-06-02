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
