/*******************************************************************************
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
 *******************************************************************************/
package nl.axians.camel.components.fhir.commands;

import org.apache.camel.Exchange;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.model.api.Bundle;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.IQuery;
import nl.axians.camel.components.fhir.DataFormatEnum;
import nl.axians.camel.components.fhir.FhirConfiguration;

// TODO Add support for use of criteria.
public class SearchCommand implements FhirCommand {
	private Class<? extends IBaseResource> searchClass;
	private BundleTypeEnum bundleType;
	private String queryURL;
	
	public SearchCommand() {
	}
	
	public SearchCommand(Class<? extends IBaseResource> searchClass, BundleTypeEnum bundleType, 
			String queryURL) {
		super();
		this.searchClass = searchClass;
		this.bundleType = bundleType;
		this.queryURL = queryURL;
	}
	
	public Class<? extends IBaseResource> getSearchClass() {
		return searchClass;
	}

	public void setSearchClass(Class<? extends IBaseResource> searchClass) {
		this.searchClass = searchClass;
	}

	public BundleTypeEnum getBundleType() {
		return bundleType;
	}

	public void setBundleType(BundleTypeEnum bundleType) {
		this.bundleType = bundleType;
	}

	public String getQueryURL() {
		return queryURL;
	}

	public void setQueryURL(String queryURL) {
		this.queryURL = queryURL;
	}

	@Override
	public void execute(FhirConfiguration configuration, IGenericClient client, Exchange exchange) {
		IQuery<Bundle> query;
		
		// Create the query for the search command.
		query = client.search().byUrl(searchClass.getSimpleName() + "?" + queryURL);
		
		// Check if we need to apply pretty printing of the request.
		if (configuration.getPrettyPrint()) {
			query.prettyPrint();
		}

		// Check which output format to use: XML or JSON.
		if (configuration.getDataFormat() == DataFormatEnum.XML) {
			query.encodedXml();
		} else {
			query.encodedJson();
		}

		// Execute the request and put response on the exchange body.
		IBaseBundle bundle;
		if (bundleType == BundleTypeEnum.HL7) { 
			bundle = query.returnBundle(org.hl7.fhir.instance.model.Bundle.class).execute();
		} else {
			bundle = query.returnBundle(ca.uhn.fhir.model.dstu2.resource.Bundle.class).execute();
		}
					
		exchange.getIn().setBody(bundle);
	}
	
}
