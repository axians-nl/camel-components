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

import java.util.Date;

import org.apache.camel.Exchange;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;

import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.IHistoryTyped;
import nl.axians.camel.components.fhir.DataFormatEnum;
import nl.axians.camel.components.fhir.FhirConfiguration;

public class HistoryCommand implements FhirCommand {

	private HistoryCommandTypeEnum type;
	private IIdType resourceId;
	private Class<? extends IBaseResource> resourceClass;
	private Class<? extends IBaseBundle> bundleClass;
	private Date since;
	private Integer count;
	
	public static final HistoryCommand createHistoryCommandOnInstance(IIdType resourceId, 
			BundleTypeEnum bundleType) {
		return new  HistoryCommand(HistoryCommandTypeEnum.ON_INSTANCE, resourceId, null,
				BundleTypeEnum.getBundleTypeClass(bundleType));
				
	}

	public static final HistoryCommand createHistoryCommandOnServer(BundleTypeEnum bundleType) {
		return new  HistoryCommand(HistoryCommandTypeEnum.ON_SERVER, null, null,
				BundleTypeEnum.getBundleTypeClass(bundleType));
				
	}

	public static final HistoryCommand createHistoryCommandOnType(Class<? extends IBaseResource> resourceClass, 
			BundleTypeEnum bundleType) {
		return new  HistoryCommand(HistoryCommandTypeEnum.ON_INSTANCE, null, resourceClass,
				BundleTypeEnum.getBundleTypeClass(bundleType));
				
	}

	public HistoryCommand() {
	}
	
	public HistoryCommand(HistoryCommandTypeEnum type, IIdType resourceId, Class<? extends IBaseResource> resourceClass,
			Class<? extends IBaseBundle> bundleClass) {
		super();
		this.type = type;
		this.resourceId = resourceId;
		this.resourceClass = resourceClass;
		this.bundleClass = bundleClass;
	}

	public HistoryCommandTypeEnum getType() {
		return type;
	}

	public void setType(HistoryCommandTypeEnum type) {
		this.type = type;
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

	public Class<? extends IBaseBundle> getBundleClass() {
		return bundleClass;
	}

	public void setBundleClass(Class<? extends IBaseBundle> bundleClass) {
		this.bundleClass = bundleClass;
	}
	
	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public void execute(FhirConfiguration configuration, IGenericClient client, Exchange exchange) {
		IHistoryTyped<? extends IBaseBundle> request;
		switch(type) {
		case ON_INSTANCE:
			request = client.history().onInstance(resourceId).andReturnBundle(bundleClass);
			break;
		case ON_SERVER:
			request = client.history().onServer().andReturnBundle(bundleClass);
			break;
		case ON_TYPE:
			request = client.history().onType(resourceClass).andReturnBundle(bundleClass);
			break;
		default:
			throw new RuntimeException(String.format("Invalid HistoryCommand type '%s'", type));
		}
		
		if (request != null) {
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

			// Set maximum number of results.
			if (count != null) {
				request.count(count);
			}
			
			// Set the date and time on or after which resource must be created.
			if (since != null) {
				request.since(since);
			}
			
			// Execute the request and put response on the exchange body.
			IBaseBundle response = request.execute();
			exchange.getIn().setBody(response);
		}
	}

}
